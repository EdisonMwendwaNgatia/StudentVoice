package com.example.studentvoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReportComplaintsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_complaints);

        Button btnHostelComplaint = findViewById(R.id.btnHostelComplaint);
        Button btnSportsComplaint = findViewById(R.id.btnSportsComplaint);
        Button btnMissingMarksComplaint = findViewById(R.id.btnMissingMarksComplaint);
        Button btnSecurityComplaint = findViewById(R.id.btnSecurityComplaint);
        Button btnLecturesComplaint = findViewById(R.id.btnLecturesComplaint);

        btnHostelComplaint.setOnClickListener(v -> openComplaintDetails("Hostel Complaint"));
        btnSportsComplaint.setOnClickListener(v -> openComplaintDetails("Sports Complaint"));
        btnMissingMarksComplaint.setOnClickListener(v -> openComplaintDetails("Missing Marks Complaint"));
        btnSecurityComplaint.setOnClickListener(v -> openComplaintDetails("Security (Theft) Complaint"));
        btnLecturesComplaint.setOnClickListener(v -> openComplaintDetails("Lectures Complaint"));
    }

    private void openComplaintDetails(String complaintType) {
        Intent intent = new Intent(ReportComplaintsActivity.this, ComplaintDetailsActivity.class);
        intent.putExtra("complaintType", complaintType);
        startActivity(intent);
    }
}
