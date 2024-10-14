package com.example.studentvoice;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintDetailsActivity extends AppCompatActivity {
    private EditText etComplaintDetails;
    private DatabaseReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_complaints_details);

        TextView tvComplaintType = findViewById(R.id.tvComplaintType);
        etComplaintDetails = findViewById(R.id.etComplaintDetails);
        Button btnSubmitComplaint = findViewById(R.id.btnSubmitComplaint);

        // Get the complaint type from intent
        String complaintType = getIntent().getStringExtra("complaintType");
        tvComplaintType.setText(complaintType);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        complaintsRef = database.getReference("complaints");

        btnSubmitComplaint.setOnClickListener(v -> {
            String complaintDetails = etComplaintDetails.getText().toString();
            if (!complaintDetails.isEmpty()) {
                // Create a unique ID for the complaint
                String complaintId = complaintsRef.push().getKey();

                Complaint complaint = new Complaint(complaintId, complaintType, complaintDetails, 0); // 0 likes initially
                complaintsRef.child(complaintId).setValue(complaint);

                Toast.makeText(ComplaintDetailsActivity.this, "Complaint submitted", Toast.LENGTH_SHORT).show();
                finish(); // Go back to report complaints screen
            } else {
                Toast.makeText(ComplaintDetailsActivity.this, "Please enter complaint details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
