package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateAccount extends AppCompatActivity {
    private ImageView backToSettings;
    private EditText username, email, oldPass, newPass;
    private Button updateAccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        backToSettings = findViewById(R.id.backToSettings);
        username = findViewById(R.id.newUsername);
        email = findViewById(R.id.newEmail);
        oldPass = findViewById(R.id.oldPassword);
        newPass = findViewById(R.id.newPassword);
        updateAccBtn = findViewById(R.id.signupBtn);

        backToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateAccount.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        updateAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: mai intai vede daca ai modificat ceva si daca da, atunci da update in baza de date la cele modificate
                //  plus oldPass trb sa coincida cu ce e in baza de date la userul respectiv
                Intent intent = new Intent(UpdateAccount.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}