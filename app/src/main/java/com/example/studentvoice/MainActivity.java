package com.example.studentvoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnReportComplaints = findViewById(R.id.btnReportComplaints);
        Button btnViewComplaints = findViewById(R.id.btnViewComplaints);
        Button btnAddressedComplaints = findViewById(R.id.btnAddressedComplaints);

        btnReportComplaints.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ReportComplaintsActivity.class)));
        btnViewComplaints.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewComplaintsActivity.class)));
        btnAddressedComplaints.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddressedComplaintsActivity.class)));
    }
}
