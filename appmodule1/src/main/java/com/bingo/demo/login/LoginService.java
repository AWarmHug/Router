package com.bingo.demo.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bingo.demo.approuterpath.Login;
import com.bingo.router.annotations.Route;

@Route(pathClass = Login.LoginService.class)
public class LoginService extends Service {
    private static final String TAG = "LoginService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
