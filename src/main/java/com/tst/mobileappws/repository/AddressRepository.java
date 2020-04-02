package com.tst.mobileappws.repository;

import com.tst.mobileappws.io.entity.AddressEntity;
import com.tst.mobileappws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
    AddressEntity findByAddressId(String addressId);
}
