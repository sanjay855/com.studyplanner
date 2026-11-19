package com.example.comstudyplanner.models;

public class StudentProfile {

    private int id;
    private String name;

    private int university_id;
    private int course_id;
    private int semester_id;

    private int daily_study_hours;

    private String university_name;
    private String course_name;

    private int semester_number;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUniversity_id() {
        return university_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public int getSemester_id() {
        return semester_id;
    }

    public int getDaily_study_hours() {
        return daily_study_hours;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public int getSemester_number() {
        return semester_number;
    }
}