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

public class TeacherDashboardActivity extends AppCompatActivity {
    MaterialCardView cardmyclass, cardqr, carduploadassignments,
            cardsubmission, cardmaterials, cardresults;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_dashboard);

        dbHelper = new DatabaseHelper(this);

        String loggedInTeacherName = getIntent().getStringExtra("teacher_name");

        cardmyclass = findViewById(R.id.cardMyClasses);
        cardqr = findViewById(R.id.cardQRScan);
        carduploadassignments = findViewById(R.id.cardUploadAssignments);
        cardsubmission = findViewById(R.id.cardViewSubmissions);
        cardmaterials = findViewById(R.id.cardCourseMaterials);
        cardresults = findViewById(R.id.cardEnterResults);
        TextView txtlogout = findViewById(R.id.txtLogout);

        cardmyclass.setOnClickListener(v ->{
            Intent intent = new Intent(TeacherDashboardActivity.this, MyClassesActivity.class);
            intent.putExtra("teacher_name", loggedInTeacherName);
            startActivity(intent);

        });

        cardqr.setOnClickListener(v ->{
            Intent intent = new Intent(TeacherDashboardActivity.this, QRAttendanceActivity.class);
            intent.putExtra("teacher_name", loggedInTeacherName);
            startActivity(intent);
        });

        carduploadassignments.setOnClickListener(v ->{
            Intent intent = new Intent(TeacherDashboardActivity.this, UploadAssignmentActivity.class);
            intent.putExtra("teacher_name", loggedInTeacherName);
            startActivity(intent);
        });

        cardsubmission.setOnClickListener(v ->
                Toast.makeText(this, "View Submissions clicked", Toast.LENGTH_SHORT).show());

        cardmaterials.setOnClickListener(v ->{
            Intent intent = new Intent(TeacherDashboardActivity.this, UploadCourseMaterialActivity.class);
            intent.putExtra("teacher_name", loggedInTeacherName);
            startActivity(intent);
        });

        cardresults.setOnClickListener(v ->{
            Intent intent = new Intent(TeacherDashboardActivity.this, EnterResultsActivity.class);
            intent.putExtra("teacher_name", loggedInTeacherName);
            String subject = dbHelper.getSubjectByTeacher(loggedInTeacherName); // You need to implement this method
            intent.putExtra("teacher_subject", subject);

            startActivity(intent);
        });

        txtlogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }
}