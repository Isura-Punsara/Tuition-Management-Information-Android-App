package com.example.madcw;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class UploadCourseMaterialActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;

    EditText inputTitle;
    TextView txtSelectedFile;
    Button btnChooseFile, btnUpload;
    Uri selectedFileUri;
    String selectedFileName = "";
    String selectedFilePath = "";

    DatabaseHelper dbHelper;
    String teacherName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_course_material);

        inputTitle = findViewById(R.id.edtMaterialTitle);
        txtSelectedFile = findViewById(R.id.txtSelectedFile);
        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnUpload = findViewById(R.id.btnUploadMaterial);

        dbHelper = new DatabaseHelper(this);

        teacherName = getIntent().getStringExtra("teacher_name");
        if (teacherName == null) teacherName = "";

        btnChooseFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "Select Course Material File"), PICK_FILE_REQUEST);
        });

        btnUpload.setOnClickListener(v -> {
            String title = inputTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedFilePath.isEmpty()) {
                Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertCourseMaterial(title, selectedFilePath, teacherName);
            if (inserted) {
                Toast.makeText(this, "Course Material Uploaded!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView txtBack = findViewById(R.id.txtBackUpload);
        txtBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedFileUri = data.getData();
            selectedFileName = getFileName(selectedFileUri);
            selectedFilePath = copyFileToInternalStorage(selectedFileUri, selectedFileName);

            if (!selectedFilePath.isEmpty()) {
                txtSelectedFile.setText("Selected: " + selectedFileName);
            } else {
                txtSelectedFile.setText("Failed to copy file");
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = "";
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                result = cursor.getString(nameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            return file.getAbsolutePath(); // Store this path in DB
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
