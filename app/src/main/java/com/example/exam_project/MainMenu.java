package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;

import java.util.function.LongConsumer;

public class MainMenu extends AppCompatActivity {
    private ImageView settings;
    private TextView points, username;
    private Button grile, probleme, randomTest, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setData();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        username.setText(pref.getString("username", "")); // Storing string

        buttonsClick();
    }

    public void setData() {
        settings = findViewById(R.id.settingsBtn);
        points = findViewById(R.id.points);  // the points are set in classes
        username = findViewById(R.id.usernameInGame);
        grile = findViewById(R.id.grileBtn);
        probleme = findViewById(R.id.problemeBtn);
        randomTest = findViewById(R.id.randomTestBtn);
        status = findViewById(R.id.statusBtn);
    }

    public TextView getPoints() {
        return points;
    }

    public void setPoints(TextView points) {
        this.points = points;
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(TextView username) {
        this.username = username;
    }

    public void buttonsClick() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to settings activity
                Intent intent = new Intent(MainMenu.this, Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        grile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to grile activity
                REST rest = new REST();
                rest.getter();
//                Intent intent = new Intent(MainMenu.this, GrileMain.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        probleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to probleme activity
                Intent intent = new Intent(MainMenu.this, Probleme.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        randomTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to randomTest activity
                Intent intent = new Intent(MainMenu.this, TestRandom.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to status activity
//                Toast.makeText(MainMenu.this, "status", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainMenu.this, Status.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}