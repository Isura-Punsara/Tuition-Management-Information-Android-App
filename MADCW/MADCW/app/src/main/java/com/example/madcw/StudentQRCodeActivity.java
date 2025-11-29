package com.example.madcw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class StudentQRCodeActivity extends AppCompatActivity {

    private ImageView imgQRCode;
    private TextView txtStudentNameQR;
    private DatabaseHelper dbHelper;
    private TextView txtBackAttendance;

    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_qr_code);

        imgQRCode = findViewById(R.id.imgQRCode);
        txtStudentNameQR = findViewById(R.id.txtStudentNameQR);
        txtBackAttendance = findViewById(R.id.txtBackAttendance);
        dbHelper = new DatabaseHelper(this);

        // Get student name from intent
        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null || studentName.isEmpty()) {
            Toast.makeText(this, "Student name not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtStudentNameQR.setText(studentName);


        // Handle back button
        txtBackAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(StudentQRCodeActivity.this, StudentDashboardActivity.class);
            intent.putExtra("student_name", studentName);
            startActivity(intent);
            finish();
        });

        // Get stored QR code string from database
        String qrCodeString = dbHelper.getStudentQRCode(studentName);

        if (qrCodeString == null || qrCodeString.isEmpty()) {
            Toast.makeText(this, "QR code not generated for this student yet.", Toast.LENGTH_LONG).show();
            return;
        }

        // Decode Base64 to Bitmap
        try {
            byte[] decodedBytes = android.util.Base64.decode(qrCodeString, android.util.Base64.DEFAULT);
            Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imgQRCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to display QR code", Toast.LENGTH_SHORT).show();
        }
    }
}
