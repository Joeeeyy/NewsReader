package com.sincha2aexample.josephjoey.newsreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sincha2aexample.josephjoey.newsreader.R;

/**
 * Created by JosephJoey on 8/20/2017.
 */

public class NewsHolder extends RecyclerView.ViewHolder {

    public TextView titleTV, descTV;
    public ImageView imgURL;

    public NewsHolder(View itemView) {
        super(itemView);

        titleTV = (TextView) itemView.findViewById(R.id.newsTitle);
        descTV = (TextView) itemView.findViewById(R.id.newsDesc);
        imgURL = (ImageView) itemView.findViewById(R.id.imgNews);

    }
}
