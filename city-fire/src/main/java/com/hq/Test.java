package com.hq;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        Calendar calendar = Calendar.getInstance();
        System.out.println(format.format(calendar.getTime()));
        int i = 5;
        int j = 10;
        System.out.println(i + ~j);
        int a=10 ; double b=3.14;
        char c='a';
        System.out.println(c+a+b);
    }


}
