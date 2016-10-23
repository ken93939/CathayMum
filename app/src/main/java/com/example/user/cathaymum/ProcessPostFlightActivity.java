package com.example.user.cathaymum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ProcessPostFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_flight);
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



        int choice=1;
        Uri gmmIntentUri = null;
        switch(choice){
            case 1: gmmIntentUri = Uri.parse("google.navigation:q=22.308047,113.9162921"); break;
            case 2: gmmIntentUri = null; break;
            case 3: gmmIntentUri = Uri.parse("geo:34.4320024,135.2282052?z=10&q=public transit"); break;
            default: return;
        }
        if(gmmIntentUri==null && choice==2){
            Intent intent=new Intent(this, MapsActivity.class);
            startActivity(intent);
            return;
        }

//        gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

}
