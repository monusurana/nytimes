package com.example.nytimes.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nytimes.R;
import com.example.nytimes.data.Articles;
import com.example.nytimes.data.Doc;
import com.example.nytimes.data.NewsClient;
import com.example.nytimes.data.NewsInterface;
import com.example.nytimes.providers.SearchSuggestionProvider;
import com.example.nytimes.receivers.NetworkStateChanged;
import com.example.nytimes.receivers.NetworkStatus;
import com.example.nytimes.utils.EndlessRecyclerViewScrollListener;
import com.example.nytimes.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends AppCompatActivity implements SearchRecyclerViewAdapter.OnItemClickListener {
    private static final String SAVED_SEARCH = "savedSearch";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.ivSearchIcon)
    ImageView mSearchIcon;
    @BindView(R.id.pbNews)
    ProgressBar mProgressBar;
    @BindView(R.id.tvNewsError)
    TextView mNewsError;

    private SearchRecyclerViewAdapter mSearchRecyclerViewAdapter;
    private List<Doc> mSearchResults = new ArrayList<>();

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initToolbar();
        setupRecyclerView();

        EventBus.getDefault().register(this);

        getBundle(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(NetworkStateChanged event) {
        if (!event.isInternetConnected()) {
            Toast.makeText(this, "No network Detected!", Toast.LENGTH_LONG).show();
        } else {
            if (mSearchResults.size() == 0 && mQuery != null) {
                showloading();
                getSearchData(mQuery, 0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings: {
                SettingsFragment frag = SettingsFragment.newInstance();
                FragmentManager fm = getSupportFragmentManager();
                frag.show(fm, "SettingsFragment");
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.expandActionView();
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;

                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                    showloading();
                    getSearchData(query, 0);
                } else {
                    showerror();
                }

                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
                        SearchActivity.this,
                        SearchSuggestionProvider.AUTHORITY,
                        SearchSuggestionProvider.MODE);
                suggestions.saveRecentQuery(query, null);

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mSearchResults != null) {
            outState.putParcelableArrayList(SAVED_SEARCH, (ArrayList<? extends Parcelable>) mSearchResults);
        }
    }

    /**
     * Functon to initialize Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Function to setup Recycler View
     */
    private void setupRecyclerView() {
        mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter();

        mRecyclerView.setAdapter(mSearchRecyclerViewAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getSearchData(mQuery, page);
            }
        });

        mSearchRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(Doc item, View parent) {
        NewsDetailActivity.startNewsDetailActivity(item.getWebUrl(), this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Function to call Movie server to fetch Now Playing Movies
     */
    private void getSearchData(String query, final int page) {
        NewsInterface movieInterface = new NewsClient().getMovieInterface();
        Call<Articles> call = movieInterface.getArticles(query, page, Preferences.optionalQueryParameters(this));

        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                Timber.d("Status Code = " + response.code());

                List<Doc> doc = response.body().getResponse().getDocs();

                mSearchIcon.setVisibility(View.GONE);

                if (page == 0) {
                    mSearchResults.clear();
                    mSearchResults.addAll(doc);
                    mSearchRecyclerViewAdapter.updateAdapter(mSearchResults);
                } else {
                    int nextItemPosition = mSearchResults.size();
                    mSearchResults.addAll(doc);
                    mSearchRecyclerViewAdapter.notifyItemRangeInserted(nextItemPosition, mSearchResults.size());
                }

                showlist();
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Timber.d("Failure " + t.getLocalizedMessage());
            }
        });
    }

    public void getBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_SEARCH)) {
            mSearchResults = savedInstanceState.getParcelableArrayList(SAVED_SEARCH);
            mSearchRecyclerViewAdapter.updateAdapter(mSearchResults);
            mSearchIcon.setVisibility(View.GONE);
        }
    }

    public void showloading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mSearchIcon.setVisibility(View.GONE);
        hidelist();
        hideerror();
    }

    public void showlist() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mSearchIcon.setVisibility(View.GONE);
        hideloading();
        hideerror();
    }

    public void showerror() {
        mNewsError.setVisibility(View.VISIBLE);
        mSearchIcon.setVisibility(View.GONE);
        hidelist();
        hideloading();
    }

    public void hideloading() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void hidelist() {
        mRecyclerView.setVisibility(View.GONE);
    }

    public void hideerror() {
        mNewsError.setVisibility(View.GONE);
    }
}
