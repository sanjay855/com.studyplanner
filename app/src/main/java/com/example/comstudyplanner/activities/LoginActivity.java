package com.example.comstudyplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.models.RegisterResponse;
import com.example.comstudyplanner.network.RetrofitClient;
import com.example.comstudyplanner.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // ✅ FIRST

        SessionManager session = new SessionManager(this);

        if (session.isLoggedIn()) {
            Toast.makeText(this, "Welcome back " + session.getUserName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize views
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        // Login button
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call API
            RetrofitClient.getApiService().loginUser(email, password)
                    .enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if ("success".equals(response.body().getStatus())) {

                                Toast.makeText(LoginActivity.this,
                                        "Welcome " + response.body().getMessage(),
                                        Toast.LENGTH_SHORT).show();

                                // 🔥 SAVE SESSION HERE
                                SessionManager session = new SessionManager(LoginActivity.this);
                                session.saveLoginSession(
                                        response.body().getMessage(),   // name
                                        response.body().getUser_id()    // id
                                );

                                // 🔥 MOVE TO DASHBOARD
                                ///Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                ///startActivity(intent);
                                ///finish();

                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,
                                    "Error: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Create account click (REGISTER)
        tvCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}