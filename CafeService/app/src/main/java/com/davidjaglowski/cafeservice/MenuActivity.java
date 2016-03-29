package com.davidjaglowski.cafeservice;

import android.content.Intent;
import android.util.Log;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {

    public final static String ORDER_NUMBER = "com.davidjaglowski.cafeservice.ORDER_NUMBER";

    private JSONArray handles = null;
    private int selected_handle = -1;
    private String order_number = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selected_handle = -1;
        order_number = "0";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        new HandlesTask().execute();
    }

    public void addOrder(View view){
        if (selected_handle != -1 && order_number.equals("0")) {
            new NewCustomerTask().execute();
        }
        else if(selected_handle != 1){
            try {
                new SelectedOrderTask(handles.getJSONObject(selected_handle).getString("idproduct"), String.valueOf(order_number)).execute();
            } catch (JSONException ex) {
                Log.d("CafeService", "Exception in addOrder: " + ex.getMessage());
            }
        }
    }

    public void viewOrder(View view){
        Intent intent = new Intent(this, ViewOrderActivity.class);
        intent.putExtra(ORDER_NUMBER, order_number);
        startActivity(intent);
    }

    private class HandlesTask extends AsyncTask<String, Void, String> {
        private String uri;

        HandlesTask() {
            uri = "http://" + URIHandler.hostName + "/Cafe/api/product/";
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                return URIHandler.doGet(uri, "");
            } catch (IOException e) {
                return "";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            loadHandles(result);
        }
    }

    private class SelectedOrderTask extends AsyncTask<String, Void, String> {
        private String uri;
        private String json;

        SelectedOrderTask(String product_id, String order_id) {
            uri = "http://" + URIHandler.hostName + "/Cafe/api/item/";
            json = "{\"product\":" + product_id +
                    ",\"ordernumber\":" + order_id +
                    "}";
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return URIHandler.doPost(uri, json);
            } catch (IOException e) {
                return "";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String messageId) {
            // After posting the message, we will want to post the individual recipients.
            //processRecipients(messageId);
        }
    }

    private class NewCustomerTask extends AsyncTask<String, Void, String> {
        private String uri;
        String json;

        NewCustomerTask() {
            uri = "http://" + URIHandler.hostName + "/Cafe/api/invoice/";
            json = "{\"customer\":\"unknown\",\"phone\":\"unknown\"}";
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.d("CafeService", "Here is the uri: " + uri);
                return URIHandler.doPost(uri, json);
            } catch (IOException e) {
                return "";
            }
        }

        //onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String invoiceId) {
            Log.d("CafeService", "It should be: " + order_number);
            if(!order_number.equals("0")){
                try {
                    new SelectedOrderTask(handles.getJSONObject(selected_handle).getString("idproduct"), invoiceId).execute();
                    selected_handle = -1;
                } catch (JSONException ex) {
                    Log.d("CafeService", "Exception in addOrder: " + ex.getMessage());
                }
            }
        }
    }

    private void loadHandles(String json) {
        handles = null;
        String[] handleStrs = null;

        ListView handlesList = (ListView) findViewById(R.id.menu_list);

        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                JSONObject handle = handles.getJSONObject(n);
                handleStrs[n] = handle.getString("name") + ": $" + handle.getString("cost");
            }
        } catch (JSONException ex) {
            Log.d("CafeService", "Exception in loadHandles: " + ex.getMessage());
            handleStrs = new String[0];
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, handleStrs);
        handlesList.setAdapter(adapter);

        handlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // remember the selection
                selected_handle = i;
            }
        });
    }
}
