package com.example.studentvoice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageComplaintsActivity extends AppCompatActivity {
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintList;
    private DatabaseReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_complaints);

        RecyclerView rvComplaints = findViewById(R.id.rvComplaints);
        rvComplaints.setLayoutManager(new LinearLayoutManager(this));

        complaintList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaintList, true); // Enable delete button for admins
        rvComplaints.setAdapter(complaintAdapter);

        // Initialize Firebase
        complaintsRef = FirebaseDatabase.getInstance().getReference("complaints");

        // Fetch complaints from Firebase
        complaintsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Complaint complaint = snapshot.getValue(Complaint.class);
                    complaintList.add(complaint);
                }
                complaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageComplaintsActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
