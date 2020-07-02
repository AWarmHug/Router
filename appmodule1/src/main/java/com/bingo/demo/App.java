package com.bingo.demo;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    private static App mApp;

    private Map<String, String> mErrorRoute = new HashMap<>();

    {
        mErrorRoute.put("/meizi/home", "app://route/apphybrid/web?url=https://www.baidu.com/");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public Map<String, String> getErrorRoute() {
        return mErrorRoute;
    }

    public static App getInstance() {
        return mApp;
    }
}
