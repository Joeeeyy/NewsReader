package com.sincha2aexample.josephjoey.newsreader;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sincha2aexample.josephjoey.newsreader.extras.ConnectionDetector;

public class ReadArticleActivity extends AppCompatActivity {

    public static String news_url = null;
    public static String news_title = null;

    private Toolbar toolbar;
    private WebView webView;
    private ProgressDialog progressDialog;

    private ConnectionDetector connectionDetector;

    private static final String TAG = ReadArticleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        news_url = getIntent().getStringExtra("news_url");
        news_title = getIntent().getStringExtra("news_title");
        String title = news_title;

        if (news_url == null && news_title == null){
            Snackbar.make(findViewById(android.R.id.content), "Nothing Received", Snackbar.LENGTH_LONG).show();
        } else {
            Log.i(TAG, "Received url is" + news_url);
            Log.i(TAG, "Received title is" + news_title);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title.substring(0, 10));

        connectionDetector = new ConnectionDetector(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (connectionDetector.isConnected()){
            webView.loadUrl(news_url);
        }

        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
