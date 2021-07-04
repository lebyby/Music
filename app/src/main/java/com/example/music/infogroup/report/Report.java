package com.example.music.infogroup.report;


public class Report {
    private	int	id;
    private	int	g_id;
    private String city;
    private String count;

    public Report(String city, String count, int g_id) {
        this.g_id = g_id;
        this.city = city;
        this.count = count;
    }

    public Report(int id, int g_id, String city, String count) {
        this.id = id;
        this.g_id = g_id;
        this.city = city;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getG_Id() {
        return g_id;
    }

    public void setG_id(String name) {
        this.g_id = g_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
