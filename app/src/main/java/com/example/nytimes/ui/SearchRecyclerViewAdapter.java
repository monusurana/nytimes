/*
 * Copyright 2016 Monu Surana
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.nytimes.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nytimes.R;
import com.example.nytimes.data.Doc;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DEFAULT = 0;

    private List<Doc> mItems;
    private OnItemClickListener mListener;

    /**
     * Constructor
     */
    public SearchRecyclerViewAdapter() {
    }

    /**
     * Interface to get Edit and Delete events in the activity
     */
    public interface OnItemClickListener {
        void onItemClick(Doc item, View parent);
    }

    /**
     * Function to set the listener for Edit and Delete events
     *
     * @param listener Listener for the events
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView;

        if (viewType == TYPE_DEFAULT) {
            itemView = inflater.inflate(R.layout.rv_search_listitem, parent, false);
            return new ViewHolderItem(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Doc movie = mItems.get(position);

        if (holder instanceof ViewHolderItem) {

            ImageView posterView = ((ViewHolderItem) holder).ivThumbnail;
            TextView titleView = ((ViewHolderItem) holder).tvTitle;
            titleView.setText(movie.getHeadline().getMain());

            if (movie.getMultimedia().size() > 0) {
                posterView.setVisibility(View.VISIBLE);
                String url = "https://www.nytimes.com/" + movie.getMultimedia().get(0).getUrl();

                Glide.with(posterView.getContext())
                        .load(url)
                        .fitCenter()
                        .centerCrop()
                        .crossFade()
                        .into(posterView);
            } else {
                posterView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();

        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_DEFAULT;
    }

    /**
     * Function to update the list of TodoItems
     *
     * @param tasks List of TodoItems as an input
     */
    public void updateAdapter(List<Doc> tasks) {
        mItems = null;
        mItems = tasks;

        notifyDataSetChanged();
    }

    /**
     * View Holder for Recycler View Item
     */
    public class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ViewHolderItem(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(mItems.get(getLayoutPosition()), v);
                }
            });
        }
    }
}