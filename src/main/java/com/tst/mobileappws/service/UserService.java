package com.tst.mobileappws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tst.mobileappws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
//User Deatail service come from springframework security
	UserDto createUser(UserDto user);
}
