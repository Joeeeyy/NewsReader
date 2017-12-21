package com.sincha2aexample.josephjoey.newsreader.extras;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by JosephJoey on 8/25/2017.
 */

public class NewsReaderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));

        Picasso picasso = builder.build();
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(true);

        Picasso.setSingletonInstance(picasso);

    }
}
