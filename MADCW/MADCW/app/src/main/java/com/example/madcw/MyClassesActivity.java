package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyClassesActivity extends AppCompatActivity {

    ListView listViewAssignedStudents;
    TextView txtBack;
    DatabaseHelper dbHelper;
    String teacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);

        listViewAssignedStudents = findViewById(R.id.listViewAssignedStudents);
        txtBack = findViewById(R.id.txtBack);
        dbHelper = new DatabaseHelper(this);

        teacherName = getIntent().getStringExtra("teacher_name");

        if (teacherName == null || teacherName.isEmpty()) {
            Toast.makeText(this, "No teacher data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadAssignedStudents();

        txtBack.setOnClickListener(v -> {
           Intent backIntent = new Intent(this,TeacherDashboardActivity.class);
           backIntent.putExtra("teacher_name",teacherName);
           startActivity(backIntent);
           finish();
        });
    }

    private void loadAssignedStudents() {
        ArrayList<String> studentNames = dbHelper.getAssignedStudents(teacherName);

        if (studentNames.isEmpty()) {
            Toast.makeText(this, "No students assigned yet", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listViewAssignedStudents.setAdapter(adapter);
    }
}
