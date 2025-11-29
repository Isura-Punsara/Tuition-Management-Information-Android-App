package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QRAttendanceActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String loggedInTeacherName = "";

    private final ActivityResultLauncher<ScanOptions> qrScanLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    handleScannedQRCode(result.getContents());
                } else {
                    Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrattendance);

        dbHelper = new DatabaseHelper(this);

        loggedInTeacherName = getIntent().getStringExtra("teacher_name");
        if (loggedInTeacherName == null) loggedInTeacherName = "";

        TextView txtBack = findViewById(R.id.txtBackAttendance);
        txtBack.setOnClickListener(v -> finish());

        TextView txtScanPrompt = findViewById(R.id.txtScanPrompt);
        txtScanPrompt.setOnClickListener(v -> startQRScanner());

        // Start scanning immediately on open:
        startQRScanner();
    }

    private void startQRScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan student QR code for attendance");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureActivity.class);
        qrScanLauncher.launch(options);
    }

    private void handleScannedQRCode(String qrData) {

        if (qrData.startsWith("STUDENT_")) {
            String[] parts = qrData.split("_");
            if (parts.length >= 2) {
                String studentName = parts[1];

                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                boolean inserted = dbHelper.insertAttendance(studentName, loggedInTeacherName, currentDate, currentTime);

                if (inserted) {
                    Toast.makeText(this, "Attendance recorded for " + studentName, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Attendance already recorded for " + studentName + " today.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid QR code format", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Invalid QR code scanned", Toast.LENGTH_LONG).show();
        }

        // After handling one scan, start scanning again automatically
        startQRScanner();
    }
}
