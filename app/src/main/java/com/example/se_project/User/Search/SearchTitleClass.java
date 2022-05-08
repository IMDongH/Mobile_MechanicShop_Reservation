package com.example.se_project.User.Search;

public class SearchTitleClass {
    private String Name;
    private String Location;
    public SearchTitleClass(String Name, String Location) {
        this.Name = Name;
        this.Location = Location;
    }

    public String getName() { return this.Name; }
    public String getLocation() {
        return this.Location;
    }

}