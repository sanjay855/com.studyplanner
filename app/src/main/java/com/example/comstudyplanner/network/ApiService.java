package com.example.comstudyplanner.network;

import com.example.comstudyplanner.models.Course;
import com.example.comstudyplanner.models.RegisterResponse;
import com.example.comstudyplanner.models.Semester;
import com.example.comstudyplanner.models.University;
import com.example.comstudyplanner.models.Subject;
import com.example.comstudyplanner.models.SaveSubjectResponse;
import java.util.List;
import com.example.comstudyplanner.models.StudentProfileResponse;
import com.example.comstudyplanner.models.StudyPlanResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.example.comstudyplanner.models.ProgressResponse;

public interface ApiService {

    // ---------------- REGISTER ----------------

    @FormUrlEncoded
    @POST("api/register.php")
    Call<RegisterResponse> registerUser(

            @Field("name") String name,

            @Field("email") String email,

            @Field("password") String password,

            @Field("university_id") int universityId,

            @Field("course_id") int courseId,

            @Field("semester_id") int semesterId,

            @Field("daily_study_hours") int hours
    );

    // ---------------- LOGIN ----------------

    @FormUrlEncoded
    @POST("api/login.php")
    Call<RegisterResponse> loginUser(

            @Field("email") String email,

            @Field("password") String password
    );

    @GET("api/getSubjects.php")
    Call<List<Subject>> getSubjects(
            @Query("course_id") int courseId,
            @Query("semester_id") int semesterId
    );

    // ---------------- UNIVERSITIES ----------------

    @GET("api/getUniversities.php")
    Call<List<University>> getUniversities();

    // ---------------- COURSES ----------------

    @GET("api/getCourses.php")
    Call<List<Course>> getCourses(

            @Query("university_id") int universityId
    );

    // ---------------- SEMESTERS ----------------

    @GET("api/getSemesters.php")
    Call<List<Semester>> getSemesters();

// ---------------- INTERFACE ----------------

    @FormUrlEncoded
    @POST("api/saveUserSubjects.php")
    Call<SaveSubjectResponse> saveUserSubject(

            @Field("user_id") int userId,

            @Field("subject_id") int subjectId,

            @Field("difficulty") String difficulty,

            @Field("exam_date") String examDate

    );

    @GET("api/getStudentProfile.php")
    Call<StudentProfileResponse> getStudentProfile(
            @Query("user_id") int userId
    );

    @FormUrlEncoded
    @POST("api/generateStudyPlan.php")
    Call<SaveSubjectResponse> generateStudyPlan(
            @Field("user_id") int userId
    );

    @GET("api/getStudyPlan.php")
    Call<StudyPlanResponse> getStudyPlan(
            @Query("user_id") int userId
    );

    @FormUrlEncoded
    @POST("api/updateStudyProgress.php")
    Call<ProgressResponse> updateStudyProgress(

            @Field("plan_id") int planId

    );

}