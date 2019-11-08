package com.bingo.demo.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bingo.demo.R;
import com.bingo.demo.databinding.FragmentNewsBinding;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

@Route("news/home")
public class HomeFragment extends Fragment {
    @Parameter
    long id;
    @Parameter
    long typeId;
    @Parameter
    long[] moduleIds;
    private FragmentNewsBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        return mBinding.getRoot();
    }
}
