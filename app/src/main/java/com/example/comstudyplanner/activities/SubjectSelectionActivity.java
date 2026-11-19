package com.example.comstudyplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.adapters.SubjectAdapter;
import com.example.comstudyplanner.models.SaveSubjectResponse;
import com.example.comstudyplanner.models.Subject;
import com.example.comstudyplanner.network.RetrofitClient;
import com.example.comstudyplanner.utils.SessionManager;
import com.example.comstudyplanner.models.StudentProfileResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.ResponseBody;
import java.io.IOException;

public class SubjectSelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerSubjects;
    private Button btnSaveSubjects;
    private SubjectAdapter adapter;
    private int courseId;
    private int semesterId;

    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection);

        recyclerSubjects = findViewById(R.id.recyclerSubjects);
        btnSaveSubjects = findViewById(R.id.btnSaveSubjects);

        recyclerSubjects.setLayoutManager(new LinearLayoutManager(this));

        session = new SessionManager(this);

        loadStudentProfile();

        btnSaveSubjects.setOnClickListener(v -> saveSubjects());
    }

    private void loadStudentProfile() {

        RetrofitClient.getApiService()
                .getStudentProfile(session.getUserId())
                .enqueue(new Callback<StudentProfileResponse>() {

                    @Override
                    public void onResponse(Call<StudentProfileResponse> call,
                                           Response<StudentProfileResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {

                            courseId = response.body().getData().getCourse_id();

                            semesterId = response.body().getData().getSemester_id();

                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    "courseId = " + courseId +
                                            "\nsemesterId = " + semesterId,
                                    Toast.LENGTH_LONG
                            ).show();

                            loadSubjects();

                        } else {

                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    "Unable to load profile.",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<StudentProfileResponse> call,
                                          Throwable t) {

                        Toast.makeText(
                                SubjectSelectionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }
    private void loadSubjects() {

        RetrofitClient.getApiService()
                .getSubjects(courseId, semesterId)
                .enqueue(new Callback<List<Subject>>() {

                    @Override
                    public void onResponse(Call<List<Subject>> call,
                                           Response<List<Subject>> response) {

                        if (!response.isSuccessful()) {
                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    "HTTP " + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                            return;
                        }

                        List<Subject> list = response.body();

                        if (list == null) {
                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    "Response is NULL",
                                    Toast.LENGTH_LONG
                            ).show();
                            return;
                        }

                        Toast.makeText(
                                SubjectSelectionActivity.this,
                                "course=" + courseId +
                                        " semester=" + semesterId +
                                        "\nsize=" + list.size(),
                                Toast.LENGTH_LONG
                        ).show();

                        adapter = new SubjectAdapter(list);
                        recyclerSubjects.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Subject>> call, Throwable t) {
                        Toast.makeText(
                                SubjectSelectionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void saveSubjects() {

        if (adapter == null) {
            Toast.makeText(this,
                    "Subjects are not loaded yet.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = session.getUserId();

        List<Subject> subjects = adapter.getSubjects();

        int totalSubjects = subjects.size();

        final int[] savedCount = {0};

        for (Subject subject : subjects) {

            if (subject.getExam_date() == null ||
                    subject.getExam_date().isEmpty()) {

                Toast.makeText(
                        this,
                        "Please select exam date for "
                                + subject.getSubject_name(),
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            RetrofitClient.getApiService()
                    .saveUserSubject(
                            userId,
                            subject.getId(),
                            subject.getDifficulty(),
                            subject.getExam_date()
                    )
                    .enqueue(new Callback<SaveSubjectResponse>() {

                        @Override
                        public void onResponse(Call<SaveSubjectResponse> call,
                                               Response<SaveSubjectResponse> response) {

                            if (response.isSuccessful()) {

                                savedCount[0]++;

                                if (savedCount[0] == totalSubjects) {

                                    generateStudyPlan(userId);

                                }

                            } else {

                                Toast.makeText(
                                        SubjectSelectionActivity.this,
                                        "Failed to save subject.",
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<SaveSubjectResponse> call,
                                              Throwable t) {

                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    });

        }

    }

    private void generateStudyPlan(int userId) {

        RetrofitClient.getApiService()
                .generateStudyPlan(userId)
                .enqueue(new Callback<SaveSubjectResponse>() {

                    @Override
                    public void onResponse(Call<SaveSubjectResponse> call,
                                           Response<SaveSubjectResponse> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    "Study Plan Generated Successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                            startActivity(
                                    new Intent(
                                            SubjectSelectionActivity.this,
                                            PlannerActivity.class
                                    )
                            );

                            finish();

                        } else {

                            Toast.makeText(
                                    SubjectSelectionActivity.this,
                                    "Unable to generate study plan.",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<SaveSubjectResponse> call,
                                          Throwable t) {

                        Toast.makeText(
                                SubjectSelectionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}