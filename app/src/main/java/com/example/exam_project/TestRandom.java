package com.example.exam_project;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRandom extends AppCompatActivity {
    private ImageView backToMainMenu;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_grila);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setFunctionality();
        // TODO: points
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setFunctionality() {
        JSONParser jsonParser = new JSONParser();
        JSONArray grile = jsonParser.parseJSON(TestRandom.this);
        for (int i = 0; i < grile.length(); i++) {
            setMenuGrile(i + 1, grile);
            // aici se pot da date?
        }
// aici cand apasa pe unul din nivele
    }

    @SuppressLint("SetTextI18n")
    public List<Integer> setGrilaThings(JSONArray grile, int nrIntrebare, TextView levelxGrila, TextView enuntGrila, TextView answerA, TextView answerB, TextView answerC) throws JSONException {
        // SE IA DIN JSON CE TREBUIE SA SE PUNA AICI
        levelxGrila.setText("Test");
        // aici o sa se ia random
        int levelRandom = (int) (Math.random() * grile.length());
        int intrRandom = (int) (Math.random() * grile.getJSONArray(levelRandom).length());
        enuntGrila.setText((nrIntrebare + 1) + grile.getJSONArray(levelRandom).getJSONObject(intrRandom).getString("intrebare"));
        answerA.setText(grile.getJSONArray(levelRandom).getJSONObject(intrRandom).getJSONArray("raspunsuri").getJSONObject(0).getString("a"));
        answerB.setText(grile.getJSONArray(levelRandom).getJSONObject(intrRandom).getJSONArray("raspunsuri").getJSONObject(0).getString("b"));
        answerC.setText(grile.getJSONArray(levelRandom).getJSONObject(intrRandom).getJSONArray("raspunsuri").getJSONObject(0).getString("c"));
        List<Integer> levelIntrebare = new ArrayList<>();
        levelIntrebare.add(levelRandom);
        levelIntrebare.add(intrRandom);
        return levelIntrebare;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setMenuGrile(int levelNr, JSONArray grile) {  // parametru: level si in fct de nivel te duce la ce trebuie
        // TODO: sa te duca la o grila si sa se transmita niste parametrii necesari pt acea grila
        // TODO: sa se verifice la ce nivel este
        final int[] nrIntrebare = {0};
        setContentView(R.layout.template_grila);
        TextView levelxGrila = findViewById(R.id.levelxGrila);
        TextView enuntGrila = findViewById(R.id.enuntGrila);
        RadioGroup radioGroupGrile = findViewById(R.id.radioGroupGrile);
        // cu asta se verifica care din raspunsuri a fost ales
        RadioButton answerA = findViewById(R.id.answerA);
        RadioButton answerB = findViewById(R.id.answerB);
        RadioButton answerC = findViewById(R.id.answerC);
        TextView points = findViewById(R.id.points);
        ImageView backToGrile = findViewById(R.id.backToGrile);
        Button nextGrila = findViewById(R.id.nextGrila);
        // points se ia din baza de date + level
        // TODO: ceva fisier cu enunturi si answers
        points.setText("1000"); // asta trebuie luat din baza de date
        List<Integer> levelIntr = new ArrayList<>();
        try {
            levelIntr = setGrilaThings(grile, nrIntrebare[0], levelxGrila, enuntGrila, answerA, answerB, answerC);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        backToGrile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TestRandom.this)
                        .setTitle("Back to Main Menu")
                        .setMessage("Are you sure you want to go back? You will lose progress!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(TestRandom.this, "Too bad...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TestRandom.this, MainMenu.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        final int[] countCorrectAnswers = {0};

        List<Integer> finalLevelIntr = levelIntr;
        nextGrila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nrIntrebare[0] == 9) {
                    nextGrila.setText("Finish");
                }
                if (nrIntrebare[0] < 9) {
                    try {
                        int answerRadioButtonId = radioGroupGrile.getCheckedRadioButtonId();
                        if (answerRadioButtonId != -1) {
                            RadioButton answer = findViewById(answerRadioButtonId);
                            if (answer.getText().equals(grile.getJSONArray(finalLevelIntr.get(0)).getJSONObject(finalLevelIntr.get(1)).getString("raspuns corect"))) {
//                                            Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
                                countCorrectAnswers[0]++;
                            } else {
//                                            Toast.makeText(getApplicationContext(), "incorrect answer", Toast.LENGTH_SHORT).show();
                            }
                            answer.setChecked(false);
                        } else {
//                                        Toast.makeText(getApplicationContext(), "missing answer", Toast.LENGTH_SHORT).show();
                            // TODO: ALERTA CU NU AI BIFAT NIMIC? esti sigur ca vrei sa continui?
                        }
                        radioGroupGrile.clearCheck();
                        nrIntrebare[0]++;
                        setGrilaThings(grile, nrIntrebare[0], levelxGrila, enuntGrila, answerA, answerB, answerC);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // TODO: SE PUNE LA STATUS
                    double punctaj = (double) countCorrectAnswers[0] / 5 * 100;
                    int points = countCorrectAnswers[0] * 20;
                    System.out.println("NOPE NU NU NU " + punctaj);
                    //adaug in db (rest)

                    String url = "https://examnet.000webhostapp.com/status.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                        Toast.makeText(Signup.this, response.trim(), Toast.LENGTH_LONG).show();
                                    System.out.println(response.trim());

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(Signup.this, error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                    ) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode



                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                            Date date = new Date(System.currentTimeMillis());
                            System.out.println(formatter.format(date));
                            params.put("username", pref.getString("username", ""));
                            params.put("category","Test");
                            params.put("date", formatter.format(date));
                            params.put("points", String.valueOf(punctaj));
                                //category -> test, data, punctaj -> status
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(TestRandom.this);
                    requestQueue.add(stringRequest);
//                        boolean success = databaseHelper.addUser(new_user);
//                        if (success) {
//                            Log.d("aaaa", "user added");
//                        } else {
//                            Log.d("aaaa", "user NOT added");
//
//                        }
                    // pana aici request

                    url = "https://examnet.000webhostapp.com/pointsAdd.php";
                    stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                        Toast.makeText(Signup.this, response.trim(), Toast.LENGTH_LONG).show();
                                    System.out.println(response.trim());

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(Signup.this, error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                    ) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode



                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", pref.getString("username", ""));
                            params.put("points", String.valueOf(points));
                            //category -> test, data, punctaj -> status
                            return params;
                        }
                    };

                    requestQueue = Volley.newRequestQueue(TestRandom.this);
                    requestQueue.add(stringRequest);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("points", points);
                    editor.apply();




                    // todo: -> asta o sa se puna in statistici : punctajul (punctaj), data si categoria: tabel in bd
                    //  cu data si cat % a fct + categoria, care aici e Grila
                    //  POTI REFACE UN NIVEL SI PUNCTAJUL SE VA REFACE, CHIAR SI IN STATISTICI
                    if (punctaj < 50.0) {
                        Intent intent = new Intent(TestRandom.this, Lost.class);
                        startActivity(intent);
                    } else {
                        setContentView(R.layout.win_template);
                        Intent intent = new Intent(TestRandom.this, Winner.class);
                        startActivity(intent);
                    }
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });
    }

    // TODO: backButton -> atunci cand e in template_grila ca si contentView, atunci sa te duca la activity_grile_main
    // TODo: daca esti in activity_grile_main, nu-ti mai pasa ca o sa te duca la MainMenu

    @Override
    public void onBackPressed() {

    }
}