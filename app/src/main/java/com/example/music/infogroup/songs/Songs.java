package com.example.music.infogroup.songs;


public class Songs {
    private	int	id;
    private	int	g_id;
    private String name;
    private String length;

    public Songs(String name, String length, int g_id) {
        this.g_id = g_id;
        this.name = name;
        this.length = length;
    }

    public Songs(int id, int g_id, String name, String length) {
        this.id = id;
        this.g_id = g_id;
        this.name = name;
        this.length = length;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
