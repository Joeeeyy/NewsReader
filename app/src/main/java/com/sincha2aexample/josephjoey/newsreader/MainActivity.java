package com.sincha2aexample.josephjoey.newsreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sincha2aexample.josephjoey.newsreader.adapter.CustomAdapter;
import com.sincha2aexample.josephjoey.newsreader.extras.ConnectionDetector;
import com.sincha2aexample.josephjoey.newsreader.extras.Constants;
import com.sincha2aexample.josephjoey.newsreader.extras.RecyclerItemTouchListener;
import com.sincha2aexample.josephjoey.newsreader.interfaces.RecyclerClickListener;
import com.sincha2aexample.josephjoey.newsreader.model.NewsItem;
import com.sincha2aexample.josephjoey.newsreader.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private List<NewsItem> itemList = new ArrayList<>();
    private ProgressBar progress;
    private RelativeLayout relativeLayout;

    private ConnectionDetector connectionDetector;
    private DatabaseReference newsRootRef;
    private Toolbar toolbar;

    private boolean callMade = false;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News Articles");

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        progress = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.mainView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectionDetector = new ConnectionDetector(this);
        newsRootRef = FirebaseDatabase.getInstance().getReference().child(Constants.NEWS_DB);
        newsRootRef.keepSynced(true);

//        SharedPreferences.Editor editor =  getSharedPreferences("NETWORK_CALL", this.MODE_PRIVATE).edit();
//        editor.putBoolean("call_made", callMade);
//        editor.apply();

        if (connectionDetector.isConnected() == true){
            if (callMade == false){
                fetchNews();
                callMade = true;
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);

            }
        } else {
            Snackbar.make(relativeLayout, "Can't Fetch News, No Network Detected", Snackbar.LENGTH_INDEFINITE).show();
        }

    }

    private void fetchNews() {

        progress.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.NEWS_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null){
                            Log.d(TAG, "News Api Response is: \t" + response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray articles = jsonObject.getJSONArray("articles");
                                for (int i = 0; i < articles.length(); i++){
                                    JSONObject items = articles.getJSONObject(i);

                                    final String title_news = items.getString("title");
                                    final String desc_news = items.getString("description");
                                    final String urlImg = items.getString("urlToImage");
                                    final String author_news = items.getString("author");
                                    final String url = items.getString("url");
                                    final String publishedAt = items.getString("publishedAt");

                                    NewsItem newsItem = new NewsItem(author_news, title_news, desc_news, url, urlImg, publishedAt);
                                    itemList.add(newsItem);

                                    /**
                                     *  Save JSON Results to Firebase
                                     * */

                                    HashMap hashMap = new HashMap();

                                    hashMap.put("newsTitle", title_news);
                                    hashMap.put("newsDesc", desc_news);
                                    hashMap.put("newsImageUrl", urlImg);
                                    hashMap.put("newsAuthor", author_news);
                                    hashMap.put("newsUrl", url);
                                    hashMap.put("newsDate", publishedAt);

                                    newsRootRef.push().setValue(hashMap);

                                    customAdapter = new CustomAdapter(getApplicationContext(), itemList);
                                    progress.setVisibility(View.GONE);
                                    recyclerView.setAdapter(customAdapter);

                                    recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(getApplicationContext(), recyclerView, new RecyclerClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {

                                            Intent readIntent = new Intent(MainActivity.this, ReadArticleActivity.class);

                                            NewsItem item = itemList.get(position);
                                            readIntent.putExtra("news_url", item.getUrl());
                                            readIntent.putExtra("news_title", item.getTitle());
                                            Log.i(TAG, "Current Item URL is: \t" + item.getUrl());
                                            startActivity(readIntent);

                                        }

                                        @Override
                                        public void onLongClick(View view, int position) {

                                        }
                                    }));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i(TAG, "Response is null" + response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Could not Fetch News \n" + error.getMessage());
                Snackbar.make(relativeLayout, "Could not Fetch News", Snackbar.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getVolleySingleton(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh_data:
                dialog = new ProgressDialog(this);
                dialog.setMessage("Fetching Latest News");
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                refreshNewsData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshNewsData() {
        dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (callMade == true){
            //fetchNewsFromFirebase();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (callMade == true){
            //fetchNewsFromFirebase();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (callMade == true){
            //fetchNewsFromFirebase();
        }
    }
}
