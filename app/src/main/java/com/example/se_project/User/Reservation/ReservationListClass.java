package com.example.se_project.User.Reservation;

public class ReservationListClass {
    private String Date;
    private String Name;
    private String Location;
    private String Time;
    public ReservationListClass(String Date,String Name, String Location, String Time) {
        this.Date = Date;
        this.Name = Name;
        this.Location = Location;
        this.Time = Time;
    }
    public String getDate() { return this.Date; }
    public String getName() { return this.Name; }
    public String getLocation() {
        return this.Location;
    }
    public String getTime() {
        return this.Time;
    }

}