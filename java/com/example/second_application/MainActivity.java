package com.example.second_application;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Extra.PrimaryFragment;
import fragment.Bill_fragment;
import fragment.Committee_fragment;
import fragment.Favorite_fragment;
import fragment.Info_fragment;
import fragment.Legislator_fragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TextView textView;
    private ListView first_list;
    public List<PrimaryFragment> legislature_list = new ArrayList<>();
    public List<PrimaryFragment> Committee_list = new ArrayList<>();
    public List<PrimaryFragment> Bill_list = new ArrayList<>();
    public String bio_id;
    public static List<String> id_list = new ArrayList<>();
    public static List<String> bill_list = new ArrayList<>();
    public static List<String> comm_list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Legislators");

        Legislator_fragment legislator_fragment = new Legislator_fragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.linear_layout,
                legislator_fragment,
                legislator_fragment.getTag()

        ).commit();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout.addDrawerListener((mToggle));
        mToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();

        switch (id){
            case R.id.nav_legislators:
                setTitle("Legislators");
                Legislator_fragment legislator_fragmen = new Legislator_fragment();
                manager.beginTransaction().replace(
                        R.id.linear_layout,
                        legislator_fragmen
                        //legislator_fragmen.getTag()
                ).commit();
                break;

            case R.id.nav_bills:
                setTitle("Bills");
                Bill_fragment bill_fragment = new Bill_fragment();
                manager.beginTransaction().replace(
                        R.id.linear_layout,
                        bill_fragment,
                        bill_fragment.getTag()
                ).commit();
                break;

            case R.id.nav_committees:
                setTitle("Committees");
                Committee_fragment committee_fragment = new Committee_fragment();
                manager.beginTransaction().replace(
                        R.id.linear_layout,
                        committee_fragment
                        //committee_fragment.getTag()
                ).commit();
                break;

            case R.id.nav_favorites:
                setTitle("Favorites");
                Favorite_fragment favorite_fragment = new Favorite_fragment();
                manager.beginTransaction().replace(
                        R.id.linear_layout,
                        favorite_fragment
                        //favorite_fragment.getTag()
                ).commit();
                break;

            case R.id.nav_info:
                setTitle("About Me");
                Info_fragment info_fragment = new Info_fragment();
                manager.beginTransaction().replace(
                        R.id.linear_layout,
                        info_fragment
                        //info_fragment.getTag()
                ).commit();
                break;

            default:
                setTitle("Legislators");
                Legislator_fragment legislator_fragment = new Legislator_fragment();
                manager.beginTransaction().replace(
                        R.id.linear_layout,
                        legislator_fragment
                        //legislator_fragmen.getTag()
                ).commit();
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}

