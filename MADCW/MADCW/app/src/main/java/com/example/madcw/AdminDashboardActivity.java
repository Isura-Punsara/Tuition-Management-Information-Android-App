package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class AdminDashboardActivity extends AppCompatActivity {

    MaterialCardView cardRegisterStudent, cardRegisterTeacher, cardAssignStudents,
            cardAttendanceReport, cardResultsReport;
    TextView txtLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardRegisterStudent = findViewById(R.id.cardRegisterStudent);
        cardRegisterTeacher = findViewById(R.id.cardRegisterTeacher);
        cardAssignStudents = findViewById(R.id.cardAssignStudents);
        cardAttendanceReport = findViewById(R.id.cardAttendanceReport);
        cardResultsReport = findViewById(R.id.cardResultsReport);
        txtLogout = findViewById(R.id.txtLogout);

        // ✅ Register Student
        cardRegisterStudent.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, RegisterStudentActivity.class);
            startActivity(intent);
        });

        // You can keep the other cards as toast for now
        cardRegisterTeacher.setOnClickListener(v ->{
            Intent intent = new Intent(AdminDashboardActivity.this, RegisterTeacherActivity.class);
            startActivity(intent);
        });

        cardAssignStudents.setOnClickListener(v ->{
            Intent intent = new Intent(AdminDashboardActivity.this, AssignStudentsActivity.class);
            startActivity(intent);
                });


        cardAttendanceReport.setOnClickListener(v ->{
            Intent intent = new Intent(AdminDashboardActivity.this, AttendanceReportActivity.class);
            startActivity(intent);
        });

        cardResultsReport.setOnClickListener(v ->{
            Intent intent = new Intent(AdminDashboardActivity.this, ViewResultsActivity.class);
            startActivity(intent);
        });

        txtLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}