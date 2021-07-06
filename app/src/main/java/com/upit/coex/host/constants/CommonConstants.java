package com.upit.coex.host.constants;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CommonConstants {
    public static final String CO = "CO";
    public static final String SERVICE_CO = "SERVICE_COO";
    public static final String INTENT_DATA_ROOM = "DATA_ROOM";

    public static final String STEP_1 = "STEP_1";

    public static final String STEP_2 = "STEP_2";

    public static final String STEP_3 = "STEP_3";


    public static final String CREATE_CO = "CREATE_CO";

    public static final String ADD_ROOM = "DO_ADD_ROOM";
    //public static final String STEP_1 = "doStep2";


    public static final String COMMON_DATE_FORMAT = "dd-MMM-yyyy h:mm a";

//    public static final StringBuilder BASE_URL = new StringBuilder("http://192.168.0.6:3000/");
    public static final StringBuilder BASE_URL = new StringBuilder("https://coextest.herokuapp.com/");
//    public static final StringBuilder BASE_URL = new StringBuilder("http://34.87.108.104:3000/");

//    public static String BASE_URL = "https://coexspace.herokuapp.com/";

    public static final String IMAGE_LINK_BASE = BASE_URL + "images/";

    public static final String PREFS_NAME = "coex_pref";

    public static final int NUMBER_OF_THREAD_IN_POOL = 5;

    public static final String COMMON_COEX_EXECUTOR_TAG = "COEX_EXECUTOR";

    public static final String COEX_FIREBASE = "COEX_FIREBASE";

    public static final String COEX_FIREBASE_TOKEN = "token_firebase";

    public static final String COEX_ID = "COEX_ID";

    public static final String TOKEN = "token";
    public static final String PREFIX_AUTHOR = "Bearer ";
    public static final int RES_CODE_200 = 200;

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String GOOGLE_MAP_KEY = "AIzaSyDS8IDvNfK_UldH1ro-YzikPmWKuu8o6DU";


    //
    public static final String BOOKING_REFEREN = "BOOKING_REFEREN";

    // status booking
    public static final String STATUS_SUCCESS = "Booking success";
    public static final String STATUS_CANCEL = "Booking cancel";

    // status booking
    public static final String STATUS_BOOKING_PENDING = "pending";
    public static final String STATUS_BOOKING_ON_GOING = "on_going";
    public static final String STATUS_BOOKING_SUCCESS = "success";
    public static final String STATUS_BOOKING_CANCEL = "cancelled";

    //
    public static final String CHECK_IN = "CHECK_IN";
    public static final String CHECK_OUT = "CHECK_OUT";


    //
    public static enum SCREEN {
        LOGIN,
        FORGOTPASSWORD,
        MAP,
        PROFILE
    }
}
