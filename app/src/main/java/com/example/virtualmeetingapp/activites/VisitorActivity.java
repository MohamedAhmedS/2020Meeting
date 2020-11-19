package com.example.virtualmeetingapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualmeetingapp.ClientTypeActivity;
import com.example.virtualmeetingapp.R;
import com.example.virtualmeetingapp.utils.Global;
import com.example.virtualmeetingapp.utils.SystemPrefs;
import com.google.firebase.auth.FirebaseAuth;

public class VisitorActivity extends AppCompatActivity {

    Button logoutVisitor, viewList, profile, appointmentResponse;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        viewList = findViewById(R.id.viewList);
        profile = findViewById(R.id.viewProfile);
        appointmentResponse = findViewById(R.id.appointmentResponse);
        logoutVisitor = findViewById(R.id.logoutVisitor);

        viewList.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
            startActivity(intent);
        });

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), VisitorProfileActivity.class);
            startActivity(intent);
        });

        appointmentResponse.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AppointResponseListActivity.class);
            startActivity(intent);
        });

        logoutVisitor.setOnClickListener(v -> {
            mAuth.signOut();
            Global.clearCurrentUser();
            new SystemPrefs().clearUserSession();
            Intent intent = new Intent(getApplicationContext(), ClientTypeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}