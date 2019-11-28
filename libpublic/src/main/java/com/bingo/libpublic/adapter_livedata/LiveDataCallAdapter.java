package com.bingo.libpublic.adapter_livedata;

import androidx.lifecycle.LiveData;

import com.bingo.libpublic.R;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class LiveDataCallAdapter<R> implements CallAdapter<R, Object> {
    private Type mResponseType;

    public LiveDataCallAdapter(Type responseType) {
        this.mResponseType = responseType;
    }

    @Override
    public Type responseType() {
        return mResponseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        Call<?> newCall = call.clone();
        LiveData<?> liveData = new RLiveData<>(newCall);
        return liveData;
    }

}
