package com.example.user.cathaymum;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Map;
import java.util.Set;

public class PostFlightActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent intent=new Intent();
    private final String TAG=this.getClass().getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_flight2);
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
        Button airport= (Button) findViewById(R.id.PostFlightAirport);
        airport.setOnClickListener(this);
        Button indoor= (Button) findViewById(R.id.PostFlightIndoor);
        indoor.setOnClickListener(this);
        Button nearBy= (Button) findViewById(R.id.PostFligthNearbyTransit);
        nearBy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent= new Intent(this,ProcessPostFlightActivity.class);
        switch (v.getId()){
            case R.id.PostFlightAirport: intent.putExtra("choice",1);break;
            case R.id.PostFlightIndoor: intent.putExtra("choice",2); break;
            case R.id.PostFligthNearbyTransit: intent.putExtra("choice",3); break;
            default: intent.putExtra("choice",0); break;
        }
        Log.i(TAG,"after switch");
        TellMumAsync async=new TellMumAsync();
        async.execute("123");
    }

    public class TellMumAsync extends AsyncTask<String,Void,JSONObject> {
        public String TAG=this.getClass().getCanonicalName();

        @Override
        protected JSONObject doInBackground(String... params) {
            Uri.Builder builder= new Uri.Builder();
            String jsonString= null;
            builder.scheme("http")
                    .authority(Constant.IP)
                    .appendPath("app")
                    .appendPath("sms");

            String myUrl=builder.build().toString();
            Log.i(TAG, myUrl);
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try{
                URL url=new URL(myUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("mum","123");
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
            catch(Exception e){
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
            Toast toast= Toast.makeText(PostFlightActivity.this,"Notified your mum already",Toast.LENGTH_LONG);
            toast.show();
            startActivity(intent);
        }
    }
}
