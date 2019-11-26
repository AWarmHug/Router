package com.bingo.demo.databinding.notifyPropertyChanged;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.DataBinding;
import com.bingo.demo.databinding.Activity1Binding;
import com.bingo.router.annotations.Route;

@Route(pathClass = DataBinding.NotifyPropertyChanged.class)
public class Activity extends AppCompatActivity {
    private Activity1Binding mBinding;
    private ViewModel1 viewModel1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_1);
        viewModel1= ViewModelProviders.of(this).get(ViewModel1.class);
        Person person = new Person("张三", 100, "http://pic1.win4000.com/wallpaper/2019-11-21/5dd64e7540bb2.jpg");
        mBinding.setPerson(person);
        mBinding.setVm(viewModel1);
        mBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.addId1();
            }
        });
        mBinding.btIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setImg("http://pic1.win4000.com/wallpaper/2019-11-20/5dd4e1ec9f1ca.jpg");
            }
        });
    }
}
