package com.example.user.cathaymum;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 10/23/2016.
 */
public class Constant {
    public static final String IP="35.160.199.205";

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit){
        long diffInMillies= date2.getTime()- date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
