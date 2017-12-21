package com.sincha2aexample.josephjoey.newsreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sincha2aexample.josephjoey.newsreader.R;
import com.sincha2aexample.josephjoey.newsreader.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JosephJoey on 8/20/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<NewsHolder> {

    private final Context context;
    private List<NewsItem> itemsList;

    public CustomAdapter(Context context, List<NewsItem> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder viewholder, int position) {
        NewsItem newsItem = itemsList.get(position);
        viewholder.titleTV.setText(newsItem.getTitle());
        viewholder.descTV.setText(newsItem.getDesc());
        Picasso.with(context)
                .load(newsItem.getUtlToImage())
                .into(viewholder.imgURL);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}