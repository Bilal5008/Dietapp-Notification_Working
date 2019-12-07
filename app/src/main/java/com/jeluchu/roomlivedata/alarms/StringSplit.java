package com.jeluchu.roomlivedata.alarms;

public class StringSplit {


    public static int getIntAt0(String s) {


        return Integer.parseInt(s.split(":")[0].trim());
    }

    public static int getIntAt1(String s) {

        return Integer.parseInt(s.split(":")[1].trim());
    }
}
