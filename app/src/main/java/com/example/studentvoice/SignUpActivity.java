package com.example.studentvoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText etAdmissionNumber, etPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etAdmissionNumber = findViewById(R.id.etAdmissionNumber);
        etPassword = findViewById(R.id.etPassword);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnAlreadySignedUp = findViewById(R.id.btnAlreadySignedUp); // New Button

        dbHelper = new DBHelper(this);

        btnSignUp.setOnClickListener(v -> {
            String admissionNumber = etAdmissionNumber.getText().toString();
            String password = etPassword.getText().toString();

            if (admissionNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                long result = dbHelper.insertUser(admissionNumber, password);
                if (result != -1) {
                    Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to LoginActivity after successful sign up
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close the SignUpActivity
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClickListener for the "Already Signed Up?" button
        btnAlreadySignedUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the SignUpActivity
        });
    }
}
