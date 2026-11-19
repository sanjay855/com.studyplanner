package com.example.comstudyplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.comstudyplanner.models.ProgressResponse;
import com.example.comstudyplanner.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.models.ProgressResponse;

import retrofit2.Call;

public class StudyTimerActivity extends AppCompatActivity {

    private TextView txtSubject;
    private TextView txtTime;
    private ProgressBar progressBar;

    private Button btnPause;
    private Button btnFinish;

    private CountDownTimer timer;

    private long timeLeft;

    private long totalTime;

    private boolean isPaused = false;

    private int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_timer);

        txtSubject = findViewById(R.id.txtSubject);
        txtTime = findViewById(R.id.txtTime);
        progressBar = findViewById(R.id.progressTimer);

        btnPause = findViewById(R.id.btnPause);
        btnFinish = findViewById(R.id.btnFinish);

        Intent intent = getIntent();

        String subject = intent.getStringExtra("subject_name");

        int minutes = intent.getIntExtra("minutes", 30);

        planId = intent.getIntExtra("plan_id", 0);

        txtSubject.setText(subject);

        totalTime = minutes * 60L * 1000L;

        timeLeft = totalTime;

        progressBar.setMax(minutes * 60);

        startTimer();

        btnPause.setOnClickListener(v -> {

            if (isPaused) {

                startTimer();

                btnPause.setText("Pause");

            } else {

                timer.cancel();

                btnPause.setText("Resume");

            }

            isPaused = !isPaused;

        });

        btnFinish.setOnClickListener(v -> finishStudy());

    }

    private void startTimer() {

        timer = new CountDownTimer(timeLeft,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timeLeft = millisUntilFinished;

                int seconds = (int)(millisUntilFinished/1000);

                int minutes = seconds/60;

                int remain = seconds%60;

                txtTime.setText(
                        String.format("%02d:%02d",minutes,remain)
                );

                progressBar.setProgress(
                        progressBar.getMax()-seconds
                );

            }

            @Override
            public void onFinish() {

                finishStudy();

            }

        };

        timer.start();

    }

    private void finishStudy() {

        if (timer != null) {
            timer.cancel();
        }

        RetrofitClient.getApiService()
                .updateStudyProgress(planId)
                .enqueue(new Callback<ProgressResponse>() {

                    @Override
                    public void onResponse(Call<ProgressResponse> call,
                                           Response<ProgressResponse> response) {

                        Toast.makeText(
                                StudyTimerActivity.this,
                                "Study Session Completed",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent = new Intent(
                                StudyTimerActivity.this,
                                PlannerActivity.class
                        );

                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                        );

                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<ProgressResponse> call,
                                          Throwable t) {

                        Toast.makeText(
                                StudyTimerActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

}