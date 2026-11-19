package com.example.comstudyplanner.activities;

/*
 NOTE:
 This is a starter replacement for RegisterActivity that matches the new
 database design. It intentionally contains TODO sections for loading
 universities/courses/semesters using Retrofit.

 Replace your old RegisterActivity with this file and then implement the
 TODO methods.
*/

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.models.Course;
import com.example.comstudyplanner.models.RegisterResponse;
import com.example.comstudyplanner.models.Semester;
import com.example.comstudyplanner.models.University;
import com.example.comstudyplanner.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etHours;
    private Spinner spUniversity, spCourse, spSemester;
    private Button btnRegister;
    private TextView tvLoginLink;

    private University selectedUniversity;
    private Course selectedCourse;
    private Semester selectedSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etHours = findViewById(R.id.etHours);

        spUniversity = findViewById(R.id.spUniversity);
        spCourse = findViewById(R.id.spCourse);
        spSemester = findViewById(R.id.spSemester);

        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        loadUniversities();
        loadSemesters();

        btnRegister.setOnClickListener(v -> register());

        tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void loadUniversities() {
        RetrofitClient.getApiService().getUniversities().enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ArrayAdapter<University> adapter=new ArrayAdapter<>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,response.body());
                    spUniversity.setAdapter(adapter);
                    spUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override public void onItemSelected(AdapterView<?> p, android.view.View v,int pos,long id){
                            selectedUniversity=(University)p.getItemAtPosition(pos);
                            loadCourses(selectedUniversity.getId());
                        }
                        @Override public void onNothingSelected(AdapterView<?> p){}
                    });
                }
            }
            @Override public void onFailure(Call<List<University>> call, Throwable t){
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCourses(int universityId){
        RetrofitClient.getApiService().getCourses(universityId).enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ArrayAdapter<Course> adapter=new ArrayAdapter<>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,response.body());
                    spCourse.setAdapter(adapter);
                    spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override public void onItemSelected(AdapterView<?> p, android.view.View v,int pos,long id){
                            selectedCourse=(Course)p.getItemAtPosition(pos);
                        }
                        @Override public void onNothingSelected(AdapterView<?> p){}
                    });
                }
            }
            @Override public void onFailure(Call<List<Course>> call, Throwable t){}
        });
    }

    private void loadSemesters(){
        RetrofitClient.getApiService().getSemesters().enqueue(new Callback<List<Semester>>() {
            @Override
            public void onResponse(Call<List<Semester>> call, Response<List<Semester>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ArrayAdapter<Semester> adapter=new ArrayAdapter<>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,response.body());
                    spSemester.setAdapter(adapter);
                    spSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override public void onItemSelected(AdapterView<?> p, android.view.View v,int pos,long id){
                            selectedSemester=(Semester)p.getItemAtPosition(pos);
                        }
                        @Override public void onNothingSelected(AdapterView<?> p){}
                    });
                }
            }
            @Override public void onFailure(Call<List<Semester>> call, Throwable t){}
        });
    }

    private void register(){
        String name=etName.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString();
        int hours=etHours.getText().toString().isEmpty()?0:Integer.parseInt(etHours.getText().toString());

        if(name.isEmpty()||email.isEmpty()||password.isEmpty()||
                selectedUniversity==null||selectedCourse==null||selectedSemester==null){
            Toast.makeText(this,"Please complete all fields.",Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApiService().registerUser(
                name,
                email,
                password,
                selectedUniversity.getId(),
                selectedCourse.getId(),
                selectedSemester.getId(),
                hours
        ).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    if("success".equalsIgnoreCase(response.body().getStatus())){
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t){
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
