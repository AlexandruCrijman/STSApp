package com.alexcrijman.stsapp10.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.uitls.EditTextModif;

import com.alexcrijman.stsapp10.uitls.ServerEndPointsAPI;
import com.alexcrijman.stsapp10.uitls.ServerRESTComm;
import com.alexcrijman.stsapp10.uitls.SettingsManager;
import com.alexcrijman.stsapp10.uitls.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VoteLoginInActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = VoteLoginInActivity.class.getSimpleName();


    private Button btnAutentif = null;
    private EditTextModif emailAutentifet = null;
    private Bundle extras;
    private String emailUser = "";
    Context mcntx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_vote_for_win);
        setTitle("");
        TextView textView = (TextView) findViewById(R.id.actionBar_day_title_tv);
        textView.setText(R.string.login_title);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().openOptionsMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_vote_login_in);


        extras = getIntent().getExtras();
        if (extras != null) {
            emailUser = extras.getString("emailUser");
        }


        emailAutentifet = (EditTextModif) findViewById(R.id.autentif_email_et);


        if (!emailUser.isEmpty())

        {
            emailAutentifet.setText(emailUser);
        }
        btnAutentif = (Button) findViewById(R.id.autentif_btn);

        btnAutentif.setOnClickListener(this);
        if (SettingsManager.getSomeStringValue(this) != null && emailUser.isEmpty()) {
            emailAutentifet.setText(SettingsManager.getSomeStringValue(this));
        }

        mcntx = this;

    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, String.valueOf(v.getId()));
        switch (v.getId()) {
            case R.id.autentif_btn:
                if (validate()) {
                    getAutentificationResponseFromServer();

                } else {

                    new AlertDialog.Builder(this)
                            .setTitle(getString(R.string.login_text_mail))
                            .setMessage(getString(R.string.login_alerg_dialog_message))
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    Toast.makeText(VoteLoginInActivity.this, "Încercaţi dinou",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(VoteLoginInActivity.this, VoteLoginInActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    Toast.makeText(VoteLoginInActivity.this, "Înregistrare",
                                            Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(VoteLoginInActivity.this, VoteSignInActivity.class);
                                    startActivity(intent);

                                }
                            }).create().show();

                }


                break;

        }
    }

    public boolean validate() {
        boolean bool = true;


        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String email = emailAutentifet.getText().toString();
        if (email.matches(emailPattern) != true) {
            if (emailAutentifet.getText().toString().isEmpty()) {
                emailAutentifet.setError("Email is required");
                bool = false;
            } else {

                emailAutentifet.getText().clear();
                emailAutentifet.setError("Email invalid");
                bool = false;
            }

        }
        return bool;

    }


    public void getAutentificationResponseFromServer() {

        String urlAutentif = ServerEndPointsAPI.getEndPointURL("autentification");

        String email = emailAutentifet.getText().toString();
        if (Utils.isInternetON(this)) {
            new AutenficiationTask().execute(urlAutentif, email);

        } else {
            Toast.makeText(getBaseContext(), "Nu aveţi conexiune la internet!", Toast.LENGTH_SHORT).show();

        }

    }

    private class AutenficiationTask extends AsyncTask<String, Void, JSONObject> {

        private JSONObject responseJson = null;
        ProgressDialog progressDialog = new ProgressDialog(VoteLoginInActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Verificăm mailul dumneavoastră...");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = params[0];
            String email = params[1];
            String response;


            try {
                response = ServerRESTComm.getAutentifURL(url, email);
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jObj) {

            progressDialog.dismiss();
            Intent intent;
            switch (jObj.optInt("code")) {
                case 200:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_LONG).show();
                    intent = new Intent(VoteLoginInActivity.this, VotForWinActivity.class);
                    SettingsManager.setSomeStringValue(mcntx, emailAutentifet.getText().toString());
                    intent.putExtra("emailUser", emailAutentifet.getText().toString());
                    startActivity(intent);

                    break;
                case 401:

                    new AlertDialog.Builder(mcntx)
                            .setTitle("Mail incorect")
                            .setMessage("Doriţi să vă înregistraţi?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent2 = new Intent(VoteLoginInActivity.this, VoteSignInActivity.class);
                                            startActivity(intent2);

                                        }

                                    }
                            ).create().show();


                    break;
                case 302:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_LONG).show();
                    intent = new Intent(VoteLoginInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Exit me", true);
                    startActivity(intent);
                    finish();

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


}


