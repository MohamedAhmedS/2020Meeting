package com.example.virtualmeetingapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualmeetingapp.activites.BaseActivity;
import com.example.virtualmeetingapp.activites.VideoChatActivity;
import com.example.virtualmeetingapp.models.User;
import com.example.virtualmeetingapp.utils.Constants;
import com.example.virtualmeetingapp.utils.Global;
import com.example.virtualmeetingapp.utils.ToastHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.Map;

public class CallingActivity extends BaseActivity {
    private static final String TAG = "CallingActivity";

    private TextView nameContact;
    private ImageView profileImage;
    private ImageView cancelCallBtn, acceptCallBtn;

    //    private DatabaseReference usersRef;
    private CollectionReference callingRef;

    private MediaPlayer mediaPlayer;

    private String callerID;
    private String receiverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        initXML();
        initVariables();

        Intent activityIntent = getIntent();
        if (activityIntent == null) {
            ToastHelper.showToast("Calling Crashed!");
            finish();
            return;
        }

        Map<String, String> callingMap = new HashMap<>();
        if (activityIntent.hasExtra("ringing") &&
                activityIntent.getBooleanExtra("ringing", false)) {
            callerID = activityIntent.getStringExtra("callerID");
            receiverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            acceptCallBtn.setVisibility(View.VISIBLE);
            checkIfCallEnded();
        } else {
            callerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            receiverID = activityIntent.getStringExtra("receiverID");

            //        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.COLLECTION_USER);
            callingRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_CALLING);

            callingMap.put("callerID", callerID);
            callingMap.put("receiverID", receiverID);
            callingMap.put("status", "ringing");
            callingRef.document(callerID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    callingRef.document(callerID).set(callingMap);
//                    callingMap.put("status", "ringing");
                    checkIfCallEnded();
                }
            });
        }

        setListeners();
        fetchAndShowUsername();
    }

    @Override
    public void initXML() {
        nameContact = findViewById(R.id.name_calling);
        cancelCallBtn = findViewById(R.id.cancel_call);
        acceptCallBtn = findViewById(R.id.make_call);
    }

    @Override
    public void initVariables() {
        mediaPlayer = MediaPlayer.create(this, R.raw.ringing);
        mediaPlayer.setLooping(true);
    }

    private void setListeners() {
        cancelCallBtn.setOnClickListener(v -> {
            mediaPlayer.stop();
            Global.listeningToCall = false;

            FirebaseFirestore.getInstance().collection(Constants.COLLECTION_CALLING).document(callerID).delete();
            finish();
        });

        acceptCallBtn.setOnClickListener(view -> {
            mediaPlayer.stop();

            Map<String, Object> callingMap = new HashMap<>();
            callingMap.put("status", "ongoing");

            FirebaseFirestore.getInstance().collection(Constants.COLLECTION_CALLING).document(callerID).update(callingMap);

            Intent intent = new Intent(this, VideoChatActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchAndShowUsername() {
        String receiverNameId = receiverID;
        if (!callerID.equals(currentUser.getUid())) {
            receiverNameId = callerID;
        }
        FirebaseFirestore.getInstance()
                .collection(Constants.COLLECTION_USER)
                .whereEqualTo("uid", receiverNameId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        User user = task.getResult().toObjects(User.class).get(0);
                        nameContact.setText(user.getUserName());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    private void checkIfCallEnded() {
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String checkCancelledID = currentUid;
//        if (!callerID.equals(currentUid)) {
//            checkCancelledID = receiverID;
//        }

        FirebaseFirestore
                .getInstance()
                .collection(Constants.COLLECTION_CALLING)
                .whereEqualTo("callerID", callerID)
                .whereEqualTo("receiverID", receiverID)
                .addSnapshotListener((snapshot, exception) -> {
                    if (snapshot != null && snapshot.getDocuments().isEmpty()) {
                        Global.listeningToCall = false;
                        mediaPlayer.stop();
                        finish();
                    } else if (snapshot.getDocuments() != null && snapshot.getDocuments().get(0).get("status").equals("ongoing")) {
                        mediaPlayer.stop();
                        Intent intent = new Intent(this, VideoChatActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}