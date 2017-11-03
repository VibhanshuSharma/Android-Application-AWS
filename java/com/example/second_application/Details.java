package com.example.second_application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Extra.PrimaryFragment;
import subFragment.Favorites.Leg_fav;

public class Details extends AppCompatActivity {

    public PrimaryFragment map;
    public String id;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legislature_details);

        setTitle("Legislators Info");

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

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.leg_detail);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener((mToggle));
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.setDrawerIndicatorEnabled(false);

        map = (PrimaryFragment) getIntent().getSerializableExtra("my data");

        TextView check;
        TextView email;
        TextView chamber;
        TextView contact1;
        TextView start_term;
        TextView end_term;
        ProgressBar term;
        TextView office;
        TextView state;
        TextView fax;
        TextView birthday;
        TextView party;
        ImageView smallImage;
        ImageView pic;
        ImageView fb;
        ImageView twitter;
        ImageView website;
        TextView progress;
        final ImageView star;
        final ImageView colorStar;

        progress = (TextView) findViewById(R.id.progress);
        check = (TextView) findViewById(R.id.check);
        email = (TextView) findViewById(R.id.email);
        chamber = (TextView) findViewById(R.id.chamber);
        contact1 = (TextView) findViewById(R.id.contact1);
        start_term = (TextView) findViewById(R.id.startTerm);
        end_term = (TextView) findViewById(R.id.endTerm);
        term = (ProgressBar) findViewById(R.id.term);
        office = (TextView) findViewById(R.id.office);
        state = (TextView) findViewById(R.id.state);
        fax = (TextView) findViewById(R.id.fax);
        birthday = (TextView) findViewById(R.id.birthday);
        party = (TextView) findViewById(R.id.party);
        smallImage = (ImageView) findViewById(R.id.smallImage);
        pic = (ImageView) findViewById(R.id.pic);
        fb = (ImageView) findViewById(R.id.fb);
        twitter = (ImageView) findViewById(R.id.twitter);
        website = (ImageView) findViewById(R.id.website);
        star = (ImageView) findViewById(R.id.star);
        colorStar = (ImageView) findViewById(R.id.colorStar);

        MainActivity main = new MainActivity();

        colorStar.setVisibility(View.GONE);
        star.setVisibility(View.VISIBLE);



        if(main.id_list.size() == 0) {
            colorStar.setVisibility(View.GONE);
            star.setVisibility(View.VISIBLE);
        }else {
            for (int i = 0; i < main.id_list.size(); i++) {
                if (main.id_list.get(i).equalsIgnoreCase(map.getBioguide_id())) {
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                }
            }
        }

        final String name = map.getTitle() + "." + map.getLast_name() + ", " + map.getFirst_name();

        ImageLoader.getInstance().displayImage("http://theunitedstates.io/images/congress/original/" + map.getBioguide_id() + ".jpg", pic);

        if (map.getParty().equalsIgnoreCase("D")) {
            ImageLoader.getInstance().displayImage("http://cs-server.usc.edu:45678/hw/hw8/images/d.png", smallImage);
        } else if (map.getParty().equalsIgnoreCase("R")) {
            ImageLoader.getInstance().displayImage("http://cs-server.usc.edu:45678/hw/hw8/images/r.png", smallImage);
        }


        star.setOnClickListener(new View.OnClickListener() {
            //PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");
            public void onClick(View v) {
                MainActivity main = new MainActivity();

                int count = 0;
                if (main.id_list.size() == 0) {
                    main.id_list.add(map.getBioguide_id());
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                } else {
                    for (int k = 0; k < main.id_list.size(); k++) {
                        if (main.id_list.get(k).equalsIgnoreCase(map.getBioguide_id())) {
                            count++;
                        }
                    }
                    if (count == 1) {
                        main.id_list.remove(map.getBioguide_id());
                        colorStar.setVisibility(View.GONE);
                        star.setVisibility(View.VISIBLE);
                    } else if(count == 0) {
                        main.id_list.add(map.getBioguide_id());
                        colorStar.setVisibility(View.VISIBLE);
                        colorStar.setColorFilter(Color.YELLOW);
                    }
                }
            }
    });






                /*SharedPreferences mPrefs = getSharedPreferences("example", 0);
                SharedPreferences.Editor editor = mPrefs.edit();
*/

                //main.id_list.add(map.getBioguide_id());

                /*for(int i=0;i<main.legislature_list.size();i++){
                if(main.legislature_list.get(i).getBioguide_id().equals(map.getBioguide_id())){
                    colorStar.setVisibility(View.VISIBLE);
                    colorStar.setColorFilter(Color.YELLOW);
                    main.legislature_list.remove(i);
                }else{
                    Set<String> set= new HashSet<String>();
                    set.add(map.getBioguide_id());
                    editor.putStringSet("com.example.app", (Set<String>) set);
                    editor.commit();
                    colorStar.setVisibility(View.GONE);
                    star.setVisibility(View.VISIBLE);
                }
                }
*/


        fb.setOnClickListener(new View.OnClickListener() {
            PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");

            public void onClick(View v) {
                if (map.getFb_id().equalsIgnoreCase("null")) {
                    Toast.makeText(getApplicationContext(), "No Facebook Account", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://facebook.com/" + map.getFb_id()));
                    startActivity(intent);
                }
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");

            public void onClick(View v) {
                if (map.getTwitter_id().equalsIgnoreCase("null")) {
                    Toast.makeText(getApplicationContext(), "No Twitter Account", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://twitter.com/" + map.getTwitter_id()));
                    startActivity(intent);
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");

            public void onClick(View v) {
                if (map.getWebsite().equalsIgnoreCase("null")) {
                    Toast.makeText(getApplicationContext(), "No Website", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(map.getWebsite()));
                    startActivity(intent);
                }
            }
        });

        String a = "";
        String b = "";
        Date oldDate = null;
        Date oldEndDate = null;
        DateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        // formatter.setLenient(false);

        a = map.getStart_term();
        b = map.getEnd_term();
        Date date1 = null;
        Date date = null;
        NumberFormat f = new DecimalFormat("00");

        try {
            date = inputFormat.parse(a);
            date1 = inputFormat.parse(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText1 = formatter.format(date);
        String outputText2 = formatter.format(date1);

        try {
            oldDate = formatter.parse(outputText1);
            oldEndDate = formatter.parse(outputText2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double oldMillis = oldDate.getTime();
        double oldEndMillis = oldEndDate.getTime();


        Date curDate = new Date();
        double curMillis = curDate.getTime();

        double cal = (curMillis - oldMillis) / (oldEndMillis - oldMillis) * 100;

        Double d = new Double(cal);

        Integer s = d.intValue();

        term.setProgress(s);
        //term.setBackgroundColor(Color.rgb(255,250,74));
        //term.setBackgroundColor(Color.red(5));
        term.setScaleY(6);
        progress.setText(s + "%");

        check.setText(map.getTitle() + "." + map.getLast_name() + ", " + map.getFirst_name());
        email.setText(map.getEmail());
        chamber.setText(map.getChamber().substring(0,1).toUpperCase() + map.getChamber().substring(1));
        contact1.setText(map.getContact());
        start_term.setText(map.getStart_term());
        end_term.setText(map.getEnd_term());
        office.setText(map.getOffice());
        state.setText(map.getState());
        fax.setText(map.getFax());
        birthday.setText(map.getBirthday());
        if (map.getParty().equalsIgnoreCase("R")) {
            map.setParty("Republic");
            party.setText(map.getParty());
        } else {
            map.setParty("Democratic");
            party.setText(map.getParty());
        }
        party.setText(map.getParty());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public PrimaryFragment getMyBitmap() {
        PrimaryFragment map = (PrimaryFragment) getIntent().getSerializableExtra("my data");
        return map;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();    //Call the back button's method
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Details Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
