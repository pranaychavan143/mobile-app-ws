package com.tst.mobileappws.shared;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;
@Component
public class Utils {
	private  final  Random RANDOM =new SecureRandom();
	private  final String ALPHBET="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	
	public String generateUserId(int length) {
		return generateRandomString(length);
		
	}

	private String generateRandomString(int length) {
		StringBuilder returnValue=new StringBuilder();
		for(int i=0;i<length;i++) {
			returnValue.append(ALPHBET.charAt(RANDOM.nextInt(ALPHBET.length())));
		}
		return new String(returnValue);
	}

}
