package com.bingo.demo.network.adapter_livedata;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LivedataCallAdapter<R> implements CallAdapter<R, Object> {
    private Type mResponseType;

    public LivedataCallAdapter(Type responseType) {
        this.mResponseType = responseType;
    }

    @Override
    public Type responseType() {
        return mResponseType;
    }

    @Override
    public Object adapt(Call<R> call) {

        Call<R> newCall = call.clone();
        LiveData<R> liveData = new RLiveData<>(newCall);
        return liveData;
    }


    public static class RLiveData<R> extends LiveData<R> {
        private Call<R> call;

        public RLiveData(Call<R> call) {
            this.call = call;
        }

        @Override
        protected void onActive() {
            super.onActive();
            this.call.enqueue(new Callback<R>() {
                @Override
                public void onResponse(Call<R> call, Response<R> response) {
                    if (call.isCanceled()) return;
                    postValue(response.body());
                }
                @Override
                public void onFailure(Call<R> call, Throwable t) {
                    Log.d("onFailure", "onFailure: "+t);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            if (!this.call.isCanceled()) {
                this.call.cancel();
            }
        }
    }

}
