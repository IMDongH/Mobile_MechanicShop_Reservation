package com.example.se_project.User.Search;

@SuppressWarnings("FieldMayBeFinal")
public class SearchTitleClass {
    @SuppressWarnings("FieldMayBeFinal")
    private String Name;
    @SuppressWarnings("FieldMayBeFinal")
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