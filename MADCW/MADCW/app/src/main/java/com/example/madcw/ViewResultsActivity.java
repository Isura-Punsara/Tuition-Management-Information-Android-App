package com.example.madcw;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ViewResultsActivity extends AppCompatActivity {

    private RecyclerView rvResults;
    private TextView txtBackResults;
    private DatabaseHelper dbHelper;
    private ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        rvResults = findViewById(R.id.rvResults);
        txtBackResults = findViewById(R.id.txtBackResults);
        dbHelper = new DatabaseHelper(this);

        txtBackResults.setOnClickListener(v -> finish());

        rvResults.setLayoutManager(new LinearLayoutManager(this));

        List<ResultRecord> resultList = dbHelper.getAllResults();
        adapter = new ResultAdapter(resultList);
        rvResults.setAdapter(adapter);
    }
}
