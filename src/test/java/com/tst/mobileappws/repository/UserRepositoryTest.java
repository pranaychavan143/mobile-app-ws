package com.tst.mobileappws.repository;

import com.tst.mobileappws.io.entity.AddressEntity;
import com.tst.mobileappws.io.entity.UserEntity;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordCreated=false;


       @BeforeEach
        void setUp () throws Exception{
          if(!recordCreated)  createRecord();
       }

        @Test
        final void testVerifiedUser(){

             // Pageable pagebleRequest = (Pageable) PageRequest.of(0, 2);
              Page<UserEntity> page = userRepository.findAllUserWithConfirmedEmailAddress(new PageRequest(0, 2));
              assertNotNull(page);


              List<UserEntity> userEntities = page.getContent();
              assertNotNull(userEntities);
              assertTrue(userEntities.size() == 2);


        }

        @Test
        final  void testFindUserByFirstName(){
           String firstName="Pranay";
           List<UserEntity> users = userRepository.findUserByFirstName(firstName);
           assertNotNull(users);
           assertTrue(users.size()==2);

           UserEntity user = users.get(0);
           assertTrue(user.getFirstName().equals(firstName));

        }


    @Test
    final  void testFindUserByLastName(){
        String lastName="Chavan";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size()==2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equals(lastName));

    }


    @Test
    final  void testFindUserByKeyword(){
        String keyword="Cha";
        List<UserEntity> users = userRepository.findUserByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size()==2);

        UserEntity user = users.get(0);
        assertTrue(
                user.getLastName().contains(keyword) ||
                user.getFirstName().contains(keyword)
        );

    }

    @Test
    final  void testFindUserFirstNameAndLastNameByKeyword(){
        String keyword="Cha";
      List <Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size()==2);

       Object[] user = users.get(0);

       assertTrue(user.length==2);

       String userFirstName=String.valueOf(user[0]);
       String userLastName=String.valueOf(user[1]);

       assertNotNull(userFirstName);
       assertNotNull(userLastName);
    }


    @Test
    final void testUpdateUserEmailVerificationStatus(){

           boolean newEmailVerificationStatus = false;

           userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "2a3s4d");

           UserEntity storedUserDetails = userRepository.findByUserId("2a3s4d");
           boolean emailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
           assertTrue(emailVerificationStatus==newEmailVerificationStatus);
    }

    @Test
    final void testfindUserEntityByUserId(){
           String userId="2a3s4d";
           UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
           assertNotNull(userEntity);
           assertTrue(userEntity.getUserId()==userId);
    }

    @Test
    final void testGetUserEntityFullNameByUserId()
    {
        String userId="2a3s4d";
        List<Object[]> userFullNameRecords = userRepository.getUserEntityFullNameByUserId(userId);

        assertNotNull(userFullNameRecords);
        assertTrue(userFullNameRecords.size()==1);

      Object[] userDetails = userFullNameRecords.get(0);

        String firstName= String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }




    @Test
    final void testUpdateUserEntityEmailVerificationStatus(){

        boolean newEmailVerificationStatus = true;

        userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus,"2a3s4d");

        UserEntity storedUserDetails = userRepository.findByEmail("2a3s4d");
        boolean emailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
        assertTrue(emailVerificationStatus==newEmailVerificationStatus);
    }

    private  void createRecord()
        {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName("Pranay");
            userEntity.setLastName("Chavan");
            userEntity.setUserId("2a3s4d");
            userEntity.setPassword("123456");
            userEntity.setEncryptedPassword("756dfhjkk");
            userEntity.setEmail("chavan.pranay4098@gmail.com");
            userEntity.setEmailVerificationStatus(true);


            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setType("Shipping");
            addressEntity.setAddressId("AEBHh134");
            addressEntity.setCountry("INDIA");
            addressEntity.setCity("Pune");
            addressEntity.setPostalCode("ABCCD");
            addressEntity.setStreetName("143 street Address");

            List<AddressEntity> address = new ArrayList();
            address.add(addressEntity);

            userEntity.setAddress(address);

            UserEntity userEntity2 = new UserEntity();
            userEntity2.setFirstName("Pranay");
            userEntity2.setLastName("Chavan");
            userEntity2.setUserId("2a3s4d");
            userEntity2.setPassword("12345678");
            userEntity2.setEncryptedPassword("756dfhjkk");
            userEntity2.setEmail("chavan.pranay4098@gmail.com");
            userEntity2.setEmailVerificationStatus(true);


            AddressEntity addressEntity2 = new AddressEntity();
            addressEntity2.setType("Shipping");
            addressEntity2.setAddressId("AEBHh134");
            addressEntity2.setCountry("INDIA");
            addressEntity2.setCity("Pune");
            addressEntity2.setPostalCode("ABCCD");
            addressEntity2.setStreetName("143 street Address");

            List<AddressEntity> address2 = new ArrayList();
            address2.add(addressEntity2);

            userEntity2.setAddress(address2);


            userRepository.save(userEntity2);

              recordCreated= true;
        }

}
