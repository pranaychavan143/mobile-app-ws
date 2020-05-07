package com.tst.mobileappws.shared;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import com.tst.mobileappws.security.SecurityConstatnt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
@Component
public class Utils {
	private  final  Random RANDOM =new SecureRandom();
	private  final String ALPHBET="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	
		public String generateUserId(int length) {
		return generateRandomString(length);
		
	}

	public String generateAddressId(int length) {
		return generateRandomString(length);

	}


	private String generateRandomString(int length) {
		StringBuilder returnValue=new StringBuilder();
		for(int i=0;i<length;i++) {
			returnValue.append(ALPHBET.charAt(RANDOM.nextInt(ALPHBET.length())));
		}
		return new String(returnValue);
	}

	public static boolean hasTokenExperied(String token){

			boolean returnValue =false;

			try {


				Claims claims = Jwts.parser()
						.setSigningKey(SecurityConstatnt.getTokenSecret())
						.parseClaimsJws(token).getBody();

				Date tokenExperationDate = claims.getExpiration();
				Date todayDate = new Date();
				returnValue=tokenExperationDate.before(todayDate);
			}
			catch (ExpiredJwtException ex){
				returnValue= true;
			}
		return returnValue;
	}

}
