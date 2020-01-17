package com.lwb.gateway.utlis;

import java.util.stream.Stream;

/**
 * @author liuweibo
 * @date 2020/1/16
 */
public class StringBuilderUtil {

    private static final StringBuilder stringBuilder = new StringBuilder(150);

    /**
     * 使用StringBuilder来拼接字符串，线程安全
     * @param objs
     * @return
     */
    public static  String appendToString(Object[] objs){
        return appendToString(true,objs);
    }

    /**
     * 使用StringBuilder来拼接字符串，线程安全
     * @param objs
     * @param clearStringBuilder 使用后是否清除原占有的空间
     * @return
     */
    public static  String appendToString(boolean clearStringBuilder,Object[] objs){
        synchronized(stringBuilder) {
            stringBuilder.setLength(0);
            Stream.of(objs).forEach(it -> stringBuilder.append(it));
            try {
                String re = stringBuilder.toString();
                return re;
            }finally {
                if(clearStringBuilder && stringBuilder.capacity() > 512){
                    stringBuilder.delete(0,stringBuilder.capacity() - 512);
                }
            }
        }
    }

    /**
     * 使用StringBuilder来拼接字符串，线程安全
     * @param objs
     * @return
     */
    public static String append(Object... objs){
        return appendToString(true,objs);
    }


}
