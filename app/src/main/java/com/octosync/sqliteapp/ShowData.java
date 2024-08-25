package com.octosync.sqliteapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowData extends AppCompatActivity {

    EditText edSearch;
    Button btnSearch;
    TextView tvResult;
    DBHelper dbHelper;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edSearch = findViewById(R.id.edSearch);
        btnSearch = findViewById(R.id.btnSearch);
        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DBHelper(ShowData.this);

        displayData(dbHelper.getData()); // Show all data initially

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edSearch.getText().toString().trim();

                if (!search.isEmpty()) {
                    // If there is a search term, filter the data
                    Cursor cursor1 = dbHelper.getSearchData(search);
                    displayData(cursor1);
                } else {
                    // If no search term, show all data
                    displayData(dbHelper.getData());
                }
            }
        });
    }

    private void displayData(Cursor cursor) {
        tvResult.setText(""); // Clear the previous results

        if (cursor != null && cursor.getCount() > 0) {
            tvResult.append("Total data: " + cursor.getCount());

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String mobile = cursor.getString(2);
                tvResult.append("\nID: " + id + " Name: " + name + " Mobile: " + mobile);
            }
        } else {
            tvResult.setText("No data found.");
        }

        // Close the cursor after use
        if (cursor != null) {
            cursor.close();
        }
    }
}
