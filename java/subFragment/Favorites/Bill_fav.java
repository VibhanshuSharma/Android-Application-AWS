package subFragment.Favorites;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.second_application.MainActivity;
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
import java.util.Map;
import java.util.Set;

import Extra.PrimaryFragment;
import subFragment.Bills.Active;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bill_fav extends Fragment {

    List<PrimaryFragment> fetch;
    public static ListView bill_fav_list;
    Map<String, Integer> mapIndex;
    List<PrimaryFragment> active_list = new ArrayList<>();
    Active active = new Active();

    public Bill_fav() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //new JSONTask().execute("http://104.198.0.197:8080/bills?&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=100");
        //List<PrimaryFragment> active_list = new ArrayList<>();
        active_list= active.activeList;
        new JSONTask().execute("http://104.198.0.197:8080/bills?&history.active=false&last_version.urls.pdf__exists=true&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=50");

        View x1 = inflater.inflate(R.layout.bill_fav, container, false);
        bill_fav_list = (ListView) x1.findViewById(R.id.bill_list);

        bill_fav_list.setClickable(true);
        bill_fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

   /* public List<PrimaryFragment> loadFromStorage() {
        SharedPreferences mPrefs = getContext().getSharedPreferences("bills.app", 0);

        ArrayList<PrimaryFragment> items = new ArrayList<PrimaryFragment>();
        PrimaryFragment myclass = new PrimaryFragment();
        Set<String> set = mPrefs.getStringSet("com.example.app", null);

        if (set != null) {
            for (String s : set) {
                myclass.setBill_id(s);
                items.add(myclass);
            }
        }
        return items;
    }
*/
    public class JSONTask extends AsyncTask<String, String, List<PrimaryFragment>> {

       MainActivity mainActivity = new MainActivity();
       //Active active = new Active();

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
                int count = 0;
                List<PrimaryFragment> newBill_list = new ArrayList<>();
                final List<PrimaryFragment> sortedJsonArray = new ArrayList<>();
                //List<PrimaryFragment> active_list = new ArrayList<>();


                //List<PrimaryFragment> obj = loadFromStorage();

                //PrimaryFragment primary_fragment = new PrimaryFragment();

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
                    } else {
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
                    primary_fragment.setFirst_name(finalObject.getJSONObject("sponsor").getString("last_name"));
                    primary_fragment.setChamber(finalObject.getString("chamber"));
                    if (finalObject.getJSONObject("history").getString("active").equalsIgnoreCase("true")) {
                        primary_fragment.setStatus("Active");
                    } else if (finalObject.getJSONObject("history").getString("active").equalsIgnoreCase("false")) {
                        primary_fragment.setStatus("New");
                    }
                    primary_fragment.setCongress_url(finalObject.getJSONObject("urls").getString("congress"));
                    primary_fragment.setVersion_name(finalObject.getJSONObject("last_version").getString("version_name"));
                    primary_fragment.setBill_url(finalObject.getJSONObject("last_version").getJSONObject("urls").getString("pdf"));

                    newBill_list.add(primary_fragment);
                    //active_list= active.primary_fragment_list;
                }
                    try {
                        for (int k = 0; k < mainActivity.bill_list.size(); k++) {
                            for (int j = 0; j < active_list.size(); j++) {
                                if (mainActivity.bill_list.get(k).equals(active_list.get(j).getBill_id())) {
                                    mainActivity.Bill_list.add(active_list.get(j));
                                    break;
                                }
                            }
                            for (int i = 0; i < newBill_list.size(); i++) {
                                if (mainActivity.bill_list.get(k).equals(newBill_list.get(i).getBill_id())) {
                                    mainActivity.Bill_list.add(newBill_list.get(i));
                                    break;
                                }
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.print(e);
                    }


                /*Collections.sort(primary_fragment_list, new Comparator<PrimaryFragment>() {

                    @Override
                    public int compare(PrimaryFragment t1, PrimaryFragment primary) {
                        String valA = new String();
                        String valB = new String();

                        valA =  primary.getParty();
                        valB =  t1.getParty();

                        //return valB.compareTo(valA);
                        return t1.getCongress_url().compareTo(primary.getCongress_url());
                    }

                });

                for (int i = 0; i < parentArray.length(); i++) {
                    sortedJsonArray.add(primary_fragment_list.get(i));
                }*/
                fetch = getObject(mainActivity.Bill_list);

                return mainActivity.Bill_list;


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
            bill_fav_list.setAdapter(adapter);
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
        //private List<PrimaryFragment> primaryFragmentList;
        private int resource;
        private LayoutInflater inflater;
        MainActivity main = new MainActivity();


        public first_adapter(Context context, int resource, List<PrimaryFragment> objects) {
            super(context, resource, objects);
            main.Bill_list = objects;
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


            bill.setText(main.Bill_list.get(position).getBill_id().toUpperCase());
            intro.setText(main.Bill_list.get(position).getParty());
            title.setText(main.Bill_list.get(position).getShort_title());

            return convertView;
        }
    }


}
