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

public class OfficersActivity extends AppCompatActivity {

    Button addPrisoners, logoutOfficer,
            viewConversations, approveVisitorsAppointment, viewList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officers);

        addPrisoners = findViewById(R.id.addPrisoners);
        logoutOfficer = findViewById(R.id.logoutOfficer);
        viewConversations = findViewById(R.id.viewConversations);
        approveVisitorsAppointment = findViewById(R.id.approveVisitorsAppointment);
        viewList = findViewById(R.id.viewList);


        addPrisoners.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddPrisonersActivity.class);
            startActivity(intent);
        });

        viewList.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
            startActivity(intent);
        });

        approveVisitorsAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AppointmentListActivity.class);
            startActivity(intent);
        });

        viewConversations.setOnClickListener(v->{
            startActivity(new Intent(this, ConversationsActivity.class));
        });

        logoutOfficer.setOnClickListener(v -> {
            mAuth.signOut();
            Global.clearCurrentUser();
            new SystemPrefs().clearUserSession();
            Intent intent = new Intent(getApplicationContext(), ClientTypeActivity.class);
            startActivity(intent);
            finish();
        });

    }
}