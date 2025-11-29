package com.example.madcw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> results;
    private LayoutInflater inflater;

    public ResultListAdapter(Context context, ArrayList<HashMap<String, String>> results) {
        this.context = context;
        this.results = results;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txtSubject, txtTeacher, txtMarks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.result_list_item, parent, false);
            holder = new ViewHolder();
            holder.txtSubject = convertView.findViewById(R.id.txtSubject);
            holder.txtTeacher = convertView.findViewById(R.id.txtTeacher);
            holder.txtMarks = convertView.findViewById(R.id.txtMarks);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> result = results.get(position);
        holder.txtSubject.setText("Subject: " + result.get("subject"));
        holder.txtTeacher.setText("Teacher: " + result.get("teacher"));
        holder.txtMarks.setText("Marks: " + result.get("marks"));

        return convertView;
    }
}
