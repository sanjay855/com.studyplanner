package com.example.comstudyplanner.models;

public class Semester {

    private int id;
    private int semester_number;

    public int getId() {
        return id;
    }

    public int getSemester_number() {
        return semester_number;
    }

    @Override
    public String toString() {
        return "Semester " + semester_number;
    }
}