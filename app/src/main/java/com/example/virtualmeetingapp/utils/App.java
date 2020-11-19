package com.example.virtualmeetingapp.utils;

import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.virtualmeetingapp.CallingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class App extends MultiDexApplication {
    public static String APP_TAG = "Virtual Meeting App";
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
