package com.bingo.demo.meizi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.Meizi;
import com.bingo.demo.databinding.ActivityMeiziBinding;
import com.bingo.demo.model.Gank;
import com.bingo.demo.model.Result;
import com.bingo.libpublic.adapter_livedata.ResultObserver;
import com.bingo.router.annotations.Route;

import java.net.URL;
import java.util.List;
import java.util.Random;

@Route(pathClass = Meizi.class)
public class MeiziActivity extends AppCompatActivity {
    private static final String TAG = "MeiziActivityTag";

    private ActivityMeiziBinding mBinding;
    private MeiziActivityViewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(this).get(MeiziActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_meizi);
        mBinding.setVm(mModel);
        PagerAdapter adapter=new PagerAdapter();
        mBinding.pager.setAdapter(adapter);
        mModel.loadMeizi(1).observe(this, new ResultObserver<Gank<List<Result>>>() {
            @Override
            public void onChanged(Gank<List<Result>> listGank) {
                if (!listGank.isError()) {
                    adapter.mResults.addAll(listGank.getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError: "+t.toString());

            }
        });
    }
}
