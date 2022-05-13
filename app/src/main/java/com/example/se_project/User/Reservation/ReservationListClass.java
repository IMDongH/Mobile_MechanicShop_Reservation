package com.example.se_project.User.Reservation;

public class ReservationListClass {
    private String Date;
    private String Name;
    private String Location;
    public ReservationListClass(String Date,String Name, String Location) {
        this.Date = Date;
        this.Name = Name;
        this.Location = Location;
    }
    public String getDate() { return this.Date; }
    public String getName() { return this.Name; }
    public String getLocation() {
        return this.Location;
    }

}