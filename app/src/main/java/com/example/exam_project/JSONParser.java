package com.example.exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONParser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String loadJSON(AppCompatActivity appCompatActivity) {
        String json = null;
        try {
            InputStream is = appCompatActivity.getAssets().open("gameData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public JSONArray parseJSON(AppCompatActivity appCompatActivity){
        try {
            JSONObject obj = new JSONObject(loadJSON(appCompatActivity));
            JSONArray tests_array = obj.getJSONArray("tests");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;
            System.out.println(tests_array.get(0));
            System.out.println(tests_array.get(1));

            JSONArray grile = tests_array.getJSONObject(0).getJSONArray("grile");
            JSONObject probleme = tests_array.getJSONObject(1);
            // for pt obiectele din interiorul nivelelor pana la level1.length()
//            JSONObject level1 = grile.getJSONArray("level_1").getJSONObject(0);
//            JSONArray level2 = grile.getJSONArray("level_2");
//            System.out.println(level1);
//            System.out.println(level2);


//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(i);
//                Log.d("Details-->", jo_inside.getString("formule"));
//                String formula_value = jo_inside.getString("formule");
//                String url_value = jo_inside.getString("url");
//
//                //Add your values in your `ArrayList` as below:
//                m_li = new HashMap<String, String>();
//                m_li.put("formule", formula_value);
//                m_li.put("url", url_value);
//
//                formList.add(m_li);
//            }
            return grile;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}