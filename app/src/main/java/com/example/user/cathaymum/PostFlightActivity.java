package com.example.user.cathaymum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PostFlightActivity extends AppCompatActivity implements View.OnClickListener{

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
        Intent intent= new Intent(this,ProcessPostFlightActivity.class);
        switch (v.getId()){
            case R.id.PostFlightAirport: intent.putExtra("choice",1);break;
            case R.id.PostFlightIndoor: intent.putExtra("choice",2); break;
            case R.id.PostFligthNearbyTransit: intent.putExtra("choice",3); break;
            default: intent.putExtra("choice",0); break;
        }
        startActivity(intent);
    }
}
