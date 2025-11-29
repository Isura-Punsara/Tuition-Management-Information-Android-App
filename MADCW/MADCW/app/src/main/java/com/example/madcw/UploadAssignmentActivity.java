package com.example.madcw;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class UploadAssignmentActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;

    EditText edtAssignmentTitle, edtAssignmentDescription;
    TextView txtFileName, txtBackUpload;
    Button btnChooseFile, btnUploadAssignment;
    Uri selectedFileUri;
    String selectedFileName = "";
    String selectedFilePath = "";

    DatabaseHelper dbHelper;
    String teacherName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_assignment);

        edtAssignmentTitle = findViewById(R.id.edtAssignmentTitle);
        edtAssignmentDescription = findViewById(R.id.edtAssignmentDescription);
        txtFileName = findViewById(R.id.txtFileName);
        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnUploadAssignment = findViewById(R.id.btnUploadAssignment);
        txtBackUpload = findViewById(R.id.txtBackUpload);
        dbHelper = new DatabaseHelper(this);

        teacherName = getIntent().getStringExtra("teacher_name");
        if (teacherName == null) teacherName = "";

        btnChooseFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
        });

        btnUploadAssignment.setOnClickListener(v -> {
            String title = edtAssignmentTitle.getText().toString().trim();
            String desc = edtAssignmentDescription.getText().toString().trim();

            if (title.isEmpty() || selectedFilePath.isEmpty()) {
                Toast.makeText(this, "Please enter title and attach a file", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertAssignment(title, selectedFilePath, teacherName);
            if (inserted) {
                Toast.makeText(this, "Assignment Uploaded", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });

        txtBackUpload.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedFileUri = data.getData();
            selectedFileName = getFileName(selectedFileUri);
            selectedFilePath = copyFileToInternalStorage(selectedFileUri, selectedFileName);

            txtFileName.setText("Selected: " + selectedFileName);
        }
    }

    private String getFileName(Uri uri) {
        String result = "";
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1) {
                    result = cursor.getString(nameIndex);
                }
            }
        }
        return result;
    }

    private String copyFileToInternalStorage(Uri uri, String filename) {
        File file = new File(getFilesDir(), filename);
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(file)) {

            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            return file.getAbsolutePath(); // Store this in DB
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
