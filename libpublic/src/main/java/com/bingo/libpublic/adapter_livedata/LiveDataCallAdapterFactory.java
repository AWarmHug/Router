package com.bingo.libpublic.adapter_livedata;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    private boolean isAsync;

    public static LiveDataCallAdapterFactory create() {
        return new LiveDataCallAdapterFactory(false);
    }

    public LiveDataCallAdapterFactory(boolean isAsync) {
        this.isAsync = isAsync;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        if (rawType == RLiveData.class) {
            Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new LiveDataCallAdapter<>(observableType);
        }else {
            return retrofit.nextCallAdapter(this,returnType,annotations);
        }
    }
}
