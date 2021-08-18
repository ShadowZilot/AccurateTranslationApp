package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.databinding.TagItemBinding;
import com.human_developing_soft.accurate_translation.translation.domain.TagLoadingObserver;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagViewHolder>
        implements TagLoadingObserver, OnTagPressed {
    private final List<BookmarkTag> mTags = new ArrayList<>();

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagViewHolder(
                TagItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.bind(mTags.get(position), this);
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onTagLoaded(List<String> tags) {
        mTags.clear();
        List<BookmarkTag> bookmarkTags = new ArrayList<>();
        for (String tagName: tags) {
            bookmarkTags.add(new BookmarkTag.Base(
               tagName, false
            ));
        }
        mTags.addAll(bookmarkTags);
        notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onPressTag(Integer tagPosition) {
        for (int i = 0; i < mTags.size(); i++) {
            mTags.get(i).changedTag(false);
        }
        mTags.get(tagPosition).changedTag(true);
        notifyDataSetChanged();
    }
}
