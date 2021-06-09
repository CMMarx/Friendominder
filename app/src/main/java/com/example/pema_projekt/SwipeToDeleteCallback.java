package com.example.pema_projekt;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerViewAdapter2 mAdapter;
    //private Drawable icon;
    //private final ColorDrawable background;

    public SwipeToDeleteCallback(RecyclerViewAdapter2 adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        /**
        icon = ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_delete_white_36);
        background = new ColorDrawable(Color.RED);
         **/
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }
}
