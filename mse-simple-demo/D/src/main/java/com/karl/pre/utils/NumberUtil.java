package com.karl.pre.utils;

public class NumberUtil {

    public static void main(String[] args) {

        String num1 = "10.65529010238908";
        System.out.println(getRound2Deci(num1));

        String num2 = "0.0";
        System.out.println(getRound2Deci(num2));

        String num3 = "100";
        System.out.println(getRound2Deci(num3));

        System.out.println(Double.parseDouble("2"));
    }

    private static String getRound2Deci(String number){
        if(number == null || "null".equals(number)){
            return number;
        }
        int index =  number.indexOf(".");
        if(index < 0) {
            return number;
        }
        return number.substring(0,index) + number.substring(index, Math.min(index + 3, number.length()));
    }
}
