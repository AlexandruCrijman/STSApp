package com.alexcrijman.stsapp10.uitls;


import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerRESTComm {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = ServerRESTComm.class.getSimpleName();
    private static OkHttpClient lHttpClient;

    static OkHttpClient getInstance() {
        if (lHttpClient == null) {
            lHttpClient = new OkHttpClient();
        }
        return lHttpClient;
    }

    // REST Method for Autentification
    public static String getAutentifURL(String url, String email) throws IOException {
        url = url + "?email=" + email;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = getInstance().newCall(request).execute();
            Log.d(TAG, response.body().toString());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "522";
        }

    }

    // REST Method for Loading the Plays of this day
    public static String getPlaysOfThisDay(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = getInstance().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "522";
        }

    }

    // REST Method to VoteForWin
    @Nullable
    public static String getVoteForWin(String url, String votIdPlay, String email, String androidID) throws IOException {

        String currentDate = DateFormat.getDateTimeInstance().format(new Date());

        url = url + "?votIdPlay=" + votIdPlay + "&email=" + email + "&androidID=" + androidID + "&currentDate=" + currentDate;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = getInstance().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    // REST Method for Registrations
    public static String postRegister(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response;
        try {
            response = getInstance().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "522";
        }
    }

}
