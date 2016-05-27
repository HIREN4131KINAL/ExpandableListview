package com.tranetech.expandablelist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String type, numbers, OfferNumber, id_offer;
    private String jsonstr;
    private static String url = "";
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView exp_leaseoffer;
    private static List<String> listDataHeader;
    private static HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadUIElements();
        LoadLIsners();
    }

    private void LoadUIElements() {
        exp_leaseoffer = (ExpandableListView) findViewById(R.id.lvExp);
        if (listDataHeader == null) {
            new DownloadJason().execute();
        } else {
            Fill_List_Adapter();
        }
    }

    private void LoadLIsners() {
    }


    private class DownloadJason extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            try {
                JSONParser call = new JSONParser();
                jsonstr = call.makeBufferCall(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("Json url view", jsonstr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (jsonstr != null) {

                try {
                    //create parent object for jsonstr(url)
                    JSONObject jobj = new JSONObject(jsonstr);
                    //create parent array for Data
                    JSONArray jarray = jobj.getJSONArray("Data");
                    //first loop is for parent array to fetch header data
                    for (int hk = 0; hk < jarray.length(); hk++) {
                        JSONObject d = jarray.getJSONObject(hk);

                        // Adding Header data
                        listDataHeader.add(d.getString("dates"));

                        //create second array obj for fetch sub items data
                        JSONArray subitem = d.getJSONArray("sub_items");

                        // create List for Adding child data for lease offer
                        List<String> lease_offer = new ArrayList<String>();

                        // second loop is for to add sub items child data
                        for (int i = 0; i < subitem.length(); i++) {
                            //create json obj for to fetch sub items data or record
                            JSONObject d2 = subitem.getJSONObject(i);
                            type = d2.getString("type").toString();
                            id_offer = d2.getString("id");
                            String type_final = type.substring(0, 1).toUpperCase() + type.substring(1);
                            numbers = d2.getString("dates").toString();
                            //	lease_offer.add("" + type_final + " " + numbers);

                            lease_offer.add("" + numbers + "Hajg9_9Ajy" + id_offer);


                            listDataChild.put(listDataHeader.get(hk), lease_offer);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Fill_List_Adapter();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Connection problem..", Toast.LENGTH_SHORT)
                        .show();

            }


        }


    }

    private void Fill_List_Adapter() {

        expandableListAdapter = new com.tranetech.expandablelist.Expandable_adapter(
                getApplicationContext(), listDataHeader, listDataChild);
        Log.i("groups", listDataHeader.toString());
        Log.i("details", listDataChild.toString());
        exp_leaseoffer.setAdapter(expandableListAdapter);

        exp_leaseoffer.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }

        });
        // Listview Group expanded listener
        exp_leaseoffer
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        // Toast.makeText(getActivity().getApplicationContext(),
                        // listDataHeader.get(groupPosition) + " Expanded",
                        // Toast.LENGTH_SHORT).show();
                    }
                });
        exp_leaseoffer
                .setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        	Toast.makeText(
                                    getApplicationContext(),
									listDataHeader.get(groupPosition)
											+ " Collapsed", Toast.LENGTH_SHORT)
									.show();
                    }
                });
        exp_leaseoffer.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(), listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).substring(0, 10), Toast.LENGTH_SHORT).show();

                try {
                    type = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString();
                    //type = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).substring(0, 15).toString();
                    numbers = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString().trim();
                    //id_offer = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String[] values = numbers.split("Hajg9_9Ajy");
                    OfferNumber = String.valueOf(values[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }
}
