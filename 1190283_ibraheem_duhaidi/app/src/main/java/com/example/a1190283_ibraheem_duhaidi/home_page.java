package com.example.a1190283_ibraheem_duhaidi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1190283_ibraheem_duhaidi.Service.DataBaseHelper;

public class home_page extends AppCompatActivity {
    LinearLayout view_score;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        view_score = findViewById(R.id.view_score);
        Button start = findViewById(R.id.start_the_game);
        EditText playerName = findViewById(R.id.nameText);
        start.setOnClickListener(v -> {
            String name = playerName.getText().toString();
            if (name.isEmpty()) {
                playerName.setError("Please enter your name");
            } else {
                Intent intent = new Intent(home_page.this, sum_game.class);
                intent.putExtra("name", name);
                home_page.this.startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(home_page.this, "EXP4", null, 1);
        Cursor userScoreCursor = dataBaseHelper.getTopFivePlayers();
        view_score.removeAllViews();
        while (userScoreCursor.moveToNext()) {
            TextView textView = new TextView(home_page.this);
            textView.setText("Name= " + userScoreCursor.getString(0) + "\nScore= " + userScoreCursor.getString(1) + "\n\n");

            view_score.addView(textView);
        }
    }
}