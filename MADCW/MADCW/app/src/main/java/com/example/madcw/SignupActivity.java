package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText inputName, inputPassword;
    private MaterialAutoCompleteTextView roleSelector;
    private MaterialButton btnRegister;
    private DatabaseHelper dbHelper;
    private TextView txtBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        inputName = findViewById(R.id.inputName);
        inputPassword = findViewById(R.id.inputPassword);
        roleSelector = findViewById(R.id.roleSelector);
        btnRegister = findViewById(R.id.btnRegister);
        txtBackToLogin = findViewById(R.id.txtBackToLogin);

        txtBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        // Initialize DB
        dbHelper = new DatabaseHelper(this);

        // Set role (Admin only)
        roleSelector.setText("Admin");
        roleSelector.setEnabled(false);

        btnRegister.setOnClickListener(v -> {
            String name = inputName.getText() != null ? inputName.getText().toString().trim() : "";
            String password = inputPassword.getText() != null ? inputPassword.getText().toString().trim() : "";
            String role = "Admin";

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertUser(name, password, role);

            if (inserted) {
                Toast.makeText(this, "Admin registered successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish(); // Close signup activity
            } else {
                Toast.makeText(this, "Registration failed. Try another name.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
