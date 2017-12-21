package com.sincha2aexample.josephjoey.newsreader.extras;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JosephJoey on 8/25/2017.
 */

public class ConnectionDetector extends Activity{

    protected Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
