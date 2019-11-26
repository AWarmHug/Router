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
        private Call<R> mCall;
        private boolean isSuccess;

        public RLiveData(Call<R> mCall) {
            this.mCall = mCall;
        }

        @Override
        protected void onActive() {
            super.onActive();
            if (isSuccess){
                return;
            }
            this.mCall.enqueue(new Callback<R>() {
                @Override
                public void onResponse(Call<R> call, Response<R> response) {
                    if (call.isCanceled()) return;
                    postValue(response.body());
                    isSuccess=true;
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
            if (this.mCall.isExecuted()){
                this.mCall.cancel();
            }
            if (!this.mCall.isCanceled()) {
                this.mCall.cancel();
            }
        }
    }

}
