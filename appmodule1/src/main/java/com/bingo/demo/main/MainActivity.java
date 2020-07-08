package com.bingo.demo.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.AppHybrid;
import com.bingo.demo.approuterpath.AppHybrid.Web.WebInfo;
import com.bingo.demo.approuterpath.CameraPath;
import com.bingo.demo.approuterpath.DataBinding;
import com.bingo.demo.approuterpath.Lifecycle;
import com.bingo.demo.approuterpath.Meizi;
import com.bingo.demo.approuterpath.User;
import com.bingo.demo.databinding.ActivityMainBinding;
import com.bingo.demo.login.rx.RxLogin;
import com.bingo.router.Request;
import com.bingo.router.RouteCallback;
import com.bingo.router.Router;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

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
                Router.create(User.Detail.class).openDetail(MainActivity.this,"10001");
            }
        });

        mBinding.bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Router.init();
                Router.create(User.Detail.class)
                        .getDetail("1")
                        .setRequestCode(100)
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
//                Router.newRequest("apphybrid/web?url=file:///android_asset/scheme.html")
                Router.newRequest(AppHybrid.Web.class)
                        .putSerializable("name", WebInfo.justUrl("file:///android_asset/scheme.html"))
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(Meizi.Home.class)
                        .build()
                        .startBy(MainActivity.this);
            }
        });

        mBinding.btEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest("empty")
                        .build()
                        .startBy(MainActivity.this, new RouteCallback() {
                            @Override
                            public void onNoFound(Request request) {
                                Toast.makeText(MainActivity.this, "未找到", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(Request request) {

                            }

                            @Override
                            public void onFail(Request request, Exception e) {

                            }
                        });
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
        mBinding.bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest(Lifecycle.class)
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

        mBinding.bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Router.newRequest(CameraPath.Camera2Path.class)
                                            .build().startBy(MainActivity.this);
                                } else {
                                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
