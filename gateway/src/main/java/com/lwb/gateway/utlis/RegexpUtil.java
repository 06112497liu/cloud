package com.lwb.gateway.utlis;

import java.util.regex.Pattern;

/**
 * @author liuweibo
 * @date 2020/1/16
 */
public class RegexpUtil {

    interface RegexpString {
        String age = "^[0-9]{1,2}$";
        String birthDay = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        String phone = "^1[3-9]\\d{9}$";
        String numEleven = "^[1-9][0-9]{10}$";
        String num = "^[1-9][0-9]*$";
        String ipV4 = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    }
    interface RegexpPattern {
        Pattern age = Pattern.compile(RegexpString.age);
        Pattern birthDay = Pattern.compile(RegexpString.birthDay);
        Pattern phone = Pattern.compile(RegexpString.phone);
        Pattern numEleven = Pattern.compile(RegexpString.numEleven);
        Pattern  num = Pattern.compile(RegexpString.num);
        Pattern ipV4 = Pattern.compile(RegexpString.ipV4);
    }

    static boolean isMath(Pattern pattern,String content){
        return pattern.matcher(content).find();
    }

    static boolean isMathAll(Pattern pattern,String content){
        return pattern.matcher(content).matches();
    }
}
