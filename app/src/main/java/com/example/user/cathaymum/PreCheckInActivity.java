package com.example.user.cathaymum;

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
import java.util.ArrayList;
import java.util.List;

public class PreCheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_check_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button= (Button) findViewById(R.id.preCheckInButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreCheckInAsync async = new PreCheckInAsync();
                async.execute("abc", "123", "1234asb");
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
    public class PreCheckInAsync extends AsyncTask<String,Void,JSONObject>{
        public String TAG=this.getClass().getCanonicalName();

        @Override
        protected JSONObject doInBackground(String... params) {
            Uri.Builder builder= new Uri.Builder();
            String jsonString= null;
            builder.scheme("http")
                    .authority(Constant.IP)
                    .appendPath("app")
                    .appendPath("pcheck")
                    .appendQueryParameter("name",params[0])
                    .appendQueryParameter("passportNo",params[1])
                    .appendQueryParameter("ticketNo",params[2]);

            String myUrl=builder.build().toString();
            Log.i(TAG, myUrl);
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try{
                URL url=new URL(myUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

//                List<String> params = new ArrayList<String>();
//                params.add(new BasicNameValuePair("firstParam", paramValue1));
//                params.add(new BasicNameValuePair("secondParam", paramValue2));
//                params.add(new BasicNameValuePair("thirdParam", paramValue3));
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params));
//                writer.flush();
//                writer.close();
//                os.close();

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
            Toast toast= Toast.makeText(PreCheckInActivity.this,jsonObject.toString(),Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
