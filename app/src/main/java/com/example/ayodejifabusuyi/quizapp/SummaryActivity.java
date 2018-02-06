package com.example.ayodejifabusuyi.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private int totalQuestions, correctAnswers, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_layout);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            totalQuestions = b.getInt("totalQuestions");
            correctAnswers = b.getInt("correctAnswers");
            score = b.getInt("score");
        }
        displaySummary();
    }

    public void displaySummary(){
        int percentageCorrect = (correctAnswers/totalQuestions)*100;
        String summaryString = "You got approximately "+percentageCorrect+"% percent of the questions correctly" +
                "\nYou got about "+percentageCorrect+"% correctly" +
                "\nYour final score is "+score;
        TextView summaryText = findViewById(R.id.summaryText);
        summaryText.setText(summaryString);
    }

    public void restartButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
