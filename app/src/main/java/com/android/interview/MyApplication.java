package com.android.interview;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication application;
    private boolean admin = false;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public boolean isAdmin() {
        return admin;
    }
}
