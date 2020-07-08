package com.bingo.demo.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.News;
import com.bingo.demo.approuterpath.User;
import com.bingo.demo.databinding.FragmentNewsBinding;
import com.bingo.router.Router;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

@Route(pathClass = News.Home.class)
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.create(HomeFragment.this, User.Detail.class)
                        .getDetail("1")
                        .setRequestCode(100)
                        .build()
                        .startBy(HomeFragment.this);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBinding.tv.setText(String.valueOf(requestCode));
    }
}
