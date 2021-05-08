package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    private EditText username, email, password, confirmPass;
    private Button signupBtn;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);
        signupBtn = findViewById(R.id.signupBtn);
        login = findViewById(R.id.goToLogin);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaaa", username.getText().toString() + " " + email.getText().toString()
                        + " " + password.getText().toString() + " " + confirmPass.getText().toString());
                if (password.getText().equals(confirmPass.getText())) {
                    Log.d("aaaa", "parolele nu coincid");
                    // TODO: put data in db before continue
                    Intent intent = new Intent(Signup.this, MainMenu.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Log.d("aaaa", "parolele trb sa coincida");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}