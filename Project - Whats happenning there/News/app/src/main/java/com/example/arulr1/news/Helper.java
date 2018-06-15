package com.example.arulr1.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by arulr1 on 31/05/2018.
 */

public class Helper {

    public static String HTTPRequest(String url) {
        String Result = null;
        HttpURLConnection con=null;
        try {
            URL urlobject = new URL(url);
            con = (HttpURLConnection) urlobject.openConnection();
            con.connect();
            InputStream inputstream = con.getInputStream();

            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedReader = new BufferedReader(inputstreamreader);

            String resstring;
            StringBuilder stringBuilder = new StringBuilder();
            while ((resstring = bufferedReader.readLine()) != null) {
                stringBuilder = stringBuilder.append(resstring);
            }
            Result = stringBuilder.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return Result;
    }

    public static boolean isNetworkAvailable(Context context)
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected; //((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


}
