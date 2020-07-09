package com.hwadee.web.entity;

public class Users {
    private int User_id;
    private String name;
    private String passwd;

    public Users() {
    }

    public int getUsers_id() {
        return User_id;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setUsers_id(int users_id) {
        User_id = users_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
