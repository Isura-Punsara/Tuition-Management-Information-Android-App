package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterResultsActivity extends AppCompatActivity {

    TextView txtBack, txtTeacherSubject;
    EditText inputStudentName, inputResultMarks;
    Button btnSubmitResult;

    DatabaseHelper dbHelper;
    String loggedInTeacherName = "";
    String teacherSubject = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_results);

        txtBack = findViewById(R.id.txtBackResults);
        txtTeacherSubject = findViewById(R.id.txtTeacherSubject);
        inputStudentName = findViewById(R.id.inputStudentName);
        inputResultMarks = findViewById(R.id.inputResultMarks);
        btnSubmitResult = findViewById(R.id.btnSubmitResult);

        dbHelper = new DatabaseHelper(this);

        // Get teacher info from intent
        loggedInTeacherName = getIntent().getStringExtra("teacher_name");
        teacherSubject = getIntent().getStringExtra("teacher_subject");

        if (teacherSubject == null) teacherSubject = "N/A";

        txtTeacherSubject.setText("Subject: " + teacherSubject);

        // Back button
        txtBack.setOnClickListener(v -> {
            Intent intent = new Intent(EnterResultsActivity.this, TeacherDashboardActivity.class);
            intent.putExtra("teacher_name", loggedInTeacherName);
            startActivity(intent);
            finish();
        });

        // Submit button logic
        btnSubmitResult.setOnClickListener(v -> {
            String studentName = inputStudentName.getText().toString().trim();
            String marks = inputResultMarks.getText().toString().trim();

            if (studentName.isEmpty() || marks.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.insertOrUpdateResult(studentName, teacherSubject, loggedInTeacherName, marks);
            if (success) {
                Toast.makeText(this, "Result saved successfully", Toast.LENGTH_SHORT).show();
                inputStudentName.setText("");
                inputResultMarks.setText("");
            } else {
                Toast.makeText(this, "Failed to save result", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
