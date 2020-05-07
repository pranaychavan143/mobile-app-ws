package com.tst.mobileappws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsTest {
    @Autowired
    Utils utils;

    @BeforeEach
    void setUp()throws Exception{

    }

    @Test
    final void testGenerateUserId(){
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId);
        assertNotNull(userId2);

        assertTrue(userId2.length()==30);
        assertTrue(!userId.equalsIgnoreCase(userId2));

    }

    @Test
    @Disabled
    final void testHasTokenNotExpired(){

        String token = "61338287871b7d6bc65524631d11906a8fd1aa5d";
        boolean hasTokenExpired = Utils.hasTokenExperied(token);
        assertFalse(hasTokenExpired);

    }

    @Test
 @Disabled
    final void testHasTokenExpired()
    {
        String expireToken = "61338287871b7d6bc65524631d11906a8fd1aa5d";
        boolean hasTokenExpired = Utils.hasTokenExperied(expireToken);
        assertTrue(hasTokenExpired);
    }


}
