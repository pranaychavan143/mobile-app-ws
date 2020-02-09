package com.tst.mobileappws.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tst.mobileappws.service.UserService;
import com.tst.mobileappws.shared.dto.UserDto;
import com.tst.mobileappws.ui.model.request.UserDetailRequestModel;
import com.tst.mobileappws.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String getUser() {
		return "get User was called";
	}

	@PostMapping
	public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) {
	
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;
	}

	@PutMapping
	public String updateUser() {
		return " update User was called";
	}

	public String deleteUser() {
		return "delete user was called";
	}

}
