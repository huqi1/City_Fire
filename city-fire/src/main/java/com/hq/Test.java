package com.hq;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        Calendar calendar = Calendar.getInstance();
        System.out.println(format.format(calendar.getTime()));
    }


}
