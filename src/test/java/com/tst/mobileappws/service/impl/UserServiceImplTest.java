package com.tst.mobileappws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tst.mobileappws.exceptions.UserServiceException;
import com.tst.mobileappws.io.entity.AddressEntity;
import com.tst.mobileappws.io.entity.UserEntity;
import com.tst.mobileappws.repository.UserRepository;
import com.tst.mobileappws.shared.Utils;
import com.tst.mobileappws.shared.dto.AddressDto;
import com.tst.mobileappws.shared.dto.UserDto;

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId ="hhjjj57cha";
    String encyptedPassword="756dfhjkk";
    UserEntity userEntity;


    @BeforeEach
     void setUp() throws  Exception{
      MockitoAnnotations.initMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setFirstName("Pranay");
        userEntity.setLastName("Chavan");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("756dfhjkk");
        userEntity.setEmail("chavan.pranay4098@gmail.com");
        userEntity.setEmailVerificationTocken("afgdhdj123");
        userEntity.setEmailVerificationStatus(true);
        userEntity.setAddress(getAddressEntityList());
    }

    @Test
    final void testGetUser(){

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto= userService.getUser("chavan.pranay4098@gmail.com");

        assertNotNull(userDto);
        assertEquals(userEntity.getFirstName(),userDto.getFirstName());
    }

    final void testCreateUser_CreateUserServiceException(){
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = new UserDto();
        userDto.setAddress(getAddressDtos());
        userDto.setFirstName("Pranay");
        userDto.setLastName("Chavan");
        userDto.setPassword("12345678");
        userDto.setEmail("chavan.pranay4098@gmail.com");


        assertThrows(UserServiceException.class,
                ()->{
                    userService.createUser(userDto);
                }
        );
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

    @Test
    final void testCreateUser(){


      when(userRepository.findByEmail(anyString())).thenReturn(null);
      when(utils.generateAddressId(anyInt())).thenReturn("dffgfggf1234");
      when(utils.generateUserId(anyInt())).thenReturn(userId);
      when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encyptedPassword);
      when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);


        UserDto userDto = new UserDto();
        userDto.setAddress(getAddressDtos());
        userDto.setFirstName("Pranay");
        userDto.setLastName("Chavan");
        userDto.setPassword("12345678");
        userDto.setEmail("chavan.pranay4098@gmail.com");

        UserDto storedUserDetail = new UserDto();

        storedUserDetail = userService.createUser(userDto);



     assertNotNull(storedUserDetail);
     assertNotNull(storedUserDetail.getUserId());
     assertEquals(userEntity.getFirstName(),storedUserDetail.getFirstName());
     assertEquals(userEntity.getLastName(),storedUserDetail.getLastName());
     assertEquals(storedUserDetail.getAddress().size(),userEntity.getAddress().size());
     verify(utils,times(userEntity.getAddress().size())).generateAddressId(30);
     verify(bCryptPasswordEncoder,times(1)).encode("12345678");
     verify(userRepository,times(1)).save(any(UserEntity.class));
    }

    private List<AddressDto> getAddressDtos(){
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("Pune");
        addressDto.setCountry("INDIA");
        addressDto.setPostalCode(" ABC1432");
        addressDto.setStreetName("1432 street Name");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("billing");
        billingAddressDto.setCity("Pune");
        billingAddressDto.setCountry("INDIA");
        billingAddressDto.setPostalCode(" ABC1432");
        billingAddressDto.setStreetName("1432 street Name");

        List<AddressDto> addressDtoList = new ArrayList<>();
        addressDtoList.add(addressDto);
        addressDtoList.add(billingAddressDto);

         return addressDtoList;
    }


       private List<AddressEntity>getAddressEntityList(){

        List<AddressDto> addresses = getAddressDtos();

        Type listType= new TypeToken<List<AddressEntity>>(){}.getType();

        return new ModelMapper().map(addresses,listType);

    }
}
