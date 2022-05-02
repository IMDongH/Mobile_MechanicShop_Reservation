package com.example.se_project.User;

public class UserInfoClass {
    private String name;
    private String phone;
    private String date;
    private String address;

    public UserInfoClass() { }

    public UserInfoClass(String name, String date, String phone) {
        this.name = name;
        this.phone = phone;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
