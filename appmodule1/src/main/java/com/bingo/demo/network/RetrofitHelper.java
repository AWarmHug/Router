package com.bingo.demo.network;

import com.bingo.demo.App;
import com.bingo.demo.BuildConfig;
import com.bingo.demo.Config;
import com.bingo.demo.network.adapter_livedata.LivedataCallAdapterFactory;
import com.bingo.demo.utils.CommonInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static Gson sGson;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    public static void init() {

        getClient();
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofit == null) {
                    retrofit = provideRetrofit(okHttpClient);
                }
            }
        }
    }

    public static OkHttpClient getClient() {
        if (okHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (okHttpClient == null) {
                    okHttpClient = provideOkHttpClient();
                }
            }
        }
        return okHttpClient;
    }

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new GsonBuilder()
//                .serializeNulls()
//                .registerTypeAdapter(Integer.class,new IntegerTypeAdapter())
                    .create();
        }
        return sGson;
    }


    public static OkHttpClient provideOkHttpClient() {
        //设置Http缓存
        Cache cache = new Cache(new File(App.getInstance().getExternalCacheDir(), "cache_http"), 1024 * 1024 * 10);


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
//                .addNetworkInterceptor(new CacheInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }


    public static Retrofit provideRetrofit(OkHttpClient client) {


        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(LivedataCallAdapterFactory.create())
                .build();
    }


    public static <T> T provideApi(Class<T> clazz) {
        if (retrofit == null) {
            init();
        }
        return retrofit.create(clazz);
    }

}
