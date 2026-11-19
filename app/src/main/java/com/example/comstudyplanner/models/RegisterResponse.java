package com.example.comstudyplanner.models;

public class RegisterResponse {

    private String status;
    private String message;
    private int user_id; // ✅ ADD THIS

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getUser_id() {   // ✅ ADD THIS
        return user_id;
    }
}