package com.bingo.libpublic.adapter_livedata;

import androidx.lifecycle.Observer;

public abstract class ResultObserver<R> implements Observer<R> {


    public abstract void onError(Throwable t);
}
