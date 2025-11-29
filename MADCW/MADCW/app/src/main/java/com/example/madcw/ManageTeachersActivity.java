package com.example.madcw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageTeachersActivity extends AppCompatActivity {

    ListView teacherListView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teachers);

        dbHelper = new DatabaseHelper(this);
        teacherListView = findViewById(R.id.teacherListView);

        Button btnBackToRegister = findViewById(R.id.btnBackToRegister);
        btnBackToRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Back to Register Student", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ManageTeachersActivity.this, RegisterTeacherActivity.class));
            finish();
        });
        ArrayList<HashMap<String, String>> teacherList = dbHelper.getAllTeachersRaw();
        TeacherAdapter adapter = new TeacherAdapter(this, teacherList);
        teacherListView.setAdapter(adapter);
    }
}
