package com.bingo.libui.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class ViewItem<Model, VH extends RecyclerView.ViewHolder> {

    public abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, int viewType);

    public abstract void onBindViewHolder(@NonNull VH holder, Model model);

    public void onBindViewHolder(@NonNull VH holder, Model model, @NonNull List<Object> payloads) {
        onBindViewHolder(holder, model);
    }

    public abstract int getItemViewType(int position);

}
