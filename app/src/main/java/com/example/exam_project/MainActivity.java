package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.androidnetworking.AndroidNetworking;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Handler handler = new Handler();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (pref.getBoolean("isLogged", false)) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent mInHome = new Intent(MainActivity.this, MainMenu.class);
                    MainActivity.this.startActivity(mInHome);
                    MainActivity.this.finish();
                }
            }, 2000);
        } else {
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent mInHome = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(mInHome);
                    MainActivity.this.finish();
                }
            }, 2000);
        }

    }
    @Override
    public void onBackPressed() {

    }
}