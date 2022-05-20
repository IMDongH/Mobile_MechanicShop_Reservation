package com.example.se_project.Center;

public class Reservation_Info {

    private String name;
    private String phone;
    private String time;
    private String type;
    private String why;
    private String date;
    private String UID;
    public Reservation_Info(String name, String phone, String time, String type, String why, String date, String UID) {
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.type = type;
        this.why = why;
        this.date = date;
        this.UID = UID ;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
