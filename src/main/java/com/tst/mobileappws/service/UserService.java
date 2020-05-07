package com.tst.mobileappws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tst.mobileappws.shared.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
//User Deatail service come from springframework security package
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId,UserDto user);
	void  deleteUser(String userId);
	List<UserDto> getUsers(int page, int limit);
}

