package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.databinding.BookmarkItemBinding;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder>
        implements BookmarkObserver {
    private final List<Bookmark> mBookmarks = new ArrayList<>();

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookmarkItemBinding binding = BookmarkItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        holder.bind(
            mBookmarks.get(position).binding()
        );
    }

    @Override
    public int getItemCount() {
        return mBookmarks.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBookmarkUpdate(List<Bookmark> bookmarks) {
        mBookmarks.clear();
        mBookmarks.addAll(bookmarks);
        notifyDataSetChanged();
    }
}
