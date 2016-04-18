package com.alexcrijman.stsapp10.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.uitls.ServerEndPointsAPI;
import com.alexcrijman.stsapp10.uitls.ServerRESTComm;
import com.alexcrijman.stsapp10.uitls.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class VoteSignInActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = VoteSignInActivity.class.getSimpleName();

    private EditText lastNameEt;
    private EditText firstNameEt;
    private EditText emailEt;
    private EditText phoneEt;
    private Button signIn;
    private String android_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_sign_in);
        setTitle("");
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().openOptionsMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }


        lastNameEt = (EditText) findViewById(R.id.sign_in_lastName_et);
        firstNameEt = (EditText) findViewById(R.id.sign_in_firstName_et);
        emailEt = (EditText) findViewById(R.id.sign_in_email_et);
        phoneEt = (EditText) findViewById(R.id.sign_in_phone_et);


        signIn = (Button) findViewById(R.id.sign_in_btn);


        signIn.setOnClickListener(this);
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e(TAG, "ID-ul telefonului este: " + android_id);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.sign_in_btn:
                if (validate()) {
                    if (Utils.isInternetON(this)) {
                        getSigInResponseFromServer();

                    } else {
                        Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, VoteLoginInActivity.class);
                        startActivity(intent);
                    }
                }


                break;
        }
    }


    public boolean validate() {
        boolean bool;


        bool = !(lastNameEt.getText().toString().isEmpty()
                || firstNameEt.getText().toString().isEmpty()
                || emailEt.getText().toString().isEmpty());
        if (lastNameEt.getText().toString().isEmpty())
            lastNameEt.setError("Numele este câmp obligatoriu");
        if (firstNameEt.getText().toString().isEmpty())
            firstNameEt.setError("Numele este câmp obligatoriu");
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String email = emailEt.getText().toString();
        if (email.matches(emailPattern) != true) {
            if (emailEt.getText().toString().isEmpty())
                emailEt.setError("Emailul este câmp obligatoriu");
            else {

                emailEt.getText().clear();
                emailEt.setError("Email invalid");
                bool = false;
            }
        }
        return bool;

    }

    private void getSigInResponseFromServer() {
        String urlSignIn = ServerEndPointsAPI.getEndPointURL("register");
        String d = DateFormat.getDateTimeInstance().format(new Date());
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("email", emailEt.getText().toString());
            userJson.put("firstName", firstNameEt.getText().toString());
            userJson.put("lastName", lastNameEt.getText().toString());
            userJson.put("phone", phoneEt.getText().toString());
            userJson.put("androidId", android_id);
            userJson.put("timeRegistered", DateFormat.getDateTimeInstance().format(new Date()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SignInTask().execute(urlSignIn, userJson.toString());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

        }


        return true;
    }

    private class SignInTask extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog progressDialog = new ProgressDialog(VoteSignInActivity.this);
        private JSONObject responseJson = null;

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Verificăm mailul dumneavoastră...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = params[0];
            String userJson = params[1];
            String response;

            try {
                response = ServerRESTComm.postRegister(url, userJson);
                if (response.equals("522")) {
                    responseJson = new JSONObject();
                    responseJson.put("code", 522);
                    responseJson.put("status", "Server doesn't work");
                    return responseJson;
                }
                Log.d(TAG, response.toString());
                responseJson = new JSONObject(response);
                Log.d(TAG, responseJson.toString());

                return responseJson;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject jObj) {
            progressDialog.dismiss();
            Intent intent;

            switch (jObj.optInt("code")) {
                case 200:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
                    intent = new Intent(VoteSignInActivity.this, VoteLoginInActivity.class);
                    intent.putExtra("emailUser", emailEt.getText().toString());
                    startActivity(intent);
                    break;
                case 301:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
                    intent = new Intent(VoteSignInActivity.this, VoteLoginInActivity.class);
                    intent.putExtra("emailUser", emailEt.getText().toString());
                    startActivity(intent);
                    break;
                case 522:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
                    break;
                case 400:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
            }


        }


    }

}
