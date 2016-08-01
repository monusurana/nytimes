package com.example.nytimes.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nytimes.R;
import com.example.nytimes.receivers.NetworkStateChanged;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.wvNews)
    WebView mNews;
    @BindView(R.id.pbNews)
    ProgressBar mProgressBar;
    @BindView(R.id.tvNewsError)
    TextView mNewsError;

    private String mUrl;

    public static final String NEWS_DATA = "NewsData";

    public static void startNewsDetailActivity(String url, Context context) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_DATA, url);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        initToolbar();

        if (savedInstanceState == null) {
            mUrl = getIntent().getStringExtra(NEWS_DATA);

            mNews.setWebViewClient(new MyBrowser());
            WebSettings webSettings = mNews.getSettings();
            webSettings.setLoadsImagesAutomatically(true);

            mNews.loadUrl(mUrl);
        } else {
            mNews.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news_detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        WebView wvArticle = (WebView) findViewById(R.id.wvNews);
        shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());

        miShare.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showloading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideloading();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mNews.saveState(outState);
    }

    public void showloading() {
        mProgressBar.setVisibility(View.VISIBLE);
        hideerror();
    }

    public void showerror() {
        mNewsError.setVisibility(View.VISIBLE);
        hideloading();
    }

    public void hideloading() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void hideerror() {
        mNewsError.setVisibility(View.GONE);
    }
}
