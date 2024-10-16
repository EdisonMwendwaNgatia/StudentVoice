package com.example.studentvoice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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

public class ViewComplaintsActivity extends AppCompatActivity {
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintList;
    private boolean isAdmin = false; // Default non-admin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);

        RecyclerView rvComplaints = findViewById(R.id.rvComplaints);
        rvComplaints.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        DatabaseReference complaintsRef = FirebaseDatabase.getInstance().getReference("complaints");
        complaintList = new ArrayList<>();

        // Check if the user is an admin (for now it's based on hardcoded logic, but you can replace it with actual logic)
        // For example, you might check the user's role in Firebase Authentication or a flag in the database
        if (checkIfUserIsAdmin()) {
            isAdmin = true; // Set to true if user is admin
        }

        // Pass the complaint list and admin status to the adapter
        complaintAdapter = new ComplaintAdapter(complaintList, isAdmin);
        rvComplaints.setAdapter(complaintAdapter);

        // Fetch complaints from Firebase
        complaintsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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
                Toast.makeText(ViewComplaintsActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });

        // Button to address complaints
        findViewById(R.id.btnAddressComplaint).setOnClickListener(v -> promptForAdminPassword());
    }

    private boolean checkIfUserIsAdmin() {
        // Placeholder for actual admin check logic, e.g., check user role from Firebase Authentication
        return false; // Replace this with real logic
    }

    private void promptForAdminPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Admin Password");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String password = input.getText().toString();
            if (password.equals("12345")) { // hardcoded password
                startActivity(new Intent(ViewComplaintsActivity.this, AddressingComplaintsActivity.class));
            } else {
                Toast.makeText(ViewComplaintsActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
