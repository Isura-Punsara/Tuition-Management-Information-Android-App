package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentCourseMaterialsActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listMaterials;
    TextView txtBackMaterials, txtMaterialsTitle;
    String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_materials);

        dbHelper = new DatabaseHelper(this);
        listMaterials = findViewById(R.id.listCourseMaterials);
        txtBackMaterials = findViewById(R.id.txtBackMaterials);
        txtMaterialsTitle = findViewById(R.id.txtMaterialsTitle);

        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null) {
            Toast.makeText(this, "Student name missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtMaterialsTitle.setText("Course Materials for " + studentName);

        // Handle back
        txtBackMaterials.setOnClickListener(v -> {
            Intent intent = new Intent(StudentCourseMaterialsActivity.this, StudentDashboardActivity.class);
            intent.putExtra("student_name", studentName);
            startActivity(intent);
            finish();
        });

        // Load all materials (from all teachers)
        ArrayList<String> materialsList = new ArrayList<>();
        ArrayList<DatabaseHelper.CourseMaterial> materials = dbHelper.getAllCourseMaterials();

        if (materials.isEmpty()) {
            materialsList.add("No course materials uploaded.");
        } else {
            for (DatabaseHelper.CourseMaterial m : materials) {
                materialsList.add("Title: " + m.title + "\nFile: " + m.filePath);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, materialsList);
        listMaterials.setAdapter(adapter);
    }
}
