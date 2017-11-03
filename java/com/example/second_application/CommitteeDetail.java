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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashSet;
import java.util.Set;

import Extra.PrimaryFragment;

public class CommitteeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_detail);

        setTitle("Committee Info");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.comms_details);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener((mToggle));
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.setDrawerIndicatorEnabled(false);

        PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");

        MainActivity mainActivity = new MainActivity();


        TextView comm_id;
        TextView comm_name;
        TextView chamber;
        TextView parent_comm;
        TextView contact;
        TextView office;
        ImageView small_pic;
        final ImageView star;
        final ImageView colorStar;

        comm_id = (TextView) findViewById(R.id.comm_id);
        comm_name = (TextView) findViewById(R.id.comm_name);
        chamber = (TextView) findViewById(R.id.chamber);
        parent_comm = (TextView) findViewById(R.id.parent_comm);
        contact = (TextView) findViewById(R.id.contact);
        office = (TextView) findViewById(R.id.office);
        small_pic = (ImageView) findViewById(R.id.small_pic);
        star = (ImageView) findViewById(R.id.star);
        colorStar = (ImageView) findViewById(R.id.goldstar);

        colorStar.setVisibility(View.GONE);
        star.setVisibility(View.VISIBLE);


        if(mainActivity.comm_list.size() == 0) {
            colorStar.setVisibility(View.GONE);
            star.setVisibility(View.VISIBLE);
        }else {
            for (int i = 0; i < mainActivity.comm_list.size(); i++) {
                if (mainActivity.comm_list.get(i).equalsIgnoreCase(map.getComm_id())) {
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
                if (mainActivity.comm_list.size() == 0) {
                    mainActivity.comm_list.add(map.getComm_id());
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                } else {
                    for (int k = 0; k < mainActivity.comm_list.size(); k++) {
                        if (mainActivity.comm_list.get(k).equalsIgnoreCase(map.getComm_id())) {
                            count++;
                        }
                    }
                    if (count == 1) {
                        mainActivity.comm_list.remove(map.getComm_id());
                        colorStar.setVisibility(View.GONE);
                        star.setVisibility(View.VISIBLE);
                    } else if(count == 0) {
                        mainActivity.comm_list.add(map.getComm_id());
                        colorStar.setVisibility(View.VISIBLE);
                        colorStar.setColorFilter(Color.YELLOW);
                    }
                }

                /*SharedPreferences mPrefs = getSharedPreferences("comm.app", 0);
                SharedPreferences.Editor editor = mPrefs.edit();

                Set<String> set= new HashSet<String>();
                set.add(map.getComm_id());
                editor.putStringSet("comm.app", (Set<String>) set);
                editor.commit();*/

                /*if (star.getVisibility() == View.VISIBLE) {
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                    star.setVisibility(View.GONE);
                } else if (colorStar.getVisibility() == View.GONE) {
                    // colorStar.setColorFilter();
                    colorStar.setVisibility(View.VISIBLE);
                    star.setVisibility(View.GONE);
                }
*/
                /*ArrayList<PrimaryFragment> list = new ArrayList<PrimaryFragment>();
*/
            }
        });

        comm_id.setText(map.getComm_id());
        comm_name.setText(map.getParty());
        chamber.setText(map.getShort_title().substring(0,1).toUpperCase()+map.getShort_title().substring(1));
        parent_comm.setText(map.getParent_comm());
        contact.setText(map.getContact());
        office.setText(map.getOffice());

        if(map.getShort_title().equalsIgnoreCase("house")){
            ImageLoader.getInstance().displayImage("http://cs-server.usc.edu:45678/hw/hw8/images/h.png", small_pic);
        }else if(map.getShort_title().equalsIgnoreCase("senate")){
            ImageLoader.getInstance().displayImage("http://cs-server.usc.edu:45678/hw/hw8/images/s.svg", small_pic);
        }else if (map.getShort_title().equalsIgnoreCase("joint")) {
            ImageLoader.getInstance().displayImage("http://cs-server.usc.edu:45678/hw/hw8/images/s.svg", small_pic);
        }


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
