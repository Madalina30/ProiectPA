package com.example.exam_project;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText useremail, password;
    private Button loginBtn;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        useremail = findViewById(R.id.useremail);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.goToSignup);
        // db: id, username, email, password, punctaj
        // statistici: id, id_user, categorie, date, %

        // un user da un input - cod!!!! si sa compilam si sa comparam rezultatele sa vedem daca codul e corect
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(Login.this);

                String user = useremail.getText().toString();
                String pass = password.getText().toString();
                Log.d("aaaa", user + " " + pass);

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(Login.this, "Username or password field empty!", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(Login.this, MainMenu.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    AndroidNetworking.get("https://exam-net.herokuapp.com/exams/examcontroller.php?view=all").build().getAsJSONObject(new JSONObjectRequestListener() {

                        @Override
                        public void onResponse(JSONObject response) {
//
                            User user_ex = new User(-1, user, pass, user);
//
                            try {
                                if (databaseHelper.searchUser(user_ex, response) == -1) {
                                    Toast.makeText(Login.this, "Username / Email doesn't exist! Use Sign Up!", Toast.LENGTH_SHORT).show();
                                } else if (databaseHelper.searchUser(user_ex, response) == 1) {
                                    Toast.makeText(Login.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Succes!", Toast.LENGTH_SHORT).show();

                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();

                                    int stop = 0;
                                    JSONArray users_informations = response.getJSONArray("exams");
                                    for (int i = 0; i < users_informations.length() && stop == 0; i++) {
                                        JSONObject username = users_informations.getJSONObject(i);
                                        if (username.get("username").equals(user_ex.getUsername()) || username.get("email").equals(user_ex.getEmail())) {
//                                            System.out.println(username.get("punctaj").getClass());
                                            editor.putInt("points", Integer.parseInt(String.valueOf(username.get("punctaj"))));
                                            if (user.contains("@")) {  // daca se conecteaza cu emailul
                                                editor.putString("username", user_ex.getUsername()); // in loc de user sa fie o cautare in baza de date pt acel user in fct de email
                                                editor.putString("email",user);
                                            } else {  // daca se conecteaza cu usernameul
                                                editor.putString("username", user);
                                                editor.putString("email", user_ex.getEmail());

                                            }

                                            stop = 1;
                                        }
                                    }

                                    editor.putBoolean("isLogged", true);
                                    editor.putString("password",user_ex.getPassword());

                                    editor.apply();
                                    Intent intent = new Intent(Login.this, MainMenu.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            } catch (InterruptedException | JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            System.out.println(anError);
                        }
                    });
//
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}