package subFragment.Legislature;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.second_application.Details;
import com.example.second_application.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Extra.PrimaryFragment;
import fragment.Legislator_fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Senate extends Fragment {

    public static ListView first_list;
    List<PrimaryFragment> fetch;
    Map<String, Integer> mapIndex;
    JSONArray array;


    public Senate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        new JSONTask().execute("http://lowcost-env.nfzmy5j3t8.us-west-2.elasticbeanstalk.com/index1.php?variable=legis");

        View x1 = inflater.inflate(R.layout.senate, container, false);
        first_list = (ListView) x1.findViewById(R.id.first_list);

        first_list.setClickable(true);
        first_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<PrimaryFragment> li;
                PrimaryFragment object = new PrimaryFragment();
                final JSONTask j = new JSONTask();
                Bundle bundle = new Bundle();

                if (fetch.size() != 0) {
                    object = fetch.get(position);
                    bundle.putSerializable("my data", (Serializable) object);
                    Intent detail = new Intent(getActivity(), Details.class);
                    detail.putExtras(bundle);
                    startActivity(detail);
                }
            }
        });


        return x1;
    }

    public class JSONTask extends AsyncTask<String, String, List<PrimaryFragment>> implements View.OnClickListener {

        @Override
        protected List<PrimaryFragment> doInBackground(String... params) {

            JSONObject parentObject = null;
            BufferedReader reader = null;
            HttpURLConnection connection;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                String a = "";
                String b="";
                String c="";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                parentObject = new JSONObject(finalJson);
                //return parentObject;

                JSONArray parentArray = parentObject.getJSONArray("results");
                DateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");


                final List<PrimaryFragment> primary_fragment_list = new ArrayList<>();
                final List<PrimaryFragment> sortedJsonArray = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    PrimaryFragment primary_fragment = new PrimaryFragment();

                    if (finalObject.getString("chamber").equalsIgnoreCase("senate")) {
                        primary_fragment.setBioguide_id(finalObject.getString("bioguide_id"));
                        primary_fragment.setLast_name(finalObject.getString("last_name"));
                        primary_fragment.setFirst_name(finalObject.getString("first_name"));
                        primary_fragment.setParty(finalObject.getString("party"));
                        primary_fragment.setState_name(finalObject.getString("state_name"));

                        a = finalObject.getString("term_start");
                        Date date = inputFormat.parse(a);
                        String outputText1 = outputFormat.format(date);

                        b = finalObject.getString("term_end");
                        Date date1 = inputFormat.parse(b);
                        String outputText2 = outputFormat.format(date1);


                        c = finalObject.getString("term_start");
                        Date date2 = inputFormat.parse(c);
                        String outputText3 = outputFormat.format(date2);

                        primary_fragment.setBioguide_id(finalObject.getString("bioguide_id"));
                        primary_fragment.setLast_name(finalObject.getString("last_name"));
                        primary_fragment.setFirst_name(finalObject.getString("first_name"));
                        primary_fragment.setParty(finalObject.getString("party"));

                        /*if (finalObject.getString("party").equalsIgnoreCase("R")){
                            primary_fragment.setParty("Republic");
                        }else {
                            primary_fragment.setParty("Democratic");
                        }*/

                        primary_fragment.setState_name(finalObject.getString("state_name"));
                        primary_fragment.setTitle(finalObject.getString("title"));
                        primary_fragment.setChamber(finalObject.getString("chamber"));
                        primary_fragment.setBirthday(outputText3);
                        primary_fragment.setContact(finalObject.getString("phone"));
                        //primary_fragment.setTerm(finalObject.getString("term"));
                        primary_fragment.setStart_term(outputText1);
                        primary_fragment.setEnd_term(outputText2);
                        primary_fragment.setState(finalObject.getString("state"));
                        primary_fragment.setFax(finalObject.getString("fax"));
                        primary_fragment.setOffice(finalObject.getString("office"));
                        primary_fragment.setEmail(finalObject.getString("oc_email"));
                        primary_fragment.setWebsite(finalObject.getString("website"));

                        if (finalObject.has("facebook_id")){
                            primary_fragment.setFb_id(finalObject.getString("facebook_id"));
                        }else{
                            primary_fragment.setFb_id("null");
                        }
                        if(finalObject.has("twitter_id")) {
                            primary_fragment.setTwitter_id(finalObject.getString("twitter_id"));
                        }else{
                            primary_fragment.setTwitter_id("null");
                        }

                        if (finalObject.getString("district").equalsIgnoreCase("null")){
                            primary_fragment.setDistrict("0");
                        }else {
                            primary_fragment.setDistrict(finalObject.getString("district"));
                        }
                        primary_fragment_list.add(primary_fragment);
                    }
                }
                Collections.sort(primary_fragment_list, new Comparator<PrimaryFragment>() {

                    @Override
                    public int compare(PrimaryFragment t1, PrimaryFragment primary) {
                        String valA = new String();
                        String valB = new String();

                        valA =  t1.getLast_name();
                        valB =  primary.getLast_name();

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
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<PrimaryFragment> result) {
            super.onPostExecute(result);

            final List<PrimaryFragment> stateName_list = new ArrayList<>();
            final List<PrimaryFragment> sortedJsonArray = new ArrayList<>();
            PrimaryFragment p = new PrimaryFragment();
            mapIndex = new LinkedHashMap<String, Integer>();
            LinearLayout indexLayout = (LinearLayout) getActivity().findViewById(R.id.side_index);

            TextView textView = null;
            String index = null;

            for (int i = 0; i < result.size(); i++) {

                String fruits = result.get(i).getLast_name();
                index = fruits.substring(0, 1);
                if (mapIndex.get(index) == null)
                    mapIndex.put(index, i);
            }

            indexLayout = (LinearLayout) getActivity().findViewById(R.id.side_index);
            List<String> indexList = new ArrayList<String>(mapIndex.keySet());

            for(int ix = 0; ix<indexList.size();ix++) {
                index = indexList.get(ix);
                textView = (TextView) getLayoutInflater(null).inflate(R.layout.side_list, null);
                textView.setText(index);
                textView.setOnClickListener(this);
                indexLayout.addView(textView);
             }

            /*Collections.sort(result, new Comparator<PrimaryFragment>() {

                @Override
                public int compare(PrimaryFragment t1, PrimaryFragment primary) {
                    String valA = new String();
                    String valB = new String();

                    valA = t1.getLast_name();
                    valB = primary.getLast_name();

                    return valA.compareTo(valB);
                }

            });*/


            first_adapter adapter = new first_adapter(getActivity(), R.layout.fragment_primary, result);
            first_list.setAdapter(adapter);
        }

        @Override
        public void onClick(View view) {
            TextView selectedIndex = (TextView) view;
            first_list.setSelection(mapIndex.get(selectedIndex.getText()));
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
            inflater = (LayoutInflater) Legislator_fragment.context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }

            ImageView image;
            TextView name;
            TextView party;
            TextView state;
            TextView district;

            image = (ImageView) convertView.findViewById(R.id.image);
            name = (TextView) convertView.findViewById(R.id.Name);
            party = (TextView) convertView.findViewById(R.id.party);
            state = (TextView) convertView.findViewById(R.id.State);
            district = (TextView) convertView.findViewById(R.id.District);

            ImageLoader.getInstance().displayImage("http://theunitedstates.io/images/congress/original/" + primaryFragmentList.get(position).getBioguide_id() + ".jpg", image);
            name.setText(primaryFragmentList.get(position).getLast_name() + ", " + primaryFragmentList.get(position).getFirst_name());
            party.setText("(" + primaryFragmentList.get(position).getParty() + ")");
            state.setText(primaryFragmentList.get(position).getState_name());
            district.setText("-District " + primaryFragmentList.get(position).getDistrict());

            return convertView;
        }
    }

}
