package com.example.virtualmeetingapp.activites;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualmeetingapp.R;
import com.example.virtualmeetingapp.adapter.AppointmentAdapter;
import com.example.virtualmeetingapp.models.AppointmentModel;
import com.example.virtualmeetingapp.utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AppointmentAdapter appointmentAdapter;
    RecyclerView appointmentList;
    private List<AppointmentModel> appointmentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        appointmentList = findViewById(R.id.appointmentList);
        Collections.sort(appointmentsList);

        appointmentAdapter = new AppointmentAdapter(appointmentsList);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        appointmentList.setLayoutManager(manager);
        appointmentList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        db.collection(Constants.COLLECTION_APPOINTMENTS).get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        List<AppointmentModel> appointmentModel = task.getResult().toObjects(AppointmentModel.class);
                        appointmentsList.addAll(appointmentModel);
                        appointmentList.setAdapter(appointmentAdapter);
                        appointmentAdapter.notifyDataSetChanged();
                    }
                });

    }
}