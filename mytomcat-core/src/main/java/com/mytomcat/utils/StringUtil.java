package com.mytomcat.utils;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-17 22:19
 **/
public class StringUtil {
    public static String getBeanName(String string){
        String[] split = string.split("\\.");
        if (split.length == 0 || split == null){
            return string;
        }
        return split[split.length-1];
    }

    public static void main(String[] args) {
        String string ="com.student";
        System.out.println(getBeanName(string));
    }
}



