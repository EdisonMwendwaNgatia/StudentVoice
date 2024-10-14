package com.example.studentvoice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressingComplaintsActivity extends AppCompatActivity {
    private EditText etAddressedDetails;
    private DatabaseReference addressedComplaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressing_complaints); // Create this XML layout

        etAddressedDetails = findViewById(R.id.etAddressedDetails);
        Button btnSubmitAddressed = findViewById(R.id.btnSubmitAddressed);

        // Initialize Firebase
        addressedComplaintsRef = FirebaseDatabase.getInstance().getReference("addressed_complaints");

        btnSubmitAddressed.setOnClickListener(v -> {
            String addressedDetails = etAddressedDetails.getText().toString();
            if (!addressedDetails.isEmpty()) {
                // Create a unique ID for the addressed complaint
                String addressedId = addressedComplaintsRef.push().getKey();

                AddressedComplaint addressedComplaint = new AddressedComplaint(addressedId, addressedDetails); // Create this AddressedComplaint class
                addressedComplaintsRef.child(addressedId).setValue(addressedComplaint);

                Toast.makeText(AddressingComplaintsActivity.this, "Addressed information submitted", Toast.LENGTH_SHORT).show();
                finish(); // Go back to the View Complaints screen
            } else {
                Toast.makeText(AddressingComplaintsActivity.this, "Please enter addressed information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
