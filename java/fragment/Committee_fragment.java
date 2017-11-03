package fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.second_application.R;

import subFragment.Committee.CommHouse;
import subFragment.Committee.CommJoint;
import subFragment.Committee.CommSenate;


/**
 * A simple {@link Fragment} subclass.
 */
public class Committee_fragment extends Fragment {

    public static TabLayout tabLayout;

    public Committee_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final CommHouse commHouse = new CommHouse();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.committee, commHouse);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        View x1= inflater.inflate(R.layout.fragment_committee_fragment,container, false);

        tabLayout = (TabLayout) x1.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("House"), true);
        tabLayout.addTab(tabLayout.newTab().setText("Senate"));
        tabLayout.addTab(tabLayout.newTab().setText("Joint"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    CommHouse commHouse = new CommHouse();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.committee, commHouse);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    CommSenate commSenate = new CommSenate();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.committee, commSenate);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    CommJoint commJoint = new CommJoint();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.committee, commJoint);
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




    /*public class JSONTask extends AsyncTask<String, String, List<PrimaryFragment>> {

        @Override
        protected List<PrimaryFragment> doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                List<PrimaryFragment> primary_fragment_list = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    PrimaryFragment primary_fragment = new PrimaryFragment();
                    //primary_fragment.setBioguide_id(finalObject.getString("bioguide_id"));
                    //primary_fragment.setState_name(finalObject.getString("state_name"));
                    //primary_fragment.setDistrict(finalObject.getString("district"));

                    primary_fragment.setLast_name(finalObject.getString("committee_id"));
                    primary_fragment.setParty(finalObject.getString("name"));
                    primary_fragment.setShort_title(finalObject.getString("chamber"));


                    primary_fragment_list.add(primary_fragment);
                }
                return primary_fragment_list;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<PrimaryFragment> result) {
            super.onPostExecute(result);

            first_adapter adapter = new first_adapter(getActivity(), R.layout.fragment_secondary, result);
            comm_list.setAdapter(adapter);
        }
    }

    public class first_adapter extends ArrayAdapter {

        private final Context context;
        private List<PrimaryFragment> primaryFragmentList;
        private int resource;
        private LayoutInflater inflater;

        public first_adapter(Context context, int resource, List<PrimaryFragment> objects) {
            super(context, resource, objects);
            primaryFragmentList = objects;
            this.resource = resource;
            this.context = context;
            inflater = (LayoutInflater) Committee_fragment.context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }


            TextView bill;
            TextView intro;
            TextView title;

            bill = (TextView) convertView.findViewById(R.id.Bill);
            intro = (TextView) convertView.findViewById(R.id.Title);
            title = (TextView) convertView.findViewById(R.id.Intro);

            bill.setText(primaryFragmentList.get(position).getLast_name());
            intro.setText(primaryFragmentList.get(position).getParty().substring(0,1).toUpperCase()+ primaryFragmentList.get(position).getParty().substring(1));
            title.setText(primaryFragmentList.get(position).getShort_title().substring(0,1).toUpperCase() + primaryFragmentList.get(position).getShort_title().substring(1) );

            return convertView;
        }
    }
*/
}
