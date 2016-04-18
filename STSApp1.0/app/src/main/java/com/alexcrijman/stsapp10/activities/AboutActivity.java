package com.alexcrijman.stsapp10.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alexcrijman.stsapp10.R;

public class AboutActivity extends AppCompatActivity {

    TextView aboutTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_vote_for_win);
        setTitle("");
        TextView textView = (TextView) findViewById(R.id.actionBar_day_title_tv);
        textView.setText("Despre STS");

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().openOptionsMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);


        aboutTV = (TextView) findViewById(R.id.AboutTV);
        aboutTV.setText(Html.fromHtml(getString(R.string.about_text)));

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
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
        finish();
    }
}
