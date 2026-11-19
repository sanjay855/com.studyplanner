package com.example.comstudyplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.adapters.StudyPlanAdapter;
import com.example.comstudyplanner.models.StudyPlanResponse;
import com.example.comstudyplanner.network.RetrofitClient;
import com.example.comstudyplanner.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlannerActivity extends AppCompatActivity {

    private RecyclerView recyclerStudyPlan;
    private ProgressBar progressStudy;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        recyclerStudyPlan = findViewById(R.id.recyclerStudyPlan);
        progressStudy = findViewById(R.id.progressStudy);
        tvProgress = findViewById(R.id.tvProgress);

        recyclerStudyPlan.setLayoutManager(new LinearLayoutManager(this));

        loadStudyPlan();
    }

    private void loadStudyPlan() {

        SessionManager session = new SessionManager(this);

        RetrofitClient.getApiService()
                .getStudyPlan(session.getUserId())
                .enqueue(new Callback<StudyPlanResponse>() {

                    @Override
                    public void onResponse(Call<StudyPlanResponse> call,
                                           Response<StudyPlanResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            StudyPlanAdapter adapter =
                                    new StudyPlanAdapter(
                                            response.body().getData(),
                                            plan -> {

                                                Intent intent = new Intent(
                                                        PlannerActivity.this,
                                                        StudyTimerActivity.class
                                                );

                                                intent.putExtra(
                                                        "subject_name",
                                                        plan.getSubject_name()
                                                );

                                                intent.putExtra(
                                                        "minutes",
                                                        plan.getAllocated_minutes()
                                                );

                                                intent.putExtra(
                                                        "plan_id",
                                                        plan.getId()
                                                );

                                                startActivity(intent);

                                            });

                            recyclerStudyPlan.setAdapter(adapter);

                            int completed = 0;

                            for (int i = 0;
                                 i < response.body().getData().size();
                                 i++) {

                                if (response.body()
                                        .getData()
                                        .get(i)
                                        .getCompleted() == 1) {

                                    completed++;

                                }

                            }

                            int total =
                                    response.body().getData().size();

                            int percent = 0;

                            if (total > 0) {

                                percent =
                                        (completed * 100) / total;

                            }

                            tvProgress.setText(
                                    "Today's Progress : "
                                            + completed
                                            + " / "
                                            + total
                                            + " Subjects Completed"
                            );

                            progressStudy.setProgress(percent);

                        }

                    }

                    @Override
                    public void onFailure(Call<StudyPlanResponse> call,
                                          Throwable t) {

                        Toast.makeText(
                                PlannerActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }


                });

    }

    @Override
    protected void onResume() {

        super.onResume();

        loadStudyPlan();

    }

}