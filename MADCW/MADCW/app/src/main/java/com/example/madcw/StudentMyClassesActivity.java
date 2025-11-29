package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentMyClassesActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listAssignedTeachers;
    String loggedInStudentName;
    TextView txtStudentMyClassesTitle, txtBackMyClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_my_classes);

        dbHelper = new DatabaseHelper(this);
        listAssignedTeachers = findViewById(R.id.listAssignedTeachers);
        txtStudentMyClassesTitle = findViewById(R.id.txtStudentMyClassesTitle);
        txtBackMyClasses = findViewById(R.id.txtBackMyClasses);

        loggedInStudentName = getIntent().getStringExtra("student_name");
        if (loggedInStudentName == null || loggedInStudentName.isEmpty()) {
            Toast.makeText(this, "Error: No student name received!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        txtStudentMyClassesTitle.setText("My Classes for " + loggedInStudentName);

        // Handle back navigation
        txtBackMyClasses.setOnClickListener(v -> {
            Intent intent = new Intent(StudentMyClassesActivity.this, StudentDashboardActivity.class);
            intent.putExtra("student_name", loggedInStudentName);
            startActivity(intent);
            finish();
        });

        ArrayList<String> teachers = dbHelper.getTeachersAssignedToStudent(loggedInStudentName);
        if (teachers.isEmpty()) {
            teachers.add("No classes assigned yet.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teachers);
        listAssignedTeachers.setAdapter(adapter);
    }
}
