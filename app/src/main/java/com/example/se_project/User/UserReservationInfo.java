package com.example.se_project.User;

import android.location.Address;

public class UserReservationInfo {
    private String name;
    private String phone;
    private String date;
    private String time;
    private String centerName;
    private String carNumber;
    private String carType;
    private String content;
    private String address;
    private String userId;

    public UserReservationInfo() { }

    public UserReservationInfo(String name, String date, String phone,String time ,
                               String centerName ,String carNumber ,String carType ,String content, String address) {
        this.name = name;
        this.date = date;
        this.phone = phone;
        this.time = time;
        this.centerName = centerName;
        this.carNumber = carNumber;
        this.carType = carType;
        this.content = content;
        this.address =address;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
