package com.example.music.infogroup.composition;


public class Composition {
    private	int	id;
    private	int	g_id;
    private String surname;
    private String forename;
    private String role;

    public Composition(String surname, String forename, String role, int g_id) {
        this.g_id = g_id;
        this.surname = surname;
        this.forename = forename;
        this.role = role;

    }

    public Composition(int id, int g_id, String surname, String forename, String role) {
        this.id = id;
        this.g_id = g_id;
        this.surname = surname;
        this.forename = forename;
        this.role = role;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
