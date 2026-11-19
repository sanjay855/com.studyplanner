package com.example.comstudyplanner.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SessionManager session = new SessionManager(this);

        new android.os.Handler().postDelayed(() -> {

            if (session.isLoggedIn()) {

                Intent intent = new Intent(
                        SplashActivity.this,
                        DashboardActivity.class
                );

                startActivity(intent);

            } else {

                Intent intent = new Intent(
                        SplashActivity.this,
                        LoginActivity.class
                );

                startActivity(intent);

            }

            finish();

        }, 2000);

    }
}