package com.tst.mobileappws.security;

import com.tst.mobileappws.SpringApplicationContext;

public class SecurityConstatnt {
    public static final long EXPIRATION_TIME = 864000000; // 10 Days
    public static final String TOKEN_PREFIX = "Pranay";
    public static final String HEADER_STRING ="Authorization";
    public static final String SING_UP_URL ="/users";
    public static final String H2_CONSOLE = "/h2-console/**";


public static String getTokenSecret(){
        AppProperties appProperties=(AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }

}
