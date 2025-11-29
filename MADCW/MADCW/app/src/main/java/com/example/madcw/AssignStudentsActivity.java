package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class AssignStudentsActivity extends AppCompatActivity {

    Spinner spinnerStudents, spinnerTeachers;
    Button btnAssign;
    TextView txtBack;
    DatabaseHelper dbHelper;
    ArrayList<String> studentList, teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_students);

        spinnerStudents = findViewById(R.id.spinnerStudents);
        spinnerTeachers = findViewById(R.id.spinnerTeachers);
        btnAssign = findViewById(R.id.btnAssign);
        txtBack = findViewById(R.id.txtBack);
        dbHelper = new DatabaseHelper(this);

        loadDataIntoSpinners();

        btnAssign.setOnClickListener(v -> {
            String selectedStudent = spinnerStudents.getSelectedItem().toString().split(" \\| ")[0];
            String selectedTeacher = spinnerTeachers.getSelectedItem().toString().split(" \\| ")[0];

            if (selectedStudent.isEmpty() || selectedTeacher.isEmpty()) {
                Toast.makeText(this, "Please select both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean assigned = dbHelper.assignStudentToTeacher(selectedStudent, selectedTeacher);
            if (assigned) {
                Toast.makeText(this, "Assigned Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Already Assigned!", Toast.LENGTH_SHORT).show();
            }
        });
        txtBack.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminDashboardActivity.class));
            finish();
        });
    }

    private void loadDataIntoSpinners() {
        studentList = new ArrayList<>();
        teacherList = new ArrayList<>();

        for (HashMap<String, String> student : dbHelper.getAllStudentsRaw()) {
            String displayText = student.get("name") + " | " + student.get("course");
            studentList.add(displayText);
        }

        for (HashMap<String, String> teacher : dbHelper.getAllTeachersRaw()) {
            String displayText = teacher.get("name") + " | " + teacher.get("subject");
            teacherList.add(displayText);
        }

        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentList);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(studentAdapter);

        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherList);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeachers.setAdapter(teacherAdapter);
    }

}
