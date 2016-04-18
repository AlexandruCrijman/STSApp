package com.alexcrijman.stsapp10.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.adaptors.ViewPagerAdapter;
import com.alexcrijman.stsapp10.fragments.programDayFriday;
import com.alexcrijman.stsapp10.fragments.programDayMonday;
import com.alexcrijman.stsapp10.fragments.programDayThursday;
import com.alexcrijman.stsapp10.fragments.programDayTuesday;
import com.alexcrijman.stsapp10.fragments.programDayWednesday;

public class ProgramPieseActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_vote_for_win);
        setTitle("");
        textView = (TextView) findViewById(R.id.actionBar_day_title_tv);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().openOptionsMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_piese);
        textView.setText("Programul Festivalului");

       viewPager = (ViewPager) findViewById(R.id.prg_viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.prg_tabs);
        tabLayout.setupWithViewPager(viewPager);






    }


    public void setupDay(int day) {
        switch (day) {
            case 0:
                textView.setText("Luni");
                break;
            case 1:
                textView.setText("Marţi");
                break;
            case 2:
                textView.setText("Miercuri");
                break;
            case 3:
                textView.setText("Joi");
                break;
            case 4:
                textView.setText("Vineri");
                break;
        }


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new programDayMonday(), "L");
        adapter.addFragment(new programDayTuesday(), "M");
        adapter.addFragment(new programDayWednesday(), "Mi");
        adapter.addFragment(new programDayThursday(), "J");
        adapter.addFragment(new programDayFriday(), "V");
        viewPager.setAdapter(adapter);


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
