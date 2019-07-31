package com.warm.demo.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warm.demo.R;
import com.warm.demo.databinding.FragmentNewsBinding;
import com.warm.router.annotations.Autowired;
import com.warm.router.annotations.Route;

import java.lang.reflect.Array;

@Route("news/home")
public class HomeFragment extends Fragment {
    private FragmentNewsBinding mBinding;

    @Autowired
    long id;

    @Autowired
    long typeId;

    @Autowired
    long[] moduleIds;

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
