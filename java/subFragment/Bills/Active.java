package subFragment.Bills;


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

import com.example.second_application.BillsDetail;
import com.example.second_application.Details;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import Extra.PrimaryFragment;
import fragment.Bill_fragment;
import subFragment.Legislature.ByState;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Active extends Fragment {

    public static ListView bill_list;
    List<PrimaryFragment> fetch;
    public static List<PrimaryFragment> activeList = new ArrayList<>();

    public Active() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        new JSONTask().execute("http://lowcost-env.nfzmy5j3t8.us-west-2.elasticbeanstalk.com/index1.php?variable=bills");

        View x1 = inflater.inflate(R.layout.active, container, false);
        bill_list = (ListView) x1.findViewById(R.id.list);

        bill_list.setClickable(true);
        bill_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<PrimaryFragment> li;
                PrimaryFragment object = new PrimaryFragment();
                final JSONTask j = new JSONTask();
                Bundle bundle = new Bundle();

                if (fetch.size() != 0) {
                    object = fetch.get(position);
                    bundle.putSerializable("my data", (Serializable) object);
                    Intent detail = new Intent(getActivity(), BillsDetail.class);
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
                String a = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                List<PrimaryFragment> primary_fragment_list = new ArrayList<>();
                final List<PrimaryFragment> sortedJsonArray = new ArrayList<>();


                DateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    PrimaryFragment primary_fragment = new PrimaryFragment();

                    a = finalObject.getString("introduced_on");
                    Date date = inputFormat.parse(a);
                    String outputText = outputFormat.format(date);

                    primary_fragment.setBill_id(finalObject.getString("bill_id"));
                   if (outputText != null) {
                       primary_fragment.setParty(outputText);
                   }else {
                       primary_fragment.setParty("0");
                   }
                    //primary_fragment.setParty(a);
                    if (finalObject.getString("short_title") == "null") {
                        primary_fragment.setShort_title(finalObject.getString("official_title"));
                    } else {
                        primary_fragment.setShort_title(finalObject.getString("short_title"));
                    }
                    primary_fragment.setCongress_url(a);
                    primary_fragment.setBill_type(finalObject.getString("bill_type"));
                    primary_fragment.setTitle(finalObject.getJSONObject("sponsor").getString("title"));
                    primary_fragment.setFirst_name(finalObject.getJSONObject("sponsor").getString("first_name"));
                    primary_fragment.setLast_name(finalObject.getJSONObject("sponsor").getString("last_name"));
                    primary_fragment.setChamber(finalObject.getString("chamber"));
                    if(finalObject.getJSONObject("history").getString("active").equalsIgnoreCase("true")){
                        primary_fragment.setStatus("Active");
                    }else if(finalObject.getJSONObject("history").getString("active").equalsIgnoreCase("false")){
                        primary_fragment.setStatus("New");
                    }
                    primary_fragment.setCongress_url(finalObject.getJSONObject("urls").getString("congress"));
                    primary_fragment.setVersion_name(finalObject.getJSONObject("last_version").getString("version_name"));
                    primary_fragment.setBill_url(finalObject.getJSONObject("last_version").getJSONObject("urls").getString("pdf"));

                    primary_fragment_list.add(primary_fragment);
                }

                Collections.sort(primary_fragment_list, new Comparator<PrimaryFragment>() {

                    @Override
                    public int compare(PrimaryFragment t1, PrimaryFragment primary) {
                        /*String valA = new String();
                        String valB = new String();

                        valA =  primary.getParty();
                        valB =  t1.getParty();*/

                        //return valB.compareTo(valA);
                        return primary.getCongress_url().compareTo(t1.getCongress_url());
                    }

                });

                for (int i = 0; i < parentArray.length(); i++) {
                    sortedJsonArray.add(primary_fragment_list.get(i));
                }
                fetch = getObject(sortedJsonArray);

                activeList=sortedJsonArray;
                return sortedJsonArray;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {


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
            bill_list.setAdapter(adapter);
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
            intro = (TextView) convertView.findViewById(R.id.Intro);
            title = (TextView) convertView.findViewById(R.id.Title);

            //SimpleDateFormat fm = new SimpleDateFormat("MMM dd,yyyy  ");


            bill.setText(primaryFragmentList.get(position).getBill_id().toUpperCase());
            intro.setText(primaryFragmentList.get(position).getParty());
            title.setText(primaryFragmentList.get(position).getShort_title());

            return convertView;
        }
    }

}
