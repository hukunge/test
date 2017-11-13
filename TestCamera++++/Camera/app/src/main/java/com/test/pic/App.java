package com.test.pic;

import android.app.Application;

/**
 * Created by Kellan on 2017/11/10.
 */

public class App extends Application{
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
