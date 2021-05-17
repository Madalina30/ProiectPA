package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
                DatabaseHelper databaseHelper = new DatabaseHelper(Settings.this);

                User dummy = new User();

                new AlertDialog.Builder(Settings.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                databaseHelper.deleteUser(dummy);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                pref.edit().putBoolean("isLogged", false).apply();
                                Toast.makeText(Settings.this, "Too bad...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Settings.this, Login.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                // first an alert because it is a dangerous thing to do

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                pref.edit().putBoolean("isLogged", false).apply();
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}