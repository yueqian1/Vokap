package com.example.wordlistapp.include;

public class UserInformation {

    private static String userID = "";
    private static String userName;
    private static String password;

    public static void init() {
        userID = SharedPreferencesKit.loadString("userID", "");

        if (!userID.equals("")) {
            userName = SharedPreferencesKit.loadString("userName", "");
            password = SharedPreferencesKit.loadString("password", "");
        }
    }

    private static void saveData() {
        if (userID.equals("")) {
            return;
        }

        SharedPreferencesKit.writeString("userID", userID);
        SharedPreferencesKit.writeString("userName", userName);
        SharedPreferencesKit.writeString("password", password);
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserInformation.userID = userID;
        saveData();
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserInformation.userName = userName;
        saveData();
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserInformation.password = password;
        saveData();
    }
}
