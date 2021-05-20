package com.example.exam_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private EditText username, email, password, confirmPass;
    private Button signupBtn;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);
        signupBtn = findViewById(R.id.signupBtn);
        login = findViewById(R.id.goToLogin);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper databaseHelper = new DatabaseHelper(Signup.this);

                String user = username.getText().toString();
                String email_user = email.getText().toString();
                String pass = password.getText().toString();
                String confPass = confirmPass.getText().toString();

                if(user.equals("") || email_user.equals("") || pass.equals("") || confPass.equals(""))
                {
                    Toast.makeText(Signup.this, "Empty fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!pass.equals(confPass)) {
                        Toast.makeText(Signup.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    } else {
                        User new_user = new User(-1, user, pass, email_user);

                        Log.d("aaaa", user + " " + email_user + " " + pass + " " + confPass);
//                        boolean success = databaseHelper.addUser(new_user);

                        Toast.makeText(Signup.this, "Success!", Toast.LENGTH_SHORT).show();

                        // first verify if user exists
                        String url = "https://exam-net.herokuapp.com/exams/examcontroller.php?view=all";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(Signup.this, response.trim(), Toast.LENGTH_SHORT).show();

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(Signup.this, error.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                        ) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", user);
                                params.put("password", pass);
                                params.put("email", email_user);

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
                        requestQueue.add(stringRequest);

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", user); // in loc de user sa fie o cautare in baza de date pt acel user in fct de email

                        editor.putString("email", email_user);
                        editor.putBoolean("isLogged", true);
                        editor.putInt("points", 0);
                        editor.apply();

                        Intent intent = new Intent(Signup.this, MainMenu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
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
    @Override
    public void onBackPressed() {

    }
}