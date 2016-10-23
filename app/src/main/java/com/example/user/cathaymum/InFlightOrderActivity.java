package com.example.user.cathaymum;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InFlightOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_flight_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        InflightAdapter adapter = new InflightAdapter(this, R.layout.in_flight_row, getResources().getStringArray(R.array.orderArray));
        ListView listView = (ListView) findViewById(R.id.orderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int itemPosition = position;
                InFlightAsync async = new InFlightAsync();
                async.execute(Integer.toString(itemPosition), String.valueOf((int)Math.random() * 123 + 1));
            }
        });
    }



    public class InFlightAsync extends AsyncTask<String,Void,JSONObject> {
        public String TAG=this.getClass().getCanonicalName();

        @Override
        protected JSONObject doInBackground(String... params) {
            Uri.Builder builder= new Uri.Builder();
            String jsonString= null;
            builder.scheme("http")
                    .authority(Constant.IP)
                    .appendPath("app")
                    .appendPath("order");

            String myUrl=builder.build().toString();
            Log.i(TAG, myUrl);
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try{
                URL url=new URL(myUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("order_type", params[0]);
                parameters.put("seat_no",params[1]);
                Set set = parameters.entrySet();
                Iterator i = set.iterator();
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, String> param : parameters.entrySet()) {
                    if (postData.length() != 0) {
                        postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(postDataBytes);

                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonString = buffer.toString();
                Log.i(TAG,jsonString);
            }
            catch(IOException e){
                Log.e(TAG, "Error ", e);
                return null;
            }
            finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
                try{
                    JSONObject obj=new JSONObject(jsonString);
                    return obj;
                }
                catch(JSONException e){
                    Log.e(TAG,e.toString());
                }

            }

            return null;
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Toast toast= Toast.makeText(InFlightOrderActivity.this,"Order has been processed",Toast.LENGTH_LONG);
            toast.show();
        }


    }



    public class InflightAdapter extends ArrayAdapter<String> {
        Context context;
        int layoutResourceId;
        String data[] = null;

        public InflightAdapter(Context context, int layoutResourceId, String[] data){
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = convertView;
            rowHolder holder = null;
            if(row == null){
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new rowHolder();
                holder.item = (TextView)row.findViewById(R.id.orderItem);
                holder.itemButton = (Button)row.findViewById(R.id.inFlightOrderButton);
                row.setTag(holder);
            }
            else{
                holder = (rowHolder)row.getTag();
            }
            String itemName = data[position];
            holder.item.setText(itemName);
            holder.itemButton.setText("Submit");
            return row;
        }

        public class rowHolder{
            TextView item;
            Button itemButton;
        }
    }

}
