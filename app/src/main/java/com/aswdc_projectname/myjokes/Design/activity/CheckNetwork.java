package com.aswdc_projectname.myjokes.Design.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.ContextThemeWrapper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by admin on 5/18/2017.
 */

public class CheckNetwork {

    public static boolean isOnline(Activity act) {
        ConnectivityManager cm = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isInternetAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(6000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress != null && !inetAddress.equals("");
    }

    public static boolean checkInternet(Activity act) {
        ProgressDialog pg = new ProgressDialog(act);
        pg.setMessage("Loading...");
        pg.setCancelable(false);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setIndeterminate(true);
        pg.show();
        if (isOnline(act)) {
            if (isInternetAvailable()) {
                if (pg.isShowing())
                    pg.dismiss();
                return true;
            } else {
                new AlertDialog.Builder(new ContextThemeWrapper(act,
                        android.R.style.Theme_Holo_Light_Dialog))
                        .setIcon(android.R.drawable.ic_dialog_alert) // icon that you want
                        .setMessage("Internet connection not found.") // message of dialog
                        .setCancelable(false)
                        .setPositiveButton("OK", null).show();
                if (pg.isShowing())
                    pg.dismiss();
                return false;
            }

        } else {
            new AlertDialog.Builder(new ContextThemeWrapper(act,
                    android.R.style.Theme_Holo_Light_Dialog))
                    .setIcon(android.R.drawable.ic_dialog_alert) // icon that you want
                    .setMessage("Check your Connection.") // message of dialog
                    .setCancelable(false)
                    .setPositiveButton("OK", null).show();
            if (pg.isShowing())
                pg.dismiss();
            return false;
        }
    }
}

