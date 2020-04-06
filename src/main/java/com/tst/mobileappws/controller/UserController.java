package com.tst.mobileappws.controller;

import com.tst.mobileappws.exceptions.UserServiceException;
import com.tst.mobileappws.service.AddressService;
import com.tst.mobileappws.shared.dto.AddressDto;
import com.tst.mobileappws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.tst.mobileappws.service.UserService;
import com.tst.mobileappws.shared.dto.UserDto;
import com.tst.mobileappws.ui.model.request.UserDetailRequestModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;


	@GetMapping(value = "/{id}",
	 					produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
	)
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue= new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto,returnValue);

		return returnValue;
	}



	@PostMapping(
			consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
	)
	public UserRest createUser(@RequestBody UserDetailRequestModel userDetails)throws UserServiceException {
	


		if (userDetails.getFirstName().isEmpty())throw  new UserServiceException(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessages());

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto= modelMapper.map(userDetails,UserDto.class);
		UserDto createdUser = userService.createUser(userDto);
		UserRest  returnValue=modelMapper.map(createdUser,UserRest.class);
		return returnValue;
	}



	@PutMapping(path = "/{id}",
			consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
	)
	public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto updatedUser = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}",
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
	)
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
			returnValue.setOperationName(RequestOperationName.DELETE.name());
			userService.deleteUser(id);
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}



	@GetMapping(produces ={MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public List<UserRest> getUsers(@RequestParam(value = "page",defaultValue = "1")int page,
								  @RequestParam(value = "limit",defaultValue = "25")int limit){
		List<UserRest> returnValue = new ArrayList<>();
		 List<UserDto> users = userService.getUsers(page,limit);
		 for (UserDto userDto: users){
		 	UserRest userModel = new UserRest();
		 	BeanUtils.copyProperties(userDto,userModel);
		 	returnValue.add(userModel);
		 }
		return  returnValue;
	}

	@GetMapping(value = "/{id}/addresses",
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE,"application/hal+json"}
	)
	public Resources<AddressRest> getUserAddresses(@PathVariable String id) {
		List<AddressRest> addressesListRestModel= new ArrayList<>();
		List<AddressDto> addressDto = addressService.getAddresses(id);
		if(addressDto!=null && !addressDto.isEmpty())
		{
			Type listType = new TypeToken<List<AddressRest>>() {}.getType();
			addressesListRestModel = new ModelMapper().map(addressDto, listType);

			for (AddressRest addressRest: addressesListRestModel){
				Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id,addressRest.getAddressId())).withSelfRel();
				addressRest.add(addressLink);

				Link userLink =  linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
				addressRest.add(userLink);
			}


		}

		return new Resources<>(addressesListRestModel);
	}

	//  04-04-2020 "application/hal+json" in postman we also change the Acceptance

	@GetMapping(value = "/{id}/addresses/{addressId}",
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE,"application/hal+json"}
	)

	public Resource<AddressRest> getUserAddress(@PathVariable String id, @PathVariable String addressId){
		AddressDto addressDto = (AddressDto) addressService.getAddresses(addressId);
		ModelMapper modelMapper = new ModelMapper();

		Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id,addressId)).withSelfRel();
		Link userLink = linkTo(UserController.class).slash(id).withRel("user");
		Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(id)).withRel("addresses");


		AddressRest addressRestModel =modelMapper.map(addressDto, AddressRest.class);

		addressRestModel.add(addressLink);
		addressRestModel.add(userLink);
		addressRestModel.add(addressesLink);

		return new Resource<>(addressRestModel);

	}


}

