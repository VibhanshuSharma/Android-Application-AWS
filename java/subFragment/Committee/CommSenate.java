package subFragment.Committee;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.second_application.CommitteeDetail;
import com.example.second_application.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Extra.PrimaryFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommSenate extends Fragment {

    public static ListView comm_list;
    List<PrimaryFragment> fetch;


    public CommSenate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View x1= inflater.inflate(R.layout.comm_house,container, false);
        comm_list = (ListView) x1.findViewById(R.id.comm_list);

        new JSONTask().execute("http://lowcost-env.nfzmy5j3t8.us-west-2.elasticbeanstalk.com/index1.php?variable=commit");

        comm_list.setClickable(true);
        comm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<PrimaryFragment> li;
                PrimaryFragment object = new PrimaryFragment();
                final JSONTask j = new JSONTask();
                Bundle bundle = new Bundle();

                if (fetch.size() != 0) {
                    object = fetch.get(position);
                    bundle.putSerializable("my data", (Serializable) object);
                    Intent detail = new Intent(getActivity(), CommitteeDetail.class);
                    detail.putExtras(bundle);
                    startActivity(detail);
                }
            }
        });

        return x1;
    }

    public class JSONTask extends AsyncTask<String, String, List<PrimaryFragment>> {

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
                final List<PrimaryFragment> sortedJsonArray = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    PrimaryFragment primary_fragment = new PrimaryFragment();

                    if (finalObject.getString("chamber").equalsIgnoreCase("senate")) {

                        primary_fragment.setComm_id(finalObject.getString("committee_id"));
                        primary_fragment.setParty(finalObject.getString("name"));
                        primary_fragment.setShort_title(finalObject.getString("chamber"));

                        if(finalObject.has("parent_committee_id")) {
                            primary_fragment.setParent_comm(finalObject.getString("parent_committee_id"));
                        }else{
                            primary_fragment.setParent_comm("null");
                        }
                        if(finalObject.has("phone")) {
                            primary_fragment.setContact(finalObject.getString("phone"));
                        }else{
                            primary_fragment.setContact("null");
                        }
                        if(finalObject.has("office")) {
                            primary_fragment.setOffice(finalObject.getString("office"));
                        }else{
                            primary_fragment.setOffice("N.A");
                        }
                        primary_fragment_list.add(primary_fragment);
                    }
                    }
                Collections.sort(primary_fragment_list, new Comparator<PrimaryFragment>() {

                    @Override
                    public int compare(PrimaryFragment t1, PrimaryFragment primary) {
                        String valA = new String();
                        String valB = new String();

                            valA = t1.getParty();
                            valB = primary.getParty();

                        return valA.compareTo(valB);
                    }

                });

                for (int i = 0; i < parentArray.length(); i++) {
                    try{
                        sortedJsonArray.add(primary_fragment_list.get(i));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                fetch = getObject(sortedJsonArray);

                return sortedJsonArray;

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

    public List<PrimaryFragment> getObject(List<PrimaryFragment> parentObject) {

        //JSONArray parentArray = parentObject.getJSONArray("results");
        if (parentObject.size() != 0) {

        }
        return parentObject;
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
            inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
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

            bill.setText(primaryFragmentList.get(position).getComm_id());
            intro.setText(primaryFragmentList.get(position).getParty().substring(0,1).toUpperCase()+ primaryFragmentList.get(position).getParty().substring(1));
            title.setText(primaryFragmentList.get(position).getShort_title().substring(0,1).toUpperCase() + primaryFragmentList.get(position).getShort_title().substring(1) );

            return convertView;
        }
    }



}
