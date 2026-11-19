package com.example.comstudyplanner.models;

import java.util.List;

public class StudyPlanResponse {

    private String status;
    private List<StudyPlan> data;

    public String getStatus() {
        return status;
    }

    public List<StudyPlan> getData() {
        return data;
    }
}