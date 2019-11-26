package com.bingo.demo.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.AppHybrid;
import com.bingo.demo.approuterpath.DataBinding;
import com.bingo.demo.approuterpath.Meizi;
import com.bingo.demo.approuterpath.User;
import com.bingo.demo.databinding.ActivityMainBinding;
import com.bingo.demo.login.rx.RxLogin;
import com.bingo.router.Router;
import com.bingo.router.annotations.Route;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxLogin.isLogin = false;
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setVm(mViewModel);
        mBinding.bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.path.set("user/detail");
//                Router.init();
                Router.newRequest("user/detail")
                        .putLong("id", 1)
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Router.init();
                Router.create(User.Detail.class)
                        .getDetail(1)
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,DetailActivity.class));
                mViewModel.path.set("test/detail");
                Router.newRequest("test/detail")
                        .putInt("type", 1)
                        .build()
                        .startBy(MainActivity.this);
            }
        });
        mBinding.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.path.set("https://www.jianshu.com/p/d57abb5b87f3");

                Router.newRequest("https://www.jianshu.com/p/d57abb5b87f3")
                        .putInt("type", 1)
                        .build()
                        .startBy(MainActivity.this);
            }
        });
        mBinding.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest("myapp://demo.app/userdetail?id=10001")
                        .putInt("type", 1)
                        .putLongArray("ids", new long[]{1, 2, 3})
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.build("app2/car")
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(AppHybrid.Web.class)
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(Meizi.class)
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(DataBinding.NotifyPropertyChanged.class)
                        .build().startBy(MainActivity.this);
            }
        });

        mBinding.bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(DataBinding.RecyclerView.class)
                        .build().startBy(MainActivity.this);
            }
        });

        mBinding.bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(DataBinding.BindingMethods.class)
                        .build().startBy(MainActivity.this);
            }
        });
        mBinding.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(mBinding.btEdit.getText().toString())
                        .build().startBy(MainActivity.this);
            }
        });
//        mViewModel.clickPath.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                mBinding.clickPath.setText(s);
//            }
//        });

    }
}