package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Winner extends AppCompatActivity {
    private TextView pointsWon;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_template);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pointsWon = findViewById(R.id.pointsWon);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        pointsWon.setText("Congrats! You got "+ pref.getInt("pointsWonOrLost", 0) +" points!");

        Button chooseLevel = findViewById(R.id.chooseLevel);
        Button goToMainMenu = findViewById(R.id.goToMainMenu);
        chooseLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Winner.this, GrileMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        goToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Winner.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}