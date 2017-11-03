package com.example.second_application;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Extra.PrimaryFragment;

import static com.example.second_application.R.id.colorStar;

public class BillsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_detail);

        setTitle("Bill Info");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.bills_detail);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.setDrawerIndicatorEnabled(false);

        mDrawerLayout.addDrawerListener((mToggle));
        mToggle.syncState();

        PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");

        TextView chamber;
        TextView party;
        TextView contact1;
        TextView start_term;
        TextView end_term;
        TextView term;
        TextView office;
        TextView state;
        TextView fax;
        TextView birthday;
        final ImageView star;
        final ImageView colorStar;

        chamber = (TextView) findViewById(R.id.chamber);
        party = (TextView) findViewById(R.id.party);
        contact1 = (TextView) findViewById(R.id.contact1);
        start_term = (TextView) findViewById(R.id.startTerm);
        end_term = (TextView) findViewById(R.id.endTerm);
        term = (TextView) findViewById(R.id.term);
        office = (TextView) findViewById(R.id.office);
        state = (TextView) findViewById(R.id.state);
        fax = (TextView) findViewById(R.id.fax);
        birthday = (TextView) findViewById(R.id.birthday);
        star = (ImageView) findViewById(R.id.smallpic);
        colorStar = (ImageView) findViewById(R.id.color_pic);


        chamber.setText(map.getBill_id().toUpperCase());
        party.setText(map.getShort_title());
        contact1.setText(map.getBill_type().toUpperCase());
        start_term.setText(map.getTitle() +"."+map.getLast_name()+", "+map.getFirst_name());
        end_term.setText(map.getChamber().substring(0,1).toUpperCase()+ map.getChamber().substring(1));
        term.setText(map.getStatus());
        office.setText(map.getParty());
        state.setText(map.getCongress_url());
        fax.setText(map.getVersion_name());
        birthday.setText(map.getBill_url());

        MainActivity mainActivity = new MainActivity();

        colorStar.setVisibility(View.GONE);
        star.setVisibility(View.VISIBLE);

        if(mainActivity.bill_list.size() == 0) {
            colorStar.setVisibility(View.GONE);
            star.setVisibility(View.VISIBLE);
        }else {
            for (int i = 0; i < mainActivity.bill_list.size(); i++) {
                if (mainActivity.bill_list.get(i).equalsIgnoreCase(map.getBill_id())) {
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                }
            }
        }

        star.setOnClickListener(new View.OnClickListener(){
            PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");
            public void onClick(View v) {

                MainActivity mainActivity = new MainActivity();

                int count = 0;
                if (mainActivity.bill_list.size() == 0) {
                    mainActivity.bill_list.add(map.getBill_id());
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                } else {
                    for (int k = 0; k < mainActivity.bill_list.size(); k++) {
                        if (mainActivity.bill_list.get(k).equalsIgnoreCase(map.getBill_id())) {
                            count++;
                        }
                    }
                    if (count == 1) {
                        mainActivity.bill_list.remove(map.getBill_id());
                        colorStar.setVisibility(View.GONE);
                        star.setVisibility(View.VISIBLE);
                    } else if(count == 0) {
                        mainActivity.bill_list.add(map.getBill_id());
                        colorStar.setVisibility(View.VISIBLE);
                        colorStar.setColorFilter(Color.YELLOW);
                    }
                }

                /*SharedPreferences mPrefs = getSharedPreferences("bills.app", 0);
                SharedPreferences.Editor editor = mPrefs.edit();

                Set<String> set= new HashSet<String>();
                set.add(map.getBill_id());
                editor.putStringSet("com.example.app", (Set<String>) set);
                editor.commit();*/
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();    //Call the back button's method
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
