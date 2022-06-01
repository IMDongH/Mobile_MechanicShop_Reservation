package com.example.se_project.User.Reservation;

@SuppressWarnings("FieldMayBeFinal")
public class ReservationListClass {
    @SuppressWarnings("FieldMayBeFinal")
    private String Date;
    @SuppressWarnings("FieldMayBeFinal")
    private String Name;
    @SuppressWarnings("FieldMayBeFinal")
    private String Location;
    @SuppressWarnings("FieldMayBeFinal")
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