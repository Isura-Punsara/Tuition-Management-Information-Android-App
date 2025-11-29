package com.example.madcw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

public class RegisterStudentActivity extends AppCompatActivity {

    TextInputEditText inputStudentName, inputStudentPassword, inputStudentAge;
    MaterialAutoCompleteTextView courseSelector;
    MaterialButton btnRegisterStudent;
    TextView txtBack;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stduent_register);

        inputStudentName = findViewById(R.id.inputStudentName);
        inputStudentPassword = findViewById(R.id.inputStudentPassword);
        courseSelector = findViewById(R.id.courseSelector);
        inputStudentAge = findViewById(R.id.inputStudentAge);
        btnRegisterStudent = findViewById(R.id.btnRegisterStudent);
        txtBack = findViewById(R.id.txtBack);

        dbHelper = new DatabaseHelper(this);

        TextView txtManageStudentsLink = findViewById(R.id.txtManageStudentsLink);
        txtManageStudentsLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterStudentActivity.this,ManageStudentsActivity.class);
            startActivity(intent);
        });

        // Course options
        String[] courses = {"Math", "Science", "English", "ICT"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, courses);
        courseSelector.setAdapter(adapter);

        // Back to Admin Dashboard
        txtBack.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminDashboardActivity.class));
            finish();
        });

        // Register Button Logic
        btnRegisterStudent.setOnClickListener(v -> {
            String name = inputStudentName.getText().toString().trim();
            String password = inputStudentPassword.getText().toString().trim();
            String course = courseSelector.getText().toString().trim();
            String ageStr = inputStudentAge.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty() || course.isEmpty() || ageStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            int age;
            try {
                age = Integer.parseInt(ageStr);
            }catch (NumberFormatException e){
                Toast.makeText(this, "Invalid age entered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate QR content (you can use student name, ID, or a combination)
            String qrContent = "STUDENT_" + name + "_" + System.currentTimeMillis();
            String encodedQR = generateBase64QR(qrContent);

            if (encodedQR != null) {
                boolean inserted = dbHelper.insertStudent(name, password, course, age, encodedQR);
                if (inserted) {
                    Toast.makeText(this, "Student Registered!", Toast.LENGTH_SHORT).show();
                    inputStudentName.setText("");
                    inputStudentPassword.setText("");
                    inputStudentAge.setText("");
                    courseSelector.setText("");
                } else {
                    Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "QR Generation Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Generate Base64 QR from text
    private String generateBase64QR(String content) {
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return Base64.encodeToString(byteArray, Base64.DEFAULT);

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}