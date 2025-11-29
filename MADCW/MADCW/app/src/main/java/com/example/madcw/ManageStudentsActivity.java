package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageStudentsActivity extends AppCompatActivity {

    ListView studentListView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);

        dbHelper = new DatabaseHelper(this);
        studentListView = findViewById(R.id.studentListView);

        Button btnBackToRegister = findViewById(R.id.btnBackToRegister);
        btnBackToRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Back to Register Student", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ManageStudentsActivity.this, RegisterStudentActivity.class));
            finish();
        });

        ArrayList<HashMap<String, String>> studentList = dbHelper.getAllStudentsRaw();
        StudentAdapter adapter = new StudentAdapter(this, studentList);
        studentListView.setAdapter(adapter);
    }
}
