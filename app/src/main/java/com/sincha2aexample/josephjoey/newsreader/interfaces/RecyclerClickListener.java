package com.sincha2aexample.josephjoey.newsreader.interfaces;

import android.view.View;

/**
 * Created by JosephJoey on 8/25/2017.
 */

public interface RecyclerClickListener {

    void onClick(View view, int position);
    void onLongClick(View view, int position);

}
