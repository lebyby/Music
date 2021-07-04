package com.example.music;


import android.content.Context;

import java.util.Comparator;

public class Groups {
    private	int	id;
    private String name;
    private String place;
    private String year;


    public Groups(String name, String place, String year) {
        this.name = name;
        this.place = place;
        this.year = year;
    }

    public Groups(int id, String name, String place, String year) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
