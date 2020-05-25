package com.tst.mobileappws.controller;


import com.tst.mobileappws.service.impl.UserServiceImpl;
import com.tst.mobileappws.shared.dto.AddressDto;
import com.tst.mobileappws.shared.dto.UserDto;
import com.tst.mobileappws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;


    UserDto userDto;

    final String USER_ID="asdfghjk567764";


    @BeforeEach()
    void setUp()throws  Exception{

        MockitoAnnotations.initMocks(this);
        userDto = new UserDto();
        userDto.setId(1l);
        userDto.setFirstName("Pranay");
        userDto.setLastName("Chavan");
        userDto.setUserId(USER_ID);
        userDto.setEncryptedPassword("756dfhjkk");
        userDto.setEmail("chavan.pranay4098@gmail.com");
        userDto.setEmailVerificationTocken(null);
        userDto.setEmailVerificationStatus(false);
        userDto.setAddress(getAddressDtos());

    }

    @Test
    final void testGetUser(){

        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
         UserRest userRest = userController.getUser(USER_ID);
         assertNotNull(userRest);
         assertEquals(USER_ID,userRest.getUserId());
         assertEquals(userRest.getFirstName(),userDto.getFirstName());
         assertEquals(userRest.getLastName(),userDto.getLastName());
       // assertSame(userRest.getAddressRest().size(),userDto.getAddressDto().size());



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

}
