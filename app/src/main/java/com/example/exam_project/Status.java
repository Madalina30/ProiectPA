package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class Status extends AppCompatActivity {
    private ImageView backToMainMenu;
    private ScrollView statusScroll;

    public ScrollView getStatusScroll() {
        return statusScroll;
    }

    public void setStatusScroll(ScrollView statusScroll) {
        this.statusScroll = statusScroll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        backToMainMenu = findViewById(R.id.backToMainMenu);
        statusScroll = findViewById(R.id.statusScroll);

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Status.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        // TODO: when a game is done, the status scroll will receive a status_template object inside, with variables changed
        // TODO: adapter for scroll view to do this

    }
}