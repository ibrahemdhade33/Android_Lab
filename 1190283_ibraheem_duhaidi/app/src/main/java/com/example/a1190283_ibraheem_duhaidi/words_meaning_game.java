package com.example.a1190283_ibraheem_duhaidi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1190283_ibraheem_duhaidi.Entity.UserScore;
import com.example.a1190283_ibraheem_duhaidi.Service.DataBaseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class words_meaning_game extends AppCompatActivity {
    private final Random random = new Random();
    private CountDownTimer timer;
    private final Button[] buttons = new Button[3];
    String correctAnswer;
    HashMap<String, String> dictionary ;
    TextView score ;
    TextView word ;
    List<String> arabicWords = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_meaning_game);
        try {
            dictionary = readArabicEnglishFile(getApplicationContext(), "words.txt");
            arabicWords.addAll(dictionary.keySet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        score = findViewById(R.id.score);
        word = findViewById(R.id.word_arabic);
        buttons[0] = findViewById(R.id.button4);
        buttons[1] = findViewById(R.id.button5);
        buttons[2] = findViewById(R.id.button6);
        setButtonValues();
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    String buttonValue = button.getText().toString();
                    if (buttonValue.equals(dictionary.get(correctAnswer))) {
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
                int allGamesScore = getIntent().getIntExtra("score", 0);
                int currentScore = Integer.parseInt(score.getText().toString().substring(score.getText().toString().indexOf("=") + 1)) ;
                allGamesScore+=currentScore;
                DataBaseHelper dataBaseHelper = new DataBaseHelper(words_meaning_game.this, "EXP4", null, 1);
                UserScore userScore = new UserScore();
                userScore.setScore(String.valueOf(allGamesScore));
                userScore.setName(getIntent().getStringExtra("playerName"));
                dataBaseHelper.insertScore(userScore);
                dataBaseHelper.getTopFivePlayers();
                Intent intent = new Intent(words_meaning_game.this, result_screen.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }


    public static HashMap<String, String> readArabicEnglishFile(Context context, String filename) throws IOException {
        HashMap<String, String> arabicEnglishMap = new HashMap<>();
        InputStream inputStream = context.getAssets().open(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split("\t");
            arabicEnglishMap.put(tokens[1], tokens[0]);
        }
        br.close();
        inputStream.close();
        return arabicEnglishMap;
    }




    private void setButtonValues() {
        // Get two random button indices from the array
        int firstButtonIndex, secondButtonIndex;
        String firstWord,secondWord;
        do {
            firstButtonIndex = random.nextInt(3);
            secondButtonIndex = random.nextInt(3);
        } while (firstButtonIndex == secondButtonIndex);
        do {
             firstWord = arabicWords.get(random.nextInt(arabicWords.size()));
             secondWord = arabicWords.get(random.nextInt(arabicWords.size()));
             correctAnswer = arabicWords.get(random.nextInt(arabicWords.size()));

        } while (Objects.equals(firstWord, secondWord) || Objects.equals(firstWord, correctAnswer) || Objects.equals(secondWord, correctAnswer));

        // Set the button texts
        word.setText(correctAnswer);
        buttons[firstButtonIndex].setText(dictionary.get(firstWord));
        buttons[secondButtonIndex].setText(dictionary.get(secondWord));
        buttons[3 - firstButtonIndex - secondButtonIndex].setText(dictionary.get(correctAnswer));
    }




    public String readTextFileFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}