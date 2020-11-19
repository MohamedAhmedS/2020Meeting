package com.example.virtualmeetingapp.activites;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualmeetingapp.CallingActivity;
import com.example.virtualmeetingapp.MainActivity;
import com.example.virtualmeetingapp.models.Officer;
import com.example.virtualmeetingapp.models.Prisoner;
import com.example.virtualmeetingapp.models.User;
import com.example.virtualmeetingapp.models.Visitor;
import com.example.virtualmeetingapp.utils.Constants;
import com.example.virtualmeetingapp.utils.Global;
import com.example.virtualmeetingapp.utils.SystemPrefs;
import com.example.virtualmeetingapp.utils.ToastHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import okhttp3.internal.Util;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected User admin;
    protected Officer officer;
    protected Visitor visitor;
    protected User currentUser;
    protected Prisoner prisoner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForReceivingCall();

        String userType = new SystemPrefs().getUserType();
        currentUser = (User) Global.getCurrentUser();
        switch (userType) {
            case Constants.USER_TYPE_OFFICER:
                officer = (Officer) Global.getCurrentUser();
                break;
            case Constants.USER_TYPE_PRISONER:
                prisoner = (Prisoner) Global.getCurrentUser();
                break;
            case Constants.USER_TYPE_VISITOR:
                visitor = (Visitor) Global.getCurrentUser();
                break;
            case Constants.USER_TYPE_ADMIN:
                admin = (User) Global.getCurrentUser();
                break;
            default:
                ToastHelper.showToast("Unauthorized Logged User ... Crashing HAHAHA :D");
                finish();
                break;
        }
    }

    public abstract void initXML();

    public abstract void initVariables();


    private void checkForReceivingCall() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection(Constants.COLLECTION_CALLING)
                .whereEqualTo("receiverID", currentUserId)
                .whereEqualTo("status", "ringing")
                .limit(1)
                .addSnapshotListener((snapshot, exception) -> {
                    if (!Global.listeningToCall) {
                        if (snapshot != null && !snapshot.getDocuments().isEmpty()) {
                            Intent intent = new Intent(this, CallingActivity.class);
                            intent.putExtra("callerID", snapshot.getDocuments().get(0).get("callerID").toString());
                            intent.putExtra("ringing", true);
                            startActivity(intent);
                            Global.listeningToCall = true;
                        }
                    }
                });
    }
}
