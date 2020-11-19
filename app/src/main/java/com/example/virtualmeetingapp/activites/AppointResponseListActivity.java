package com.example.virtualmeetingapp.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualmeetingapp.R;
import com.example.virtualmeetingapp.adapter.AppointmentResponseAdapter;
import com.example.virtualmeetingapp.models.AppointmentModel;
import com.example.virtualmeetingapp.models.User;
import com.example.virtualmeetingapp.utils.Constants;
import com.example.virtualmeetingapp.utils.Global;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AppointResponseListActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User currentUser = (User) Global.getCurrentUser();

    AppointmentResponseAdapter appointmentResponseAdapter;
    RecyclerView appointmentResponseList;

    TextView noRecord;
    ProgressBar pb;

    private List<AppointmentModel> appointmentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_response_list);

        appointmentResponseList = findViewById(R.id.appointmentResponseList);
        noRecord = findViewById(R.id.noRecord);
        pb = findViewById(R.id.pb);

        appointmentResponseAdapter = new AppointmentResponseAdapter(appointmentsList);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        appointmentResponseList.setLayoutManager(manager);
        appointmentResponseList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        appointmentHistory();

    }

    private void appointmentHistory(){
        db.collection(Constants.COLLECTION_APPOINTMENTS).whereEqualTo("visitorName", currentUser.getUserName()).get()
                .addOnCompleteListener(task -> {
                    pb.setVisibility(View.VISIBLE);
                    if (task.getResult() == null){
                        pb.setVisibility(View.GONE);
                        noRecord.setText(Constants.NO_RECORD);
                        noRecord.setVisibility(View.VISIBLE);
                    } else {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            pb.setVisibility(View.GONE);
                            noRecord.setVisibility(View.GONE);
                            List<AppointmentModel> appointmentModel = task.getResult().toObjects(AppointmentModel.class);
                            appointmentsList.addAll(appointmentModel);
                            appointmentResponseList.setAdapter(appointmentResponseAdapter);
                        }
                    }
                });
    }

}