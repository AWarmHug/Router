package com.bingo.demo.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainActivityViewModel extends ViewModel {


    public MutableLiveData<String> clickPath =new MutableLiveData<>();

    public ObservableField<String> path=new ObservableField<>();

}
