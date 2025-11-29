package com.example.madcw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<ResultRecord> resultList;

    public ResultAdapter(List<ResultRecord> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        ResultRecord record = resultList.get(position);
        holder.txtStudent.setText("Student: " + record.getStudentName());
        holder.txtSubject.setText("Subject: " + record.getSubject());
        holder.txtTeacher.setText("Teacher: " + record.getTeacherName());
        holder.txtMarks.setText("Marks: " + record.getMarks());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView txtStudent, txtSubject, txtTeacher, txtMarks;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStudent = itemView.findViewById(R.id.txtStudent);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            txtMarks = itemView.findViewById(R.id.txtMarks);
        }
    }
}
