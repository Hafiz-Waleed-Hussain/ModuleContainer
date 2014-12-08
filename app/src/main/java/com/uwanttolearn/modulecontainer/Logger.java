package com.uwanttolearn.modulecontainer;

import android.text.format.Time;
import android.util.Log;

/**
 * Created by Hafiz Waleed Hussain on 12/6/2014.
 */
public class Logger {

    private static final boolean DEBUG = true;
    private static final String TAG = "Check";

    public static final void showLog(String msg, Class<?> c) {
        if(DEBUG) {
            Log.d(TAG, c.getName());
            Log.d(TAG, msg);
            Log.d(TAG, "------------------");
        }
    }

    public static final void showLogWithTag(String msg,String tag, Class<?> c) {
        if (DEBUG) {
            Log.d(tag, c.getName());
            Log.d(tag, msg);
            Log.d(tag, "------------------");
        }
    }


    public static final void showErrorLog(String msg, Class<?> c) {
        if (DEBUG) {
            Log.e(TAG, c.getName());
            Log.e(TAG, msg);
            Log.e(TAG, "------------------");
        }
    }

    public static void showTimeLog(String msg, Class<?> c) {
        if (DEBUG) {
            Time time = new Time();
            time.setToNow();
            showLog(msg + " : " + time.hour + " : " + time.minute + " : "
                    + time.second, c);
        }
    }
}
