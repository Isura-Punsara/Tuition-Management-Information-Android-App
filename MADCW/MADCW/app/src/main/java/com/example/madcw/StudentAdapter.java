package com.example.madcw;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> studentList;
    LayoutInflater inflater;
    DatabaseHelper dbHelper;

    public StudentAdapter(Context context, ArrayList<HashMap<String, String>> studentList) {
        this.context = context;
        this.studentList = studentList;
        this.inflater = LayoutInflater.from(context);
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.student_list_item, null);

        TextView txtInfo = view.findViewById(R.id.txtStudentInfo);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        HashMap<String, String> student = studentList.get(i);
        String name = student.get("name");
        String course = student.get("course");
        String age = student.get("age");

        txtInfo.setText(name + " | " + course + " | Age: " + age);

        // DELETE button
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Student")
                    .setMessage("Delete " + name + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteStudent(name);
                        studentList.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Deleted " + name, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // EDIT button
        btnEdit.setOnClickListener(v -> {

            //inflate the popup
            View dialogview = inflater.inflate(R.layout.dialog_edit_student,null);
            EditText edtName = dialogview.findViewById(R.id.editStudentName);
            EditText edtCourse = dialogview.findViewById(R.id.editStudentCourse);
            EditText edtAge = dialogview.findViewById(R.id.editStudentAge);

                edtName.setText(name);
                edtCourse.setText(course);
                edtAge.setText(age);

                new AlertDialog.Builder(context).setTitle("Edit Student").setView(dialogview).setPositiveButton("Save" , ((dialog, which) -> {
                    String newName = edtName.getText().toString().trim();
                    String newCourse = edtCourse.getText().toString().trim();
                    String ageStr = edtAge.getText().toString().trim();

                        if (newName.isEmpty() || newCourse.isEmpty() || ageStr.isEmpty()){
                            Toast.makeText(context,"All Fields Required", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int newAge;
                        try{
                            newAge = Integer.parseInt(ageStr);
                        }catch (NumberFormatException e){
                            Toast.makeText(context, "Invalid Age", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        boolean updated = dbHelper.updateStudent(name,newName,newCourse,newAge);
                        if (updated){
                            student.put("name", newName);
                            student.put("course", newCourse);
                            student.put("age", String.valueOf(newAge));

                            Toast.makeText(context,"Student Updated",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                }))
                        .setNegativeButton("Cancel",null).show();

        });

        return view;
    }
}
