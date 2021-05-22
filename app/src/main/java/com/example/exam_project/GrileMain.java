package com.example.exam_project;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GrileMain extends AppCompatActivity {
    private ImageView backToMainMenu;
    private ScrollView grileScroll;
    private LinearLayout grileLayout;
    private TextView points;

    public ScrollView getStatusScroll() {
        return grileScroll;
    }

    public void setStatusScroll(ScrollView grileScroll) {
        this.grileScroll = grileScroll;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grile_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setFunctionality();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        points.setText(String.valueOf(pref.getInt("points", 0)));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setFunctionality() {
        backToMainMenu = findViewById(R.id.backToMainMenu);
        grileScroll = findViewById(R.id.grileScroll);
        grileLayout = findViewById(R.id.grileLayout);
        points = findViewById(R.id.points);


        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrileMain.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        JSONParser jsonParser = new JSONParser();
        JSONArray grile = jsonParser.parseJSON(GrileMain.this);
        for (int i = 0; i < grile.length(); i++) {
            setMenuGrile(i + 1, grile);
        }
// aici cand apasa pe unul din nivele
    }

    @SuppressLint("SetTextI18n")
    public void setGrilaThings(JSONArray grile, int nrIntrebare, int levelNr, TextView levelxGrila, TextView enuntGrila, TextView answerA, TextView answerB, TextView answerC) throws JSONException {
        // SE IA DIN JSON CE TREBUIE SA SE PUNA AICI
        levelxGrila.setText("Level " + levelNr);
        // 'question_nr'
        // se repeta unele....
//        int intrRandom = (int) (Math.random()*grile.getJSONArray(levelNr).length());
        enuntGrila.setText((nrIntrebare + 1) + grile.getJSONArray(levelNr - 1).getJSONObject(nrIntrebare).getString("intrebare"));
        answerA.setText(grile.getJSONArray(levelNr - 1).getJSONObject(nrIntrebare).getJSONArray("raspunsuri").getJSONObject(0).getString("a"));
        answerB.setText(grile.getJSONArray(levelNr - 1).getJSONObject(nrIntrebare).getJSONArray("raspunsuri").getJSONObject(0).getString("b"));
        answerC.setText(grile.getJSONArray(levelNr - 1).getJSONObject(nrIntrebare).getJSONArray("raspunsuri").getJSONObject(0).getString("c"));
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setMenuGrile(int levelNr, JSONArray grile) {  // parametru: level si in fct de nivel te duce la ce trebuie
        /// o sa fie un for pana la cate leveluri se gasesc in json
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParamsRel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParamsRel.setMargins(20, 20, 20, 20);

        relativeLayout.setLayoutParams(layoutParamsRel);
        relativeLayout.setBackgroundResource(R.drawable.btn_bkg);
        LinearLayout linearLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutParamsLin = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsLin.addRule(RelativeLayout.CENTER_IN_PARENT);
        linearLayout.setLayoutParams(layoutParamsLin);

        TextView level = new TextView(this);
        LinearLayout.LayoutParams textv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        level.setTextSize(25);
        level.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        level.setText("Level " + levelNr + " - max 100 points"); // level + i
        level.setLayoutParams(textv);
        level.setGravity(Gravity.CENTER);

        linearLayout.addView(level);
        relativeLayout.addView(linearLayout);

        // de la relative layout trebuie sa te duca la grila specifica pentru acel nivel (prima din set - momentan chiar prima, dupa random)

        grileLayout.addView(relativeLayout);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                points.setText(String.valueOf(pref.getInt("points", 0)));
                try {
                    setGrilaThings(grile, nrIntrebare[0], levelNr, levelxGrila, enuntGrila, answerA, answerB, answerC);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                backToGrile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_grile_main);
                        setFunctionality();
                    }
                });
                final int[] countCorrectAnswers = {0};
                nextGrila.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (nrIntrebare[0] == 3) {
                                nextGrila.setText("Finish");
                            }
                            if (nrIntrebare[0] < grile.getJSONArray(levelNr - 1).length() - 1) {
                                try {
                                    System.out.println(nrIntrebare[0] + " " + grile.getJSONArray(levelNr - 1).length());
                                    int answerRadioButtonId = radioGroupGrile.getCheckedRadioButtonId();
                                    if (answerRadioButtonId != -1) {
                                        RadioButton answer = findViewById(answerRadioButtonId);
                                        if (answer.getText().equals(grile.getJSONArray(levelNr - 1).getJSONObject(nrIntrebare[0]).getString("raspuns corect"))) {
//                                            Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_SHORT).show();
                                            countCorrectAnswers[0]++;
                                        } else {
//                                            Toast.makeText(getApplicationContext(), "incorrect answer", Toast.LENGTH_SHORT).show();
                                        }
                                        answer.setChecked(false);
                                    } else {
//                                        Toast.makeText(getApplicationContext(), "missing answer", Toast.LENGTH_SHORT).show();
                                    }
                                    radioGroupGrile.clearCheck();
                                    nrIntrebare[0]++;
                                    setGrilaThings(grile, nrIntrebare[0], levelNr, levelxGrila, enuntGrila, answerA, answerB, answerC);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                double punctaj = (double) countCorrectAnswers[0] / 5 * 100;
                                int points = countCorrectAnswers[0] * 20;
                                // TODO POTI REFACE UN NIVEL SI PUNCTAJUL SE VA REFACE, CHIAR SI IN STATISTICI
//                                System.out.println("NOPE NU NU NU " + punctaj);


                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("pointsWonOrLost", points).apply();
                                points += pref.getInt("points", 0);

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
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                                        Date date = new Date(System.currentTimeMillis());
                                        System.out.println(formatter.format(date));
                                        params.put("username", pref.getString("username", ""));
                                        params.put("category", "Grile");
                                        params.put("date", formatter.format(date));
                                        params.put("points", String.valueOf(punctaj));
                                        //category -> test, data, punctaj -> status
                                        return params;
                                    }
                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(GrileMain.this);
                                requestQueue.add(stringRequest);

                                url = "https://examnet.000webhostapp.com/pointsAdd.php";
                                int finalPoints = points;
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
                                        params.put("points", String.valueOf(finalPoints));
                                        //category -> test, data, punctaj -> status
                                        return params;
                                    }
                                };

                                requestQueue = Volley.newRequestQueue(GrileMain.this);
                                requestQueue.add(stringRequest);

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                editor = pref.edit();
                                editor.putInt("points", points);
                                editor.apply();

                                if (punctaj < 50.0) {

                                    Intent intent = new Intent(GrileMain.this, Lost.class);
                                    startActivity(intent);
                                } else {
                                    setContentView(R.layout.win_template);
                                    Intent intent = new Intent(GrileMain.this, Winner.class);
                                    startActivity(intent);
                                }
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}