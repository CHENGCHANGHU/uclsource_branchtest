package com.example.uclsourceproject;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JsonUtil {

    private static final String TAG = "tigercheng";

    public void string2json() {

    }

//    public static String getJSON(String... args) {
//        StringBuilder resultJson = new StringBuilder("{");
//
//        for (int i = 0; i < args.length; i += 2) {
//            resultJson.append("\"")
//                    .append(args[i])
//                    .append("\"")
//                    .append(":")
//                    .append("\"")
//                    .append(args[i + 1])
//                    .append("\"")
//                    .append(",");
//        }
//        resultJson.setLength(resultJson.length() - 1);
//        resultJson.append("}");
//        return resultJson.toString();
//    }

    public static String getJSON(Object... args) {
        StringBuilder resultJson = new StringBuilder("{");

        for (int i = 0; i < args.length; i += 2) {
            resultJson.append("\"")
                    .append(args[i])
                    .append("\"")
                    .append(":")
                    .append("\"")
                    .append(args[i + 1])
                    .append("\"")
                    .append(",");
        }
        resultJson.setLength(resultJson.length() - 1);
        resultJson.append("}");
        return resultJson.toString();
    }

    public static ArrayList<JSONObject> getJSONArray(String str) {

        ArrayList<JSONObject> jsonAL = new ArrayList<>();

        String[] strings = str.split("\\}\\{");
        Log.d(TAG, "getJSONArray: " + strings.length);
        int l = strings.length;
        try {
            for (int i = 0; i < l; i++) {
                Log.d(TAG, "getJSONArray: " + strings[i]);
                if (i == 0) {
                    strings[0] = strings[0].concat("}");
//                    Log.d(TAG, "strings[0]: " + strings[0]);
                    jsonAL.add(new JSONObject(strings[0]));

                } else if (i == l - 1) {
                    strings[l - 1] = "{".concat(strings[l - 1]);
                    jsonAL.add(new JSONObject(strings[l - 1]));
                } else {
                    strings[i] = "{".concat(strings[i]).concat("}");
                    jsonAL.add(new JSONObject(strings[i]));
                }
            }
            Log.d(TAG, "strings: " + strings);
        } catch (JSONException e) {
            Log.d(TAG, "error: " + e);
            e.printStackTrace();
        }

        return jsonAL;
    }

    private static JSONObject[] add(JSONObject[] arr, JSONObject jsonObject) {
        int size = arr.length;
        JSONObject[] tmp = new JSONObject[size + 1];
        System.arraycopy(arr, 0, tmp, 0, size);
        tmp[size] = jsonObject;
        return tmp;
    }

}
