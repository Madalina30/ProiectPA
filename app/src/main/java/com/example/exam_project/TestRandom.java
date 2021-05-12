package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TestRandom extends AppCompatActivity {
    private TextView levelxGrila, enuntGrila, answerA, answerB, answerC, points;
    private ImageView backToMainMenu;
    private Button nextGrila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_grila);
        // doar 10 intrebari o sa fie per test
        // cumva aici ar trebui sa se faca acel random, nu in MainMenu
        // si sa seteze grila in functie de random
        levelxGrila = findViewById(R.id.levelxGrila);
        enuntGrila = findViewById(R.id.enuntGrila);
        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        points = findViewById(R.id.points);
        backToMainMenu = findViewById(R.id.backToGrile);
        // sa ai o alerta daca vrei sa te intorci pentru ca pierzi progresul!!!!
        nextGrila = findViewById(R.id.nextGrila);
        points.setText("1000");
        setRandomGrilaThings(points, levelxGrila, enuntGrila, answerA, answerB, answerC);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestRandom.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        nextGrila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // doar reface raspunsurile si intrebarea, nu si layoutul
//                setContentView(R.layout.activity_grile_main);
                // todo: nu spui daca e corect sau nu decat la final
                //  in caz ca a ajuns la ultima intrebare, nextLevel se schimba in "Submit" si te duce la MainMenu, dar inainte
                //  iti apare un ecran cu cate puncte ai adunat si cat % ai fct corect si daca esti eligibil pentru a trece la urmatorul nivel
                //  daca esti: congrats+points+new level unlocked+next level button+main menu button
                //  -> asta o sa se puna in statistici : punctajul (%), data si categoria: tabel in bd
                //  cu data si cat % a fct + categoria, care aici e Grila
                //  POTI REFACE UN NIVEL SI PUNCTAJUL SE VA REFACE, CHIAR SI IN STATISTICI
                //  go to next level - change setGrilaThings
                // TODO: cand a ajuns la submit, apare un ecran
            }
        });
    }
    public void setRandomGrilaThings(TextView points, TextView levelxGrila, TextView enuntGrila, TextView answerA, TextView answerB, TextView answerC) {
        levelxGrila.setText("Question 1");
        levelxGrila.setTextSize(20);
        enuntGrila.setText("1. Acesta este primul enunt al primei grile dintr-un test random hahahahaha");
        answerA.setText("primul raspuns");
        answerB.setText("al doilea raspuns");
        answerC.setText("al treilea raspuns");
    }
}