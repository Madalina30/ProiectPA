package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private EditText useremail, password;
    private Button loginBtn;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        useremail = findViewById(R.id.useremail);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.goToSignup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaaa", useremail.getText().toString() + " " + password.getText().toString());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Login.this, Signup.class));
                } catch (Exception e) {
                    Log.d("AAAX", e.toString());
                }

            }
        });
    }
}