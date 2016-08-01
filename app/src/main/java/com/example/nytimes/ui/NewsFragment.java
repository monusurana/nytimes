/**
 * Created by monusurana on 7/27/16.
 */
package com.example.nytimes.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nytimes.R;
import com.example.nytimes.data.NewsClient;
import com.example.nytimes.data.NewsInterface;
import com.example.nytimes.data.Result;
import com.example.nytimes.data.TopStories;
import com.example.nytimes.receivers.NetworkStateChanged;
import com.example.nytimes.receivers.NetworkStatus;
import com.example.nytimes.utils.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NewsFragment extends Fragment {

    private static int DIVIDER_PADDING_DP = 16;
    public static final String ARG_SECTION = "Section";
    private static final String SAVED_NEWS = "savedNews";

    @BindView(R.id.rvNews)
    RecyclerView mRecyclerView;
    @BindView(R.id.pbNews)
    ProgressBar mProgressBar;
    @BindView(R.id.tvNewsError)
    TextView mNewsError;

    @BindColor(R.color.colorPrimaryDark)
    int mPrimaryDark;

    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private NewsRecyclerViewAdapter.OnItemClickListener mClickListener;

    private List<Result> mTopStories = new ArrayList<>();

    public static NewsFragment newInstance(String section) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        mNewsRecyclerViewAdapter.setOnItemClickListener(null);
        mClickListener = null;
    }

    @Subscribe
    public void onEvent(NetworkStateChanged event) {
        if (!event.isInternetConnected()) {
            Toast.makeText(getActivity(), "No network Detected!", Toast.LENGTH_LONG).show();
        } else {
            if (mTopStories.size() == 0) {
                showloading();
                getTopStories(getArguments().getString(ARG_SECTION));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mTopStories != null) {
            outState.putParcelableArrayList(SAVED_NEWS, (ArrayList<? extends Parcelable>) mTopStories);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);

        getBundle(savedInstanceState);
        setUpRecycler();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setUpRecycler() {
        mRecyclerView.setAdapter(mNewsRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), R.drawable.divider, DIVIDER_PADDING_DP);
        mRecyclerView.addItemDecoration(itemDecoration);

        mClickListener = new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Result news, View view) {
                NewsDetailActivity.startNewsDetailActivity(news.getUrl(), getContext());
            }
        };
        mNewsRecyclerViewAdapter.setOnItemClickListener(mClickListener);
    }

    private void getTopStories(String section) {
        NewsInterface movieInterface = new NewsClient().getMovieInterface();
        Call<TopStories> call = movieInterface.getTopStories(section);

        call.enqueue(new Callback<TopStories>() {
            @Override
            public void onResponse(Call<TopStories> call, Response<TopStories> response) {
                Timber.d("Status Code = " + response.code());

                List<Result> doc = response.body().getResults();

                mTopStories.clear();
                mTopStories.addAll(doc);

                mNewsRecyclerViewAdapter.updateAdapter(mTopStories);

                showlist();
            }

            @Override
            public void onFailure(Call<TopStories> call, Throwable t) {
                Timber.d("Failure " + t.getLocalizedMessage());
            }
        });
    }

    public void getBundle(Bundle savedInstanceState) {
        List<Result> topstories;

        if (savedInstanceState == null) {
            if (NetworkStatus.getInstance(getActivity()).isOnline()) {
                showloading();
                getTopStories(getArguments().getString(ARG_SECTION));
            } else {
                showerror();
            }

        } else {
            if (savedInstanceState.containsKey(SAVED_NEWS)) {
                topstories = savedInstanceState.getParcelableArrayList(SAVED_NEWS);
                mTopStories = topstories;
                mNewsRecyclerViewAdapter.updateAdapter(mTopStories);
                showlist();
            } else {
                if (NetworkStatus.getInstance(getActivity()).isOnline()) {
                    showloading();
                    getTopStories(getArguments().getString(ARG_SECTION));
                } else {
                    showerror();
                }
            }
        }
    }

    public void showloading() {
        mProgressBar.setVisibility(View.VISIBLE);
        hidelist();
        hideerror();
    }

    public void showlist() {
        mRecyclerView.setVisibility(View.VISIBLE);
        hideloading();
        hideerror();
    }

    public void showerror() {
        mNewsError.setVisibility(View.VISIBLE);
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