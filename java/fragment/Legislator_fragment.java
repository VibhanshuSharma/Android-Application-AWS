package fragment;
//package info.androidhive.materialtabs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import Extra.PrimaryFragment;

import com.example.second_application.R;

import subFragment.Legislature.ByState;
import subFragment.Legislature.House;
import subFragment.Legislature.Senate;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Legislator_fragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static ListView first_list;
    public static int int_items = 3;
    private List<PrimaryFragment> primaryFragmentList;
    public static Context context = null;
    private String title;
    private int page;
    private static ImageView img;


    public static Legislator_fragment newInstance(int page, String title) {

        Legislator_fragment frag = new Legislator_fragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        context = getActivity();
        ByState state = new ByState();
        House house = new House();
        Senate senate = new Senate();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.legis, state);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        View x1 = inflater.inflate(R.layout.fragment_legislator_fragment, container, false);
        first_list = (ListView) x1.findViewById(R.id.first_list);

        View x = inflater.inflate(R.layout.fragment_legislator_fragment, null);
        tabLayout = (TabLayout) x1.findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("By State"), true);
        tabLayout.addTab(tabLayout.newTab().setText("House"));
        tabLayout.addTab(tabLayout.newTab().setText("Senate"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    ByState state = new ByState();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.legis, state);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }else if(tabLayout.getSelectedTabPosition() == 1){
                    House house = new House();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.legis, house);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }else if(tabLayout.getSelectedTabPosition() == 2){
                    Senate senate = new Senate();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.legis, senate);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return x1;
    }
}











