package com.example.studentvoice;

import android.os.Bundle;
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

public class AddressedComplaintsActivity extends AppCompatActivity {
    private AddressedComplaintAdapter addressedComplaintAdapter; // You need to create this adapter
    private List<AddressedComplaint> addressedComplaintList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressed_complaints); // Make sure this XML file exists

        RecyclerView rvAddressedComplaints = findViewById(R.id.rvAddressedComplaints);
        rvAddressedComplaints.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        DatabaseReference addressedComplaintsRef = FirebaseDatabase.getInstance().getReference("addressed_complaints");
        addressedComplaintList = new ArrayList<>();
        addressedComplaintAdapter = new AddressedComplaintAdapter(addressedComplaintList); // Create this adapter

        rvAddressedComplaints.setAdapter(addressedComplaintAdapter);

        // Fetch addressed complaints from Firebase
        addressedComplaintsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addressedComplaintList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AddressedComplaint addressedComplaint = snapshot.getValue(AddressedComplaint.class);
                    addressedComplaintList.add(addressedComplaint);
                }
                addressedComplaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddressedComplaintsActivity.this, "Failed to load addressed complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
