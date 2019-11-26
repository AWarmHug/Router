package com.bingo.demo.meizi;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bingo.demo.R;
import com.bingo.demo.databinding.MeiziPagerItemBinding;
import com.bingo.demo.model.Result;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder> {
    public List<Result> mResults;

    public PagerAdapter() {
        this.mResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.meizi_pager_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.setResult(mResults.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private MeiziPagerItemBinding mBinding;
        private PhotoView photo;
        ViewHolder(@NonNull MeiziPagerItemBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
            photo=itemView.findViewById(R.id.photo);
        }

    }


}
