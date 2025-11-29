package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class StudentDashboardActivity extends AppCompatActivity {
    MaterialCardView cardmycourses, cardattendance, cardassignments, cardResults,
                    cardMaterials, cardnotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dashboard);

        String loggedInStudentName = getIntent().getStringExtra("student_name");

        cardmycourses = findViewById(R.id.cardMyCourses);
        cardattendance = findViewById(R.id.cardAttendance);
        cardassignments = findViewById(R.id.cardAssignments);
        cardnotifications = findViewById(R.id.cardNotifications);
        cardMaterials = findViewById(R.id.cardMaterials);
        cardResults = findViewById(R.id.cardResults);
        TextView txtlogout = findViewById(R.id.txtLogout);

        cardmycourses.setOnClickListener(v ->{
            Intent intent = new Intent(StudentDashboardActivity.this, StudentMyClassesActivity.class);
            intent.putExtra("student_name", loggedInStudentName);
            startActivity(intent);
        });

        cardattendance.setOnClickListener(v ->{
            Intent intent = new Intent(StudentDashboardActivity.this, StudentQRCodeActivity.class);
            intent.putExtra("student_name", loggedInStudentName);
            startActivity(intent);
        });

        cardassignments.setOnClickListener(v ->
                Toast.makeText(this, "Upload Assignments clicked", Toast.LENGTH_SHORT).show());

        cardnotifications.setOnClickListener(v ->
                Toast.makeText(this, "View Submissions clicked", Toast.LENGTH_SHORT).show());

        cardMaterials.setOnClickListener(v ->{
            Intent intent = new Intent(StudentDashboardActivity.this, StudentCourseMaterialsActivity.class);
            intent.putExtra("student_name", loggedInStudentName);
            startActivity(intent);
        });

        cardResults.setOnClickListener(v ->{
            Intent intent = new Intent(StudentDashboardActivity.this, StudentsResultsActivity.class);
            intent.putExtra("student_name", loggedInStudentName);
            startActivity(intent);
        });

        txtlogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }
}