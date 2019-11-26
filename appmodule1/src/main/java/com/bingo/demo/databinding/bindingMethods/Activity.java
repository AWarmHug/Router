package com.bingo.demo.databinding.bindingMethods;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.DataBinding;
import com.bingo.demo.databinding.ActivityBindingmethodsBinding;
import com.bingo.router.annotations.Route;

@Route(pathClass = DataBinding.BindingMethods.class)
public class Activity extends AppCompatActivity {
    private ActivityBindingmethodsBinding mBinding;
    private ViewModel1 mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bindingmethods);
        mViewModel = ViewModelProviders.of(this).get(ViewModel1.class);
        mBinding.setVm(mViewModel);

        mBinding.setText("hhhh");

        mBinding.bt.setOnClickListener(v -> {
//            if (mBinding.dv.getVisibility()== View.VISIBLE) {
//                mBinding.dv.setVisibility(View.GONE);
//            }else {
//                mBinding.dv.setVisibility(View.VISIBLE);
//            }
            mViewModel.isDisplay.set(true);

            mBinding.bt.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Activity.this, "加载成功", Toast.LENGTH_SHORT).show();
                    mViewModel.isDisplay.set(false);
                }
            }, 3000);

        });


    }
}
