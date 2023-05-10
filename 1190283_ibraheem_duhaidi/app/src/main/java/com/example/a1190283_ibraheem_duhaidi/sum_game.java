package com.example.a1190283_ibraheem_duhaidi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class sum_game extends AppCompatActivity {
    private final Random random = new Random();
    private TextView firstNumber;
    private TextView secondNumber;
    private TextView score;
    private CountDownTimer timer;


    private final Button[] buttons = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_game);

        // Define text views
        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        score = findViewById(R.id.score);

        // Initialize the first and second number text views with random values
        int firstNumberValue = random.nextInt(101) + 100;
        int secondNumberValue = random.nextInt(101) + 100;
        firstNumber.setText(String.valueOf(firstNumberValue));
        secondNumber.setText(String.valueOf(secondNumberValue));

        // Define buttons
        buttons[0] = findViewById(R.id.button);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        // Set button values
        setButtonValues();

        // Set click listeners
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    // Get the sum of the first and second numbers
                    int sum = Integer.parseInt(firstNumber.getText().toString()) + Integer.parseInt(secondNumber.getText().toString());

                    // Check if the button value is equal to the sum
                    Button button = (Button) view;
                    int buttonValue = Integer.parseInt(button.getText().toString());
                    System.out.println(buttonValue);
                    if (buttonValue == sum) {
                        // Increment the score
                        int currentScore = Integer.parseInt(score.getText().toString().substring(score.getText().toString().indexOf("=") + 1));
                        score.setText("Score="+ (currentScore + 1));

                        // Show a toast message
                        Toast.makeText(view.getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show an error message
                        Toast.makeText(view.getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                    }

                    // Reinitialize the UI
                    setButtonValues();
                }
            });
        }

        timer = new CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                TextView timerTextView = findViewById(R.id.timer);
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(sum_game.this, words_meaning_game.class);
                intent.putExtra("score", Integer.parseInt(score.getText().toString().substring(score.getText().toString().indexOf("=") + 1)));
                intent.putExtra("playerName", getIntent().getStringExtra("name")); // Replace "John Doe" with the actual name of the player
                startActivity(intent);
                finish();
            }
        }.start();

    }

    private void setButtonValues() {
        // Get two random button indices from the array
        int firstButtonIndex, secondButtonIndex;
        firstNumber.setText(String.valueOf(random.nextInt(101) + 100));
        secondNumber.setText(String.valueOf(random.nextInt(101) + 100));

        do {
            firstButtonIndex = random.nextInt(3);
            secondButtonIndex = random.nextInt(3);
        } while (firstButtonIndex == secondButtonIndex);

        // Set the button texts
        buttons[firstButtonIndex].setText(String.valueOf(random.nextInt(201) + 200));
        buttons[secondButtonIndex].setText(String.valueOf(random.nextInt(201) + 200));
        System.out.println(3 - firstButtonIndex - secondButtonIndex);
        buttons[3 - firstButtonIndex - secondButtonIndex].setText(String.valueOf(Integer.parseInt(firstNumber.getText().toString()) + Integer.parseInt(secondNumber.getText().toString())));
    }
}