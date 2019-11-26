package com.bingo.demo.databinding.recyclerView;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bingo.demo.R;
import com.bingo.libpublic.ContextProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewModel1 extends ViewModel {

    public MutableLiveData<Person> personLiveData=new MutableLiveData<>();
    public ObservableField<Drawable> placeHolder = new ObservableField<>();

    {
        placeHolder.set(ContextCompat.getDrawable(ContextProvider.autoContext, R.drawable.ic_launcher_background));
    }


    public LiveData<List<Person>> loadPerson() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Person("张三" + i + "-", i, "http://pic1.win4000.com/wallpaper/2019-11-19/5dd3bb96bd934.jpg", "大家好大家好大家好大家好大家好大家好大家好大家好大家好" + i));
        }
        MutableLiveData<List<Person>> personObservableList = new MutableLiveData<>();
        personObservableList.setValue(list);
        return personObservableList;
    }

    public void addPerson() {
        Observable.empty()
                .timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                        Person person = new Person("张三", 19999, "http://pic1.win4000.com/wallpaper/2019-11-21/5dd64e75eb5e6_270_185.jpg", "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                        personLiveData.setValue(person);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
