package com.tst.mobileappws.service.impl;

import com.tst.mobileappws.io.entity.AddressEntity;
import com.tst.mobileappws.io.entity.UserEntity;
import com.tst.mobileappws.repository.AddressRepository;
import com.tst.mobileappws.repository.UserRepository;
import com.tst.mobileappws.service.AddressService;
import com.tst.mobileappws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
     private UserRepository userRepository;


     @Autowired
     private AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String id) {

        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) return  returnValue;

        Iterable<AddressEntity>addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity:addresses){
            returnValue.add(modelMapper.map(addressEntity,AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {

        AddressDto returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if(addressEntity!=null){
            returnValue = new ModelMapper().map(addressEntity,AddressDto.class);
        }

        return returnValue;
    }
}
