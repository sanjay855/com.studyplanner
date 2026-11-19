package com.example.comstudyplanner.models;

import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("id")
    private int id;

    @SerializedName("subject_code")
    private String subjectCode;

    @SerializedName("subject_name")
    private String subjectName;

    @SerializedName("credits")
    private int credits;

    private String difficulty = "Easy";
    private String exam_date = "";
    private boolean saved = false;

    public int getId() {
        return id;
    }

    public String getSubject_code() {
        return subjectCode;
    }

    public String getSubject_name() {
        return subjectName;
    }

    public int getCredits() {
        return credits;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @Override
    public String toString() {
        return subjectName;
    }
}