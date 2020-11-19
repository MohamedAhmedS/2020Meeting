package com.example.virtualmeetingapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Global {
    private static Object currentUser;
    public static boolean listeningToCall = false;


    public static Object getCurrentUser() {
        if (currentUser == null) {
            currentUser = new SystemPrefs().getUserSession();
        }
        return currentUser;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }

    public static String getDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getTopActivityName(Context context) {
        ActivityManager result = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = result.getRunningTasks(Integer.MAX_VALUE);
        if (services != null && services.size() > 0) {
            return services.get(0).topActivity != null ? services.get(0).topActivity.getClassName() : "";
        }
        return "";
    }

    public static boolean isGreaterDate(Date operate_date_1, Date operate_date_2) {
        return operate_date_1.getTime() > operate_date_2.getTime();
    }

    public static String randomString() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
