package com.example.exam_project;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

public class REST {

    public void getter(){
        AndroidNetworking.get("https://exam-net.herokuapp.com/exams/examcontroller.php?view=all").build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
                System.out.println("ceva");
            }

            @Override
            public void onError(ANError anError) {
                System.out.println(anError);
            }
        });
    }
}
