package com.example.exam_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Probleme extends AppCompatActivity {
    private ImageView backToMainMenu;
    private ScrollView problemeScroll;
    private LinearLayout layoutProbleme;
    private RelativeLayout problemSample;
    private TextView points;

    public ScrollView getProblemeScroll() {
        return problemeScroll;
    }

    public void setProblemeScroll(ScrollView problemeScroll) {
        this.problemeScroll = problemeScroll;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probleme);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setFunctionality();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        points.setText(String.valueOf(pref.getInt("points", 0)));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setMenuGrile(int levelNr, JSONArray probleme) throws JSONException {  // parametru: level si in fct de nivel te duce la ce trebuie
        /// o sa fie un for pana la cate leveluri se gasesc in json
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParamsRel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParamsRel.setMargins(20, 20, 20, 20);

        relativeLayout.setLayoutParams(layoutParamsRel);
        relativeLayout.setBackgroundResource(R.drawable.btn_bkg);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams layoutParamsLin = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsLin.addRule(RelativeLayout.CENTER_IN_PARENT);
        linearLayout.setLayoutParams(layoutParamsLin);

        TextView problem = new TextView(this);
        LinearLayout.LayoutParams problemLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        problem.setTextSize(25);
        problem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        problem.setText("Problem " + levelNr + " - 100 points"); // level + i
        problem.setLayoutParams(problemLayout);
        problem.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView titlu = new TextView(this);
        LinearLayout.LayoutParams textv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titlu.setTextSize(20);
        titlu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titlu.setText("ceva aici");
        titlu.setText(probleme.getJSONObject(levelNr - 1).getString("titlu")); // level + i
        titlu.setLayoutParams(textv);
        titlu.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(problem);
        linearLayout.addView(titlu);
        relativeLayout.addView(linearLayout);

        // de la relative layout trebuie sa te duca la grila specifica pentru acel nivel (prima din set - momentan chiar prima, dupa random)

        layoutProbleme.addView(relativeLayout);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] nrIntrebare = {0};
                setContentView(R.layout.template_problemaa);
                try {
                    goToProblema("titlu problema", "enunt problema", "rezolvarea", probleme, levelNr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setFunctionality() {
        backToMainMenu = findViewById(R.id.backToMainMenu);
        problemeScroll = findViewById(R.id.grileScroll);
        layoutProbleme = findViewById(R.id.layoutProbleme);
        points = findViewById(R.id.points);

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Probleme.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        JSONParser jsonParser = new JSONParser();
        JSONArray probleme = jsonParser.parseProbleme(Probleme.this);
        for (int i = 0; i < probleme.length(); i++) {
            try {
                setMenuGrile(i + 1, probleme);
                System.out.println("aici ai titlu" + probleme.getJSONObject(i).getString("titlu"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void goToProblema(String titlu, String enunt, String solve, JSONArray probleme, int levelNr) throws JSONException {
        TextView titluProblema = findViewById(R.id.titluProblema);
        TextView enuntProblema = findViewById(R.id.enuntProblema);
        EditText rezolvare = findViewById(R.id.rezolvare);
        TextView points = findViewById(R.id.points);
        ImageView backToProbleme = findViewById(R.id.backToProbleme);
        Button verificarePb = findViewById(R.id.verificarePb);
        // points se ia din baza de date + level
        points.setText("1000");
        setProblemeThings(titluProblema, enuntProblema, probleme.getJSONObject(levelNr - 1).getString("titlu"),
                probleme.getJSONObject(levelNr - 1).getString("intrebare"));
        backToProbleme.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_probleme);
                setFunctionality();
            }
        });
        verificarePb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se ia din json
                System.out.println("aici ai rezolvarea ma" + rezolvare.getText());
                // se ruleaza si se compara cu raspunsul
//                System.out.println(probleme);
                // TODO: aici verifica problema si apare un ecran de loading pt verify
                //  si dupa apare un ecran cu congrats sau cu lost
//                        setContentView(R.layout.verificareProblemLoading);
                // cand a terminat de verificat: setContentView(R.layout.win_template) sau lost in functie de rezultat
                // dar o sa apara raspunsul tau + raspunsul bun daca nu e corect!!!!!
            }
        });
    }

    public void setProblemeThings(TextView titluProblema, TextView enuntProblema, String titlu, String enunt) {
        titluProblema.setText(titlu);
        enuntProblema.setText(enunt);

    }

    @Override
    public void onBackPressed() {

    }
}