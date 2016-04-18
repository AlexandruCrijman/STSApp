package com.alexcrijman.stsapp10.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.alexcrijman.stsapp10.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button openSiSC;
    private Button openSTS;
    private Button openAbout;
    private Button openProgram;
    private Button openPublicVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        setTitle("");


        openSiSC = (Button) findViewById(R.id.btn_to_SiSC_site);
        openSTS = (Button) findViewById(R.id.btn_to_STS_site);
        openAbout = (Button) findViewById(R.id.btn_despre);
        openProgram = (Button) findViewById(R.id.btn_program);
        openPublicVote = (Button) findViewById(R.id.public_vote_btn);


        openSiSC.setOnClickListener(this);
        openSTS.setOnClickListener(this);
        openAbout.setOnClickListener(this);
        openProgram.setOnClickListener(this);
        openPublicVote.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)

                .setMessage("Doriţi să părăsiţi aplicaţia?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this, "Aţi ieşit din STSApp 2016",
                                Toast.LENGTH_LONG).show();

                        finish();

                    }
                }).create().show();
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


    @Override
    public void onClick(View v) {
        Intent intent = null;
        Uri uri = null;
        switch (v.getId()) {
            case R.id.btn_to_SiSC_site:
                uri = Uri.parse("http://www.sisc.ro");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.btn_to_STS_site:
                uri = Uri.parse("http://www.sts.sisc.ro");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.btn_despre:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;


            case R.id.btn_program:
                intent = new Intent(this, ProgramPieseActivity.class);
                startActivity(intent);

                break;
            case R.id.public_vote_btn:
                intent = new Intent(this, VoteLoginInActivity.class);
                startActivity(intent);
                break;


        }
    }

}
