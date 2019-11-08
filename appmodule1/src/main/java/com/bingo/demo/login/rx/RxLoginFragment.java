package com.bingo.demo.login.rx;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.bingo.demo.login.LoginActivity;

import io.reactivex.subjects.PublishSubject;

public class RxLoginFragment extends Fragment {


    public static final int REQUEST_CODE = 1;


    private static final String TAG = "RxPhotoFragment--";
    private PublishSubject<Boolean> publishSubject;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void login() {
        publishSubject = PublishSubject.create();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    boolean isLogin = data.getBooleanExtra(LoginActivity.NAME_IS_LOGIN, false);
                    publishSubject.onNext(isLogin);
                    publishSubject.onComplete();
                    break;
                default:
                    publishSubject.onError(new Throwable("no action"));
                    break;
            }
        }
    }

    public PublishSubject<Boolean> getSubject() {
        return publishSubject;
    }

}
