package com.example.a1190283_ibraheem_duhaidi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1190283_ibraheem_duhaidi.Service.DataBaseHelper;

public class result_screen extends AppCompatActivity {
    LinearLayout view_score;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        view_score = findViewById(R.id.result_lay);
        Button go_home = findViewById(R.id.go_home);
        go_home.setOnClickListener(v -> {
            Intent intent = new Intent(result_screen.this, home_page.class);
            result_screen.this.startActivity(intent);
            finish();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(result_screen.this, "EXP4", null, 1);
        Cursor userScoreCursor = dataBaseHelper.getTopFivePlayers();
        view_score.removeAllViews();
        while (userScoreCursor.moveToNext()) {
            TextView textView = new TextView(result_screen.this);
            textView.setText("Name= " + userScoreCursor.getString(0) + "\nScore= " + userScoreCursor.getString(1) + "\n\n");
            view_score.addView(textView);
        }
    }
}