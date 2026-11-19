package com.example.comstudyplanner.models;

public class University {

    private int id;
    private String university_name;

    public int getId() {
        return id;
    }

    public String getUniversity_name() {
        return university_name;
    }

    @Override
    public String toString() {
        return university_name;
    }
}