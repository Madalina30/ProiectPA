package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GrileMain extends AppCompatActivity {
    private ImageView backToMainMenu;
    private ScrollView grileScroll;
    private RelativeLayout levelSample;

    public ScrollView getStatusScroll() {
        return grileScroll;
    }

    public void setStatusScroll(ScrollView grileScroll) {
        this.grileScroll = grileScroll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grile_main);
        setFunctionality();
        // TODO: we have to add by hand here the levels and the ones that can't be reached
        // TODO: and change them when it's time and they reached those needya points
        // TODO: adapter for scroll view to do this

    }

    public void setFunctionality() {
        backToMainMenu = findViewById(R.id.backToMainMenu);
        grileScroll = findViewById(R.id.grileScroll);
        levelSample = findViewById(R.id.levelSample);

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrileMain.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        levelSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: sa te duca la o grila si sa se transmita niste parametrii necesari pt acea grila
                // TODO: sa se verifice la ce nivel este
                setContentView(R.layout.template_grila);
                TextView levelxGrila = findViewById(R.id.levelxGrila);
                TextView enuntGrila = findViewById(R.id.enuntGrila);
                TextView answerA = findViewById(R.id.answerA);
                TextView answerB = findViewById(R.id.answerB);
                TextView answerC = findViewById(R.id.answerC);
                TextView points = findViewById(R.id.points);
                ImageView backToGrile = findViewById(R.id.backToGrile);
                Button nextGrila = findViewById(R.id.nextGrila);
                // points se ia din baza de date + level
                // TODO: ceva fisier cu enunturi si answers
                points.setText("1000");
                setGrilaThings(points, levelxGrila, enuntGrila, answerA, answerB, answerC);
                backToGrile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_grile_main);
                        setFunctionality();
                    }
                });
                nextGrila.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // o sa te duca la urmatoarea grila, nu la urm activity
                        setContentView(R.layout.activity_grile_main);
                        // todo: nu spui daca e corect sau nu decat la final
                        //  in caz ca a ajuns la ultima intrebare, nextLevel se schimba in "Submit" si te duce la MainMenu, dar inainte
                        //  iti apare un ecran cu cate puncte ai adunat si cat % ai fct corect si daca esti eligibil pentru a trece la urmatorul nivel
                        //  daca esti: congrats+points+new level unlocked+next level button+main menu button
                        //  -> asta o sa se puna in statistici : punctajul (%), data si categoria: tabel in bd
                        //  cu data si cat % a fct + categoria, care aici e Grila
                        //  POTI REFACE UN NIVEL SI PUNCTAJUL SE VA REFACE, CHIAR SI IN STATISTICI
                        //  go to next level - change setGrilaThings
                        // TODO: cand a ajuns la submit, apare un ecran
                        setFunctionality();
                    }
                });
            }
        });
    }

    public void setGrilaThings(TextView points, TextView levelxGrila, TextView enuntGrila, TextView answerA, TextView answerB, TextView answerC) {
        levelxGrila.setText("Level 1");
        enuntGrila.setText("1. Acesta este primul enunt al primei grile din primul nivel hahahahaha");
        answerA.setText("primul raspuns");
        answerB.setText("al doilea raspuns");
        answerC.setText("al treilea raspuns");
    }
    // TODO: backButton -> atunci cand e in template_grila ca si contentView, atunci sa te duca la activity_grile_main
    // TODo: daca esti in activity_grile_main, nu-ti mai pasa ca o sa te duca la MainMenu
}