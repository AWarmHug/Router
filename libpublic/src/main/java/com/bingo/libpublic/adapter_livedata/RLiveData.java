package com.bingo.libpublic.adapter_livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RLiveData<R> extends LiveData<Result<R>> {

    private Call<R> mCall;
    private AtomicBoolean isSuccess=new AtomicBoolean(false);

    public RLiveData(Call<R> mCall) {
        this.mCall = mCall;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (isSuccess.compareAndSet(false,true)) {
            this.mCall.enqueue(new Callback<R>() {
                @Override
                public void onResponse(Call<R> call, Response<R> response) {
                    if (call.isCanceled()) return;
                    postValue(new Result<R>(response.body()));
                }

                @Override
                public void onFailure(Call<R> call, Throwable t) {
                    postValue(new Result<R>(t));
                }
            });        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (this.mCall.isExecuted()) {
            this.mCall.cancel();
        }
        if (!this.mCall.isCanceled()) {
            this.mCall.cancel();
        }
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Result<R>> observer) {
        super.observe(owner, observer);
    }

    public void observe(@NonNull LifecycleOwner owner, final ResultObserver<? super R> observer) {
        observe(owner, new Observer<Result<R>>() {
            @Override
            public void onChanged(Result<R> rResult) {
                if (rResult.getBody() != null) {
                    observer.onChanged(rResult.getBody());
                }
                if (rResult.getT() != null) {
                    observer.onError(rResult.getT());
                }

            }
        });
    }
}
