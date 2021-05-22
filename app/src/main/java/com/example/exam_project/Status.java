package com.example.exam_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Status extends AppCompatActivity {
    private ImageView backToMainMenu;
    private LinearLayout layoutStatus;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backToMainMenu = findViewById(R.id.backToMainMenu);
        layoutStatus = findViewById(R.id.layoutStatus);

        AndroidNetworking.get("https://exam-net.herokuapp.com/statuses/statcontroller.php?view=all").build().getAsJSONObject(new JSONObjectRequestListener() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    int stop = 0;
                    JSONArray users_informations = response.getJSONArray("exams");
                    for (int i = 0; i < users_informations.length(); i++) {
                        JSONObject username = users_informations.getJSONObject(i);
                        if (username.get("username").equals(pref.getString("username",""))) {
                            addToStatus(String.valueOf(username.get("category")), (String) username.get("date"), (String) username.get("procent"));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError);
            }
        });

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Status.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        // TODO: when a game is done, the status scroll will receive a status_template object inside, with variables changed
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addToStatus(String categoryName, String dateName, String percentage) {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParamsRel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParamsRel.setMargins(30, 30, 30, 30);

        relativeLayout.setLayoutParams(layoutParamsRel);
        relativeLayout.setBackgroundResource(R.drawable.btn_bkg);

        TextView category = new TextView(this);
        RelativeLayout.LayoutParams textv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        category.setTextSize(25);
        textv.leftMargin = 50;
        textv.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        textv.addRule(RelativeLayout.CENTER_VERTICAL);
        category.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        category.setText(categoryName); // sau teste random sau probleme
        category.setLayoutParams(textv);

        TextView date = new TextView(this);
        RelativeLayout.LayoutParams dateParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        date.setTextSize(25);
        dateParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        date.setText(dateName); // sau teste random sau probleme
        date.setLayoutParams(dateParam);

        TextView points = new TextView(this);
        RelativeLayout.LayoutParams pointsParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        points.setTextSize(25);
        pointsParam.rightMargin = 50;
        pointsParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        pointsParam.addRule(RelativeLayout.CENTER_VERTICAL);
        points.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        points.setText(percentage + "%"); // sau teste random sau probleme
        points.setLayoutParams(pointsParam);

        relativeLayout.addView(category);
        relativeLayout.addView(date);
        relativeLayout.addView(points);

        // de la relative layout trebuie sa te duca la grila specifica pentru acel nivel (prima din set - momentan chiar prima, dupa random)

        layoutStatus.addView(relativeLayout);
    }

    @Override
    public void onBackPressed() {

    }
}