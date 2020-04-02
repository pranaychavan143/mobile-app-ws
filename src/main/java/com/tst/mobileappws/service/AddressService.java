package com.tst.mobileappws.service;

import com.tst.mobileappws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService
{
    List<AddressDto> getAddresses(String id);
    AddressDto getAddress(String addressId);
}
