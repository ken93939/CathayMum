package com.example.user.cathaymum;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class CountDown extends IntentService {
    private final String TAG=CountDown.class.getCanonicalName();
    private int seconds=0;
    private Messenger messenger=null;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CountDown(String name) {
        super(name);
    }

    public CountDown(){
        super("CountDown");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long time=intent.getLongExtra("time",0);
        seconds= (int) time;
        Log.i(TAG, String.valueOf(time));
        Log.i(TAG,"entering coundowntimer");

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
