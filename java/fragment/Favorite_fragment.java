package fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Extra.PrimaryFragment;
import com.example.second_application.R;
import Extra.SecondaryFragment;
import Extra.ThirdFragment;
import subFragment.Favorites.Bill_fav;
import subFragment.Favorites.Comm_fav;
import subFragment.Favorites.Leg_fav;
import subFragment.Legislature.ByState;
import subFragment.Legislature.House;
import subFragment.Legislature.Senate;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite_fragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;



    public Favorite_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View x1 = inflater.inflate(R.layout.fragment_favorite_fragment, container, false);
        tabLayout = (TabLayout) x1.findViewById(R.id.tabs);

        Leg_fav legfav  = new Leg_fav();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.favorites, legfav);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tabLayout.addTab(tabLayout.newTab().setText("Legisltors"), true);
        tabLayout.addTab(tabLayout.newTab().setText("Bills"));
        tabLayout.addTab(tabLayout.newTab().setText("Committees"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    Leg_fav legfav  = new Leg_fav();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.favorites, legfav);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }else if(tabLayout.getSelectedTabPosition() == 1){
                    Bill_fav billfav = new Bill_fav();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.favorites, billfav);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }else if(tabLayout.getSelectedTabPosition() == 2){
                    Comm_fav commfav = new Comm_fav();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.favorites, commfav);
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
