package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Probleme extends AppCompatActivity {
    private ImageView backToMainMenu;
    private ScrollView problemeScroll;
    private RelativeLayout problemSample;

    public ScrollView getProblemeScroll() {
        return problemeScroll;
    }

    public void setProblemeScroll(ScrollView problemeScroll) {
        this.problemeScroll = problemeScroll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probleme);
        setFunctionality();

    }
    public void setFunctionality() {
        backToMainMenu = findViewById(R.id.backToMainMenu);
        problemeScroll = findViewById(R.id.grileScroll);
        problemSample = findViewById(R.id.problemSample);

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Probleme.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        problemSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: sa te duca la o grila si sa se transmita niste parametrii necesari pt acea grila
                // TODO: sa se verifice la ce nivel este
                setContentView(R.layout.template_problemaa);
                // template bun probleme
                TextView titluProblema = findViewById(R.id.titluProblema);
                TextView enuntProblema = findViewById(R.id.enuntProblema);
                EditText rezolvare = findViewById(R.id.rezolvare);
                TextView points = findViewById(R.id.points);
                ImageView backToProbleme = findViewById(R.id.backToProbleme);
                Button verificarePb = findViewById(R.id.verificarePb);
                // points se ia din baza de date + level
                // TODO: ceva fisier cu enunturi si answers
                points.setText("1000");
                setProblemeThings(points, titluProblema, enuntProblema, rezolvare);
                backToProbleme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_probleme);
                        setFunctionality();
                    }
                });
                verificarePb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: aici verifica problema si apare un ecran de loading pt verify
                        //  si dupa apare un ecran cu congrats sau cu lost
//                        setContentView(R.layout.verificareProblemLoading);
                        // cand a terminat de verificat: setContentView(R.layout.win_template) sau lost in functie de rezultat
                        // dar o sa apara raspunsul tau + raspunsul bun daca nu e corect!!!!!
                    }
                });
            }
        });
    }

    public void setProblemeThings(TextView points, TextView titluProblema, TextView enuntProblema, TextView rezolvare) {
        titluProblema.setText("Problema 1");
        enuntProblema.setText("Acesta este primul enunt al primei probleme din primul nivel hahahahaha");

    }
    // TODO: backButton -> atunci cand e in template_grila ca si contentView, atunci sa te duca la activity_grile_main
    // TODo: daca esti in activity_grile_main, nu-ti mai pasa ca o sa te duca la MainMenu
    @Override
    public void onBackPressed() {

    }
}