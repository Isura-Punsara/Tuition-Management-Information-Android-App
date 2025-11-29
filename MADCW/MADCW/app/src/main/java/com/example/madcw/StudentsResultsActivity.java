package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentsResultsActivity extends AppCompatActivity {

    private ListView listResults;
    private TextView txtNoResults, txtResultsTitle;
    private DatabaseHelper dbHelper;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_results);

        listResults = findViewById(R.id.listResults);
        txtNoResults = findViewById(R.id.txtNoResults);
        txtResultsTitle = findViewById(R.id.txtResultsTitle);

        dbHelper = new DatabaseHelper(this);

        studentName = getIntent().getStringExtra("student_name");

        if (studentName == null || studentName.isEmpty()) {
            Toast.makeText(this, "Student info missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtResultsTitle.setText("Results for " + studentName);

        TextView txtBackResults = findViewById(R.id.txtBackResults);

        txtBackResults.setOnClickListener(v -> {
            Intent intent = new Intent(StudentsResultsActivity.this, StudentDashboardActivity.class);
            intent.putExtra("student_name", studentName);
            startActivity(intent);
            finish();
        });

        ArrayList<HashMap<String, String>> results = dbHelper.getResultsByStudent(studentName);

        if (results.isEmpty()) {
            txtNoResults.setVisibility(TextView.VISIBLE);
            listResults.setVisibility(ListView.GONE);
        } else {
            txtNoResults.setVisibility(TextView.GONE);
            listResults.setVisibility(ListView.VISIBLE);

            // Create simple adapter for results list
            ResultListAdapter adapter = new ResultListAdapter(this, results);
            listResults.setAdapter(adapter);
        }
    }
}
