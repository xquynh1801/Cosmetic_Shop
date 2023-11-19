package com.example.lthdt.config;

import java.util.ArrayList;
import java.util.Arrays;

public class Constant {
    public static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;

    public static final int CXN_STATUS = 0;
    public static final int CGH_STATUS = 1;
    public static final int DANGGH_STATUS = 2;
    public static final int DAGH_STATUS = 3;
    public static final int HUY_STATUS = 4;
    public static ArrayList<Integer> LIST_ORDER_STATUS = new ArrayList<>(Arrays.asList(CXN_STATUS, CGH_STATUS, DANGGH_STATUS, DAGH_STATUS, HUY_STATUS));
}
