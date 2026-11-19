package com.example.comstudyplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.models.StudentProfileResponse;
import com.example.comstudyplanner.network.RetrofitClient;
import com.example.comstudyplanner.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private TextView tvStudyProgress;
    private TextView tvUniversity;
    private TextView tvCourse;
    private TextView tvSemester;

    private ProgressBar progressStudy;

    private Button btnSubjects;
    private Button btnPlanner;
    private Button btnAI;
    private Button btnProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SessionManager session = new SessionManager(this);

        if (!session.isLoggedIn()) {

            Intent intent = new Intent(this, LoginActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();

            return;
        }

        tvWelcome = findViewById(R.id.tvWelcome);
        tvStudyProgress = findViewById(R.id.tvStudyProgress);
        tvUniversity = findViewById(R.id.tvUniversity);
        tvCourse = findViewById(R.id.tvCourse);
        tvSemester = findViewById(R.id.tvSemester);

        btnLogout = findViewById(R.id.btnLogout);

        progressStudy = findViewById(R.id.progressStudy);

        btnSubjects = findViewById(R.id.btnSubjects);
        btnPlanner = findViewById(R.id.btnPlanner);
        btnAI = findViewById(R.id.btnAI);
        btnProgress = findViewById(R.id.btnProgress);

        //SessionManager session = new SessionManager(this);

        tvWelcome.setText("Welcome, " + session.getUserName());

        loadProfile(session.getUserId());

        btnSubjects.setOnClickListener(v ->
                startActivity(new Intent(this, SubjectSelectionActivity.class)));

        btnPlanner.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            DashboardActivity.this,
                            PlannerActivity.class
                    )
            );

        });

        btnAI.setOnClickListener(v ->
                startActivity(new Intent(this, AiChatActivity.class)));

        btnProgress.setOnClickListener(v ->
                startActivity(new Intent(this, ProgressActivity.class)));

        btnLogout.setOnClickListener(v -> {

            //SessionManager session = new SessionManager(DashboardActivity.this);

            session.logout();

            Intent intent = new Intent(
                    DashboardActivity.this,
                    LoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();

        });
    }

    private void loadProfile(int userId) {

        RetrofitClient.getApiService()
                .getStudentProfile(userId)
                .enqueue(new Callback<StudentProfileResponse>() {

                    @Override
                    public void onResponse(Call<StudentProfileResponse> call,
                                           Response<StudentProfileResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            tvUniversity.setText(
                                    "University : " +
                                            response.body().getData().getUniversity_name());

                            tvCourse.setText(
                                    "Course : " +
                                            response.body().getData().getCourse_name());

                            tvSemester.setText(
                                    "Semester : " +
                                            response.body().getData().getSemester_number());

                            progressStudy.setProgress(0);

                            tvStudyProgress.setText(
                                    "0 / "
                                            + response.body().getData().getDaily_study_hours()
                                            + " Hours");

                        }

                    }

                    @Override
                    public void onFailure(Call<StudentProfileResponse> call,
                                          Throwable t) {

                        Toast.makeText(
                                DashboardActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });

    }

}