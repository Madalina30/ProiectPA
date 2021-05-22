package com.example.exam_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateAccount extends AppCompatActivity {
    private ImageView backToSettings;
    private EditText username, email, oldPass, newPass;
    private Button updateAccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backToSettings = findViewById(R.id.backToSettings);
        username = findViewById(R.id.newUsername);
        email = findViewById(R.id.newEmail);
        oldPass = findViewById(R.id.oldPassword);
        newPass = findViewById(R.id.newPassword);
        updateAccBtn = findViewById(R.id.signupBtn);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        username.setText(pref.getString("username", ""));
        email.setText(pref.getString("email", ""));


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
                if (oldPass.getText().toString().equals(pref.getString("password", ""))) {
                    if (oldPass.getText().toString().equals(newPass.getText().toString())) {
                        Toast.makeText(UpdateAccount.this, "Parola noua coincide cu cea veche", Toast.LENGTH_SHORT).show();
                    } else {
                        new AlertDialog.Builder(UpdateAccount.this)
                                .setTitle("Update Account")
                                .setMessage("Are you sure you want to change your password?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        String url = "https://examnet.000webhostapp.com/updatePass.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        System.out.println(response.trim());
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                    }
                                                }
                                        ) {
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode


                                            @Nullable
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<>();
                                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                                                Date date = new Date(System.currentTimeMillis());
                                                System.out.println(formatter.format(date));
                                                params.put("username", pref.getString("username", ""));
                                                params.put("password", newPass.getText().toString());
                                                return params;
                                            }
                                        };

                                        RequestQueue requestQueue = Volley.newRequestQueue(UpdateAccount.this);
                                        requestQueue.add(stringRequest);

                                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("password", String.valueOf(newPass)).apply();

                                        Intent intent = new Intent(UpdateAccount.this, Settings.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else {
                    Toast.makeText(UpdateAccount.this, "Introdu parola veche corecta!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}