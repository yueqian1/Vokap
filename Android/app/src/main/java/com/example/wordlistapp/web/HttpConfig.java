package com.example.wordlistapp.web;

public class HttpConfig {
    public static final int HTTP_FAILURE = -1;
    public static final int HTTP_SUCCESS = 200;
    public static final int HTTP_ERROR = 500;
    public static final int LOGIN_FAILURE = 202;
    public static final int LOGIN_SUCCESS = 203;
    public static final int ADD_SUCCESS = 201;
    public static final int DELETE_SUCCESS = 204;
    public static final int ADD_FAILURE = 205;
    public static final int DELETE_FAILURE = 206;
    public static final int SELECT_ONE = 207;
    public static final int SELECT_FAILURE = 208;
    public static final int REFISTER_FAILURE = 209;
    public static final int UPDATE_FAILURE = 210;
    public static final int UPDATE_SUCCESS = 211;
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    //public static final String SELECT_URL="http://192.168.0.109:8080/Server3/select";
    //public static final String UPDATE_URL="http://192.168.0.109:8080/Server3/update";

    public static final String SELECT_URL = "http://192.168.1.3:8080/ServerTest/select";
    public static final String UPDATE_URL = "http://192.168.1.3:8080/ServerTest/update";
}
