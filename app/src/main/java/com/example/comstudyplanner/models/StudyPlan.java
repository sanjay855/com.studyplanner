package com.example.comstudyplanner.models;

public class StudyPlan {

    private int id;
    private int subject_id;
    private String subject_name;
    private int allocated_minutes;
    private int completed;

    public int getId() {
        return id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public int getAllocated_minutes() {
        return allocated_minutes;
    }

    public int getCompleted() {
        return completed;
    }
}