package com.tst.mobileappws.service.impl;

import com.tst.mobileappws.io.entity.UserEntity;
import com.tst.mobileappws.repository.UserRepository;
import com.tst.mobileappws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.anyString;

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws  Exception{
      MockitoAnnotations.initMocks(this);
    }

    @Test
    final void testGetUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setFirstName("Pranay");
        userEntity.setLastName("chavan");
        userEntity.setUserId("hhjjj57cha");
        userEntity.setEncryptedPassword("756dfhjkk");
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto= userService.getUser("chavan.pranay4098@gmail.com");

        assertNotNull(userDto);
        assertEquals("Pranay",userDto.getFirstName());
    }

    @Test
    final void testGetUser_UserNotFoundException()
    {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                ()->{
                        userService.getUser("chavan.pranay4098@gmail.com");
                }
                );
    }
}
