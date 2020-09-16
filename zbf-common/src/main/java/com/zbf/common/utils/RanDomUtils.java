package com.zbf.common.utils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 */
public class RanDomUtils {

    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    private static final Pattern PATTERN_EMAIL = Pattern.compile("^\\w+@\\w+([-]\\w+)*(\\.\\w+)+$");


    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
            for(int i=1; i<=4-randLength; i++)
                fourRandom = "0"+ fourRandom ;
        }
        return fourRandom;
    }

    // 手机验证正则比对
    public static boolean isPhone(String phone){
        return PATTERN_PHONE.matcher(phone).matches();
    }

    // 邮箱验证正则比对
    public static boolean isEmail(String email){
        return PATTERN_EMAIL.matcher(email).matches();
    }
}
