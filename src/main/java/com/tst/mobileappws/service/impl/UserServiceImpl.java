package com.tst.mobileappws.service.impl;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import com.tst.mobileappws.exceptions.UserServiceException;
import com.tst.mobileappws.shared.dto.AddressDto;
import com.tst.mobileappws.ui.model.response.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tst.mobileappws.io.entity.UserEntity;
import com.tst.mobileappws.repository.UserRepository;
import com.tst.mobileappws.service.UserService;
import com.tst.mobileappws.shared.Utils;
import com.tst.mobileappws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("Record Allredy Exists");
		    for (int i=0; i<user.getAddressDto().size();i++){
				AddressDto addressDto = user.getAddressDto().get(i);
				addressDto.setUserDetails(user);
				addressDto.setAddressId(utils.generateAddressId(30));
				user.getAddressDto().set(i,addressDto);
			}

		//BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper= new ModelMapper();
		UserEntity	userEntity = modelMapper.map(user,UserEntity.class);

		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		UserEntity storedUserDetail = userRepository.save(userEntity);
		UserDto returnValue =modelMapper.map(storedUserDetail,UserDto.class);
		return returnValue;
	}



	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue =new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity,returnValue);
		return returnValue;

	}

	@Override
	public  UserDto getUser(String email){
		UserEntity userEntity =userRepository.findByEmail(email);
		if (userEntity==null)throw 	new UsernameNotFoundException(email);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity,returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue =new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null)
			throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getErrorMessages());

			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());

			UserEntity updateUserDetails = userRepository.save(userEntity);
			BeanUtils.copyProperties(updateUserDetails,returnValue);
		return returnValue;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		if(page>0) page= page-1;
		PageRequest pageableRequest = PageRequest.of(page,limit);
		Page<UserEntity> usersPage=userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
         for (UserEntity userEntity: users){
         	UserDto userDto = new UserDto();
         	BeanUtils.copyProperties(userEntity,userDto);
         	returnValue.add(userDto);
		 }
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null)
			throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getErrorMessages());

		userRepository.delete(userEntity);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

}
