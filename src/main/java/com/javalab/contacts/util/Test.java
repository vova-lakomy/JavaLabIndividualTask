package com.javalab.contacts.util;

import java.util.Random;

/**
 * Created by student on 29.09.16.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(randomString(1000));
    }

    public static String randomString(int charsCount){
        StringBuilder stringBuilder = new StringBuilder("-");
        Random random = new Random();
        for (int i = 0; i < charsCount; i++) {
            char ch = (char) ((Math.abs(random.nextInt()) % 25) + 65);
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
}
