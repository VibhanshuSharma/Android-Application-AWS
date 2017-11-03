package fragment;


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
import android.widget.ListView;
import android.widget.Toast;

import com.example.second_application.R;

import subFragment.Bills.Active;
import subFragment.Bills.NewBill;


/**
 * A simple {@link Fragment} subclass.
 */
public class Bill_fragment extends Fragment  {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static ListView bill_list;
    public static int int_items = 2;
    ListView billList;
    public static Context context = null;

    String introDate = "";

    public Bill_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Active active = new Active();
        NewBill newBill = new NewBill();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.bills, active);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        View x1 = inflater.inflate(R.layout.fragment_bill_fragment, container, false);

        tabLayout = (TabLayout) x1.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Active Bills"), true);
        tabLayout.addTab(tabLayout.newTab().setText("New Bills"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    Active active = new Active();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.bills, active);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                }else if(tabLayout.getSelectedTabPosition() == 1){
                    NewBill newBill = new NewBill();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.bills, newBill);
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




