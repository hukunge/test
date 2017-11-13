package com.permission;

import android.app.Application;

/**
 * Created by Kellan on 2017/10/11.
 */

public class App extends Application{
    private static App instance;

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
