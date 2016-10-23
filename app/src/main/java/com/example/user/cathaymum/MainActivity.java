package com.example.user.cathaymum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId()){
            case R.id.preCheckInButton: intent=new Intent(this,PreCheckInActivity.class);break;
            case R.id.preFlightButton: intent= new Intent(this, PreFlightActivity.class);break;
            case R.id.InFlightButton: intent= new Intent(this, InFlightOrderActivity.class); break;
            case R.id.PostFlightButton: intent = new Intent(this, PostFlightActivity.class); break;
        }
        if(intent !=null){
            startActivity(intent);
        }
    }
}
