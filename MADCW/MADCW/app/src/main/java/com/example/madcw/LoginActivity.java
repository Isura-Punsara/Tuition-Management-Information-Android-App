package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inputName, inputPassword;
    private MaterialAutoCompleteTextView roleSelector;
    private MaterialButton btnLogin;
    private TextView txtSignupLink;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        inputName = findViewById(R.id.inputName);
        inputPassword = findViewById(R.id.inputPassword);
        roleSelector = findViewById(R.id.roleSelector);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignupLink = findViewById(R.id.txtSignupLink);
        dbHelper = new DatabaseHelper(this);


        txtSignupLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Setting up role drop down
        String[] roles = {"Admin", "Teacher", "Student"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, roles);
        roleSelector.setAdapter(adapter);

        roleSelector.setOnClickListener(v -> roleSelector.showDropDown());

        // Show signup link only for admin
        roleSelector.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRole = roles[position];
            if ("Admin".equals(selectedRole)) {
                txtSignupLink.setVisibility(View.VISIBLE);
            } else {
                txtSignupLink.setVisibility(View.GONE);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String name = inputName.getText() != null ? inputName.getText().toString().trim() : "";
            String password = inputPassword.getText() != null ? inputPassword.getText().toString().trim() : "";
            String role = roleSelector.getText() != null ? roleSelector.getText().toString() : "";

            if (name.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Role-based login validation
            boolean isValid = false;
            if (role.equals("Admin")) {
                isValid = dbHelper.loginUser(name, password, role);
            } else if (role.equals("Teacher")) {
                isValid = dbHelper.loginTeacher(name, password);
            } else if (role.equals("Student")) {
                isValid = dbHelper.loginStudent(name, password);
            }


            if (isValid) {
                Toast.makeText(LoginActivity.this, "Login Successful!!", Toast.LENGTH_SHORT).show();

                switch (role) {
                    case "Admin":
                        startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                        break;
                    case "Teacher":
                        Intent teacherIntent = new Intent(LoginActivity.this,TeacherDashboardActivity.class);
                        teacherIntent.putExtra("teacher_name",name);
                        startActivity(teacherIntent);
                        break;
                    case "Student":
                        Intent intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
                        intent.putExtra("student_name", name);  // ✅ this must not be null
                        startActivity(intent);
                        break;
                }
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid User Credentials", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(LoginActivity.this, "Logging in as " + role + " with name " + name, Toast.LENGTH_SHORT).show();
        });
    }
}
