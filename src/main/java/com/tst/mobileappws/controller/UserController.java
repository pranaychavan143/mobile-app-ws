package com.tst.mobileappws.controller;

import com.tst.mobileappws.exceptions.UserServiceException;
import com.tst.mobileappws.ui.model.response.ErrorMessage;
import com.tst.mobileappws.ui.model.response.OperationStatusModel;
import com.tst.mobileappws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.tst.mobileappws.service.UserService;
import com.tst.mobileappws.shared.dto.UserDto;
import com.tst.mobileappws.ui.model.request.UserDetailRequestModel;
import com.tst.mobileappws.ui.model.response.UserRest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

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
	
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())throw  new UserServiceException(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessages());
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
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
}

