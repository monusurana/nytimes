package com.example.nytimes.ui;

/**
 * Created by monusurana on 7/27/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nytimes.R;
import com.example.nytimes.data.Multimedium;
import com.example.nytimes.data.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_HEADER = 1;

    public List<Result> mNews = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Result news, View parent);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NewsRecyclerViewAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == TYPE_DEFAULT) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_list_item, parent, false);
            return new ViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_list_item_main, parent, false);
            return new ViewHolderMain(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Result news = mNews.get(position);

        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mAuthor.setText(news.getByline());
            ((ViewHolder) holder).mTitle.setText(news.getTitle());

            if (news.getMultimedia().size() > 0) {
                String url = news.getMultimedia().get(0).getUrl();

                Glide.with(((ViewHolder) holder).mThumb.getContext())
                        .load(url)
                        .centerCrop()
                        .crossFade()
                        .into(((ViewHolder) holder).mThumb);
            }

            ((ViewHolder) holder).mDescription.setText(news.getAbstract());
        } else if (holder instanceof ViewHolderMain) {
            ((ViewHolderMain) holder).tvNewsTitle.setText(news.getTitle());
            ((ViewHolderMain) holder).tvNewsDescription.setText(news.getAbstract());
            ((ViewHolderMain) holder).tvNewsAuthor.setText(news.getByline());

            Multimedium largest = getLargestImage(news);

            Glide.with(((ViewHolderMain) holder).ivNewsimage.getContext())
                    .load(largest.getUrl())
                    .centerCrop()
                    .crossFade()
                    .into(((ViewHolderMain) holder).ivNewsimage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && getLargestImage(mNews.get(0)) != null)
            return TYPE_HEADER;

        return TYPE_DEFAULT;
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public void updateAdapter(List<Result> news) {
        mNews = news;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_news_title)
        TextView mTitle;
        @BindView(R.id.list_item_news_description)
        TextView mDescription;
        @BindView(R.id.list_item_new_thumb)
        ImageView mThumb;
        @BindView(R.id.list_item_news_author)
        TextView mAuthor;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(mNews.get(getLayoutPosition()), v);
                }
            });
        }
    }

    public class ViewHolderMain extends RecyclerView.ViewHolder {
        @BindView(R.id.ivNewsImage)
        ImageView ivNewsimage;
        @BindView(R.id.tvNewsTitle)
        TextView tvNewsTitle;
        @BindView(R.id.tvNewsDescription)
        TextView tvNewsDescription;
        @BindView(R.id.tvNewsAuthor)
        TextView tvNewsAuthor;

        public ViewHolderMain(final View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(mNews.get(getLayoutPosition()), v);
                }
            });
        }
    }

    private Multimedium getLargestImage(Result r) {
        Multimedium largestImage = null;
        for (Multimedium multimedia : r.getMultimedia()) {
            if (multimedia.getType().equalsIgnoreCase("image")) {
                if (largestImage == null || multimedia.getWidth() > largestImage.getWidth()) {
                    largestImage = multimedia;
                }
            }
        }

        return largestImage;
    }
}

