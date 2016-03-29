package com.davidjaglowski.cafeservice;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by DAVID JAGLOWSKI on 10/29/2015.
 */
public class ViewOrderActivity extends AppCompatActivity {

    private JSONArray handles = null;
    private int selected_handle = -1;
    private String order_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            order_number = savedInstanceState.getString(MenuActivity.ORDER_NUMBER);
        } else {
            Intent intent = getIntent();
            order_number = intent.getStringExtra(MenuActivity.ORDER_NUMBER);
        }
        setContentView(R.layout.activity_view);
        new listViewTask(order_number).execute();
    }

    public void submitOrder(View view){
        EditText userText = (EditText) findViewById(R.id.text_name);
        String name = userText.getText().toString();
        EditText passwordText = (EditText) findViewById(R.id.text_phone);
        String phone = passwordText.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new SubmitOrderTask(name,phone).execute();
        }
    }

    public void deleteOrder(View view){
        if (selected_handle != -1) {
            try {
                new DeleteTask(handles.getJSONObject(selected_handle).getString("id")).execute();
            } catch(JSONException ex) {
                Log.d("CafeService","Exception in deleteOrder: "+ex.getMessage());
            }
        }
    }

    private class DeleteTask extends AsyncTask<String, Void, Void> {
        private String uri;

        DeleteTask(String iditem) {
            uri = "http://" + URIHandler.hostName + "/Cafe/api/item/" + iditem;
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                URIHandler.doDelete(uri);
            } catch (IOException e) {
            }
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            selected_handle = -1;
            new listViewTask(order_number).execute();
        }
    }

    private class SubmitOrderTask extends AsyncTask<String, Void, String> {
        private String uri;
        String json;

        SubmitOrderTask(String name, String phone) {
            uri = "http://" + URIHandler.hostName + "/Cafe/api/invoice/" + order_number;
            json = "{\"idinvoice\":" + order_number + ",\"customer\":" + name + ",\"phone\":" + phone + "}";
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return URIHandler.doPut(uri, json);
            } catch (IOException e) {
                return "";
            }
        }

        //onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String invoiceId) {
            finish();
        }
    }

    private class listViewTask extends AsyncTask<String, Void, String> {
        private String uri;

        listViewTask(String order_id) {
            uri = "http://" + URIHandler.hostName + "/Cafe/api/customerorders?ordernumber=" + order_id;
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

    private void loadHandles(String json) {
        handles = null;
        String[] handleStrs = null;

        ListView handlesList = (ListView) findViewById(R.id.que);

        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                JSONObject handle = handles.getJSONObject(n);
                handleStrs[n] = handle.getString("name");
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

