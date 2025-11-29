package com.example.madcw;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TeacherAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> teacherList;
    LayoutInflater inflater;
    DatabaseHelper dbHelper;

    public TeacherAdapter(Context context, ArrayList<HashMap<String, String>> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
        this.inflater = LayoutInflater.from(context);
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return teacherList.size();
    }

    @Override
    public Object getItem(int i) {
        return teacherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.teacher_list_item, null);

        TextView txtInfo = view.findViewById(R.id.txtTeacherInfo);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        HashMap<String, String> teacher = teacherList.get(i);
        String name = teacher.get("name");
        String subject = teacher.get("subject");

        txtInfo.setText(name + " | " + subject);

        // Delete
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Teacher")
                    .setMessage("Delete " + name + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteTeacher(name);
                        teacherList.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Deleted " + name, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Edit
        btnEdit.setOnClickListener(v -> {
            View dialogView = inflater.inflate(R.layout.dialog_edit_teacher, null);

            EditText edtName = dialogView.findViewById(R.id.editTeacherName);
            EditText edtSubject = dialogView.findViewById(R.id.editTeacherSubject);

            edtName.setText(name);
            edtSubject.setText(subject);

            new AlertDialog.Builder(context)
                    .setTitle("Edit Teacher")
                    .setView(dialogView)
                    .setPositiveButton("Save", (dialog1, which1) -> {
                        String newName = edtName.getText().toString().trim();
                        String newSubject = edtSubject.getText().toString().trim();

                        if (newName.isEmpty() || newSubject.isEmpty()) {
                            Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean updated = dbHelper.updateTeacher(name, newName, newSubject);
                        if (updated) {
                            teacher.put("name", newName);
                            teacher.put("subject", newSubject);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Teacher updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        return view;
    }
}
