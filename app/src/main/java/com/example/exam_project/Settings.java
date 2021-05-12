package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Settings extends AppCompatActivity {
    private ImageView backToMainMenu;
    private Button resetProgressBtn, updateAccountBtn, deteleAccountBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backToMainMenu = findViewById(R.id.backToMainMenu);
        resetProgressBtn = findViewById(R.id.resetProgressBtn);
        updateAccountBtn = findViewById(R.id.updateAccountBtn);
        deteleAccountBtn = findViewById(R.id.deteleAccountBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        resetProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first an alert because you will need to do everything from 0
                Intent intent = new Intent(Settings.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        updateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // te va duce la un activity asemanator cu signup, doar ca iti updatezi datele
                Intent intent = new Intent(Settings.this, UpdateAccount.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        deteleAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first an alert because it is a dangerous thing to do
                Intent intent = new Intent(Settings.this, Signup.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}