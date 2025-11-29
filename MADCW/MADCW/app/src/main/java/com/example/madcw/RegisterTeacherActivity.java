package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterTeacherActivity extends AppCompatActivity {

    TextInputEditText inputTeacherName, inputTeacherPassword, inputTeacherSubject;
    MaterialButton btnRegisterTeacher;
    TextView txtBack, txtViewManageTeachers;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        inputTeacherName = findViewById(R.id.inputTeacherName);
        inputTeacherPassword = findViewById(R.id.inputTeacherPassword);
        inputTeacherSubject = findViewById(R.id.inputTeacherSubject);
        btnRegisterTeacher = findViewById(R.id.btnRegisterTeacher);
        txtBack = findViewById(R.id.txtBack);
        txtViewManageTeachers = findViewById(R.id.txtViewManageTeachers);

        dbHelper = new DatabaseHelper(this);

        btnRegisterTeacher.setOnClickListener(v -> {
            String name = inputTeacherName.getText().toString().trim();
            String password = inputTeacherPassword.getText().toString().trim();
            String subject = inputTeacherSubject.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty() || subject.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertTeacher(name, password, subject);
            if (inserted) {
                Toast.makeText(this, "Teacher Registered!", Toast.LENGTH_SHORT).show();
                inputTeacherName.setText("");
                inputTeacherPassword.setText("");
                inputTeacherSubject.setText("");
            } else {
                Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        txtBack.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminDashboardActivity.class));
            finish();
        });

        txtViewManageTeachers.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageTeachersActivity.class));
        });
    }
}
