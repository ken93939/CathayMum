package com.example.user.cathaymum;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NotifyPreCheckInActivity extends AppCompatActivity {
    private final String TAG=this.getClass().getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_pre_check_in);
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

        final TextView textView= (TextView) findViewById(R.id.notifyPreCheckInText);
        Log.i(TAG,"dateb4conversion"+getIntent().getStringExtra("date"));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.setLenient(false);
        Date date= null;
        try {
            date = sdf.parse(getIntent().getStringExtra("date"));
            Log.i(TAG,date.toString()+"received time");
        } catch (ParseException e) {
            Log.e(TAG,e.getLocalizedMessage());
        }
        String queue=getIntent().getStringExtra("queue_no");
        Integer queue_no=0;
        if(queue.matches("\\d+")){
            queue_no=Integer.parseInt(queue);
        }
        Date cur=new Date();
        Log.i(TAG,cur.toString()+"current time");
        long diff=Constant.getDateDiff(cur,date, TimeUnit.SECONDS);
        Log.i(TAG,"difference in second"+diff);
        Log.i(TAG, "starting the service");


        new CountDownTimer(diff*1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "remaining" + millisUntilFinished);
                millisUntilFinished=millisUntilFinished/1000;
                textView.setText(String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "finishing countdowntimer");
                textView.setText(0 + "");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotifyPreCheckInActivity.this)
                        .setContentTitle("Time to check in")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Check in now"))
                        .setContentText("Check in")
                        .setSmallIcon(R.drawable.icon)
                        .setVibrate(new long[]{1000,1000,1000,1000,1000});
                Log.i(TAG,"setup the noti");
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(001, builder.build());
            }
        }.start();


//        Intent intent=new Intent(this,CountDown.class);
//        intent.putExtra("time",diff);
//        intent.putExtra("messenger", new Messenger(handler));
//        startService(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
