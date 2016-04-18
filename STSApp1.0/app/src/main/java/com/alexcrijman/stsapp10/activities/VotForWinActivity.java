package com.alexcrijman.stsapp10.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.adaptors.VotePlayAdapter;
import com.alexcrijman.stsapp10.models.TheatrePlays;
import com.alexcrijman.stsapp10.uitls.ServerEndPointsAPI;
import com.alexcrijman.stsapp10.uitls.ServerRESTComm;
import com.alexcrijman.stsapp10.uitls.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class VotForWinActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = VotForWinActivity.class.getSimpleName();
    private ListView lPlayToVoteLV = null;
    private VotePlayAdapter lToVoteAdapter = null;
    private ArrayList<TheatrePlays> lListPlayToVote = new ArrayList<>();
    Bundle extras;
    String emailUser;
    private TextView ldayTitleTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_vote_for_win);
        setTitle("");
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().openOptionsMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }


        extras = getIntent().getExtras();
        if (extras != null) {
            emailUser = extras.getString("emailUser");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vot_for_win);

        lPlayToVoteLV = (ListView) findViewById(R.id.plays_to_vot_LV);
        lToVoteAdapter = new VotePlayAdapter(this, lListPlayToVote);
        lPlayToVoteLV.setAdapter(lToVoteAdapter);
        lPlayToVoteLV.setOnItemClickListener(this);


        if (Utils.isInternetON(this)) {
            getLoadPlayResponseFromServer();

        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getLoadPlayResponseFromServer() {

        String url = ServerEndPointsAPI.getEndPointURL("loadplays");
        new LoadPlaysTask().execute(url);
    }

    private class LoadPlaysTask extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog progressDialog = new ProgressDialog(VotForWinActivity.this);
        private JSONObject responseJson = null;

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Încărcăm trupele concurente de astăzi...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = params[0];

            String response;

            try {
                response = ServerRESTComm.getPlaysOfThisDay(url);
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


            switch (jObj.optInt("code")) {
                case 200:
                    setItems(jObj);
                    break;
                case 500:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
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

    void setItems(JSONObject jobj) {
        setDay(jobj.optString("day"));
        JSONArray aux = jobj.optJSONArray("plays");
        Log.d(TAG, aux.toString());
        for (int i = 0; i < aux.length(); i++) {
            try {
                JSONObject jsonPlay = aux.getJSONObject(i);
                TheatrePlays playAux = new TheatrePlays();
                playAux.setIdPlay(jsonPlay.optInt("idPlay"));
                playAux.setDayPlay(jsonPlay.optInt("dayPlay"));
                playAux.setBandName(jsonPlay.optString("bandName"));
                playAux.setPlayName(jsonPlay.optString("playName"));
                lListPlayToVote.add(playAux);
                lToVoteAdapter.updateData(lListPlayToVote);
                lToVoteAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void setDay(String dayPlay) {
        ldayTitleTV = (TextView) findViewById(R.id.actionBar_day_title_tv);
        switch (dayPlay) {
            case "1":
                ldayTitleTV.setText("Luni");
                break;
            case "2":
                ldayTitleTV.setText("Marți");
                break;
            case "3":
                ldayTitleTV.setText("Miercuri");
                break;
            case "4":
                ldayTitleTV.setText("Joi");
                break;
            case "5":
                ldayTitleTV.setText("Vineri");
                break;
            case "6":
                ldayTitleTV.setText("Sâmbătă");
                break;
            case "7":
                ldayTitleTV.setText("Duminică");
                break;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final TheatrePlays playItem = lListPlayToVote.get(position);

        Log.d(TAG, String.valueOf(playItem.getIdPlay()));
        new AlertDialog.Builder(this)
                .setTitle("Trupa preferată")
                .setMessage(Html.fromHtml("Eşti sigur că " +
                        "<b>" + playItem.getPlayName() + "</b>" +
                        " jucată de " +
                        "<b>" + playItem.getBandName() + "</b>" +
                        " este piesa ta preferată din această seară?"))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Utils.isInternetON(getApplicationContext())) {
                            getVoteForWinResponseFromServer(playItem.getIdPlay());

                        } else {
                            Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).create().show();


    }

    private void getVoteForWinResponseFromServer(int idPlay) {

        String url = ServerEndPointsAPI.getEndPointURL("voteforwin");
        String idPlayString = Integer.toString(idPlay);


        new VoteForWinTask().execute(url, idPlayString, emailUser);
    }

    private class VoteForWinTask extends AsyncTask<String, Void, JSONObject> {

        private JSONObject responseJson = null;
        ProgressDialog progressDialog = new ProgressDialog(VotForWinActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Procesăm opţiunea dumneavoastra...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = params[0];
            String idPlay = params[1];
            String emailUser = params[2];
            String androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            String response;

            try {
                response = ServerRESTComm.getVoteForWin(url, idPlay, emailUser, androidID);
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
                    Toast.makeText(getBaseContext(), "Mulţumim mult pentru votul acordat!", Toast.LENGTH_SHORT).show();
                    intent = new Intent(VotForWinActivity.this, MainActivity.class);
                    startActivity(intent);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Exit me", true);
                    startActivity(intent);

                    VotForWinActivity.this.finish();
                    break;
                case 301:
                    Toast.makeText(getBaseContext(), "User-ul nu exista", Toast.LENGTH_SHORT).show();
                    intent = new Intent(VotForWinActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Exit me", true);
                    startActivity(intent);

                    VotForWinActivity.this.finish();
                    break;
                case 304:
                    Toast.makeText(getBaseContext(), "Ai votat deja astăzi!", Toast.LENGTH_SHORT).show();
                    intent = new Intent(VotForWinActivity.this, MainActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Exit me", true);
                    startActivity(intent);
                    VotForWinActivity.this.finish();
                    break;
                default:
                    Toast.makeText(getBaseContext(), jObj.optString("status"), Toast.LENGTH_SHORT).show();
            }

        }
    }


}
