package com.example.exam_project;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse {
    public void method() throws JSONException {
        String result = "{\"someKey\":\"someValue\"}";
        JSONObject jObject = new JSONObject(result);

    }

}
