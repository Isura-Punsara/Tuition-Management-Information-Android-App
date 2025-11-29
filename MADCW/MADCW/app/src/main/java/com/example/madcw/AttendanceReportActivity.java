package com.example.madcw;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceReportActivity extends AppCompatActivity {

    private RecyclerView rvAttendanceReport;
    private TextView txtBackAttendanceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report2);

        rvAttendanceReport = findViewById(R.id.rvAttendanceReport);
        txtBackAttendanceReport = findViewById(R.id.txtBackAttendanceReport);

        rvAttendanceReport.setLayoutManager(new LinearLayoutManager(this));

        txtBackAttendanceReport.setOnClickListener(v -> {
            finish(); // Go back to previous screen
        });

        // 🔸 Add logic later for fetching attendance data and setting adapter
    }
}
