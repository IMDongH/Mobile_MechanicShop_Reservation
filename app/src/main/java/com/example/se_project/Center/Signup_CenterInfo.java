package com.example.se_project.Center;

import java.util.HashMap;

public class Signup_CenterInfo {
    private String name;
    private String phone;
    private String date;

    private String startTime;
    private String endTime;
    private String RoadName_Address;
    private String CenterName;
    private double longitude;
    private double Latitude ;
    private long type;

    public Signup_CenterInfo(String name, String date, String phone,
                             String startTime, String endTime,
                             String centerName, String roadName_Address,
                             double longitude, double latitude, long type) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        RoadName_Address = roadName_Address;
        CenterName = centerName;
        this.longitude = longitude;
        Latitude = latitude;
        this.type = type;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoadName_Address() {
        return RoadName_Address;
    }

    public void setRoadName_Address(String roadName_Address) {
        RoadName_Address = roadName_Address;
    }

    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

}
