package com.bingo.demo.network.adapter_livedata;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LivedataCallAdapterFactory extends CallAdapter.Factory {

    private boolean isAsync;

    public static LivedataCallAdapterFactory create() {
        return new LivedataCallAdapterFactory(false);
    }

    public LivedataCallAdapterFactory(boolean isAsync) {
        this.isAsync = isAsync;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        if (rawType == LiveData.class) {
            Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new LivedataCallAdapter<>(observableType);
        }else {
            return retrofit.nextCallAdapter(this,returnType,annotations);
        }
    }
}
