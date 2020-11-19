package com.example.virtualmeetingapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.virtualmeetingapp.ClientTypeActivity;
import com.example.virtualmeetingapp.R;
import com.example.virtualmeetingapp.utils.Global;
import com.example.virtualmeetingapp.utils.SystemPrefs;
import com.google.firebase.auth.FirebaseAuth;

public class PrisonersActivity extends BaseActivity {

    Button logoutPrisoner, viewConversations;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisoners);

        initXML();
        initVariables();
        setListeners();
    }

    @Override
    public void initXML() {
        logoutPrisoner = findViewById(R.id.logoutPrisoner);
        viewConversations = findViewById(R.id.viewConversations);
    }

    @Override
    public void initVariables() {

    }

    private void setListeners() {
        viewConversations.setOnClickListener(v -> {
            startActivity(new Intent(this, ConversationsActivity.class));
        });

        logoutPrisoner.setOnClickListener(v -> {
            mAuth.signOut();
            Global.clearCurrentUser();
            new SystemPrefs().clearUserSession();
            Intent intent = new Intent(getApplicationContext(), ClientTypeActivity.class);
            startActivity(intent);
            finish();
        });
    }

}