package com.example.comstudyplanner.models;

public class Course {

    private int id;
    private String course_name;

    public int getId() {
        return id;
    }

    public String getCourse_name() {
        return course_name;
    }

    @Override
    public String toString() {
        return course_name;
    }
}