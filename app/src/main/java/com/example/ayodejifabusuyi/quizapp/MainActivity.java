package com.example.ayodejifabusuyi.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Questions questions = new Questions();
    int mTotalQuestionsNumber = questions.mTotalQuestionsNumber;
    int mTestScore = 0;
    String[] mCorrectAnswersArray = new String[3];
    String[] mUserAnswersArray = new String[3];

    /**
     * Method invoked upon app start, contains initialization of processes,
     * methods and UI threads within the app.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_layout);
        startUp();
        groupCorrectAnswers();
        timeThreader();
    }

    void setProgressOfBar(int startTime, int currentTime) {
        ProgressBar testProgressBar = findViewById(R.id.testProgressBar);
        TextView testTimer = findViewById(R.id.testTimer);
        testProgressBar.setMax(startTime);
        int secondaryProgress = startTime;
        int progress = currentTime;
        testProgressBar.setSecondaryProgress(secondaryProgress);
        testProgressBar.setProgress(currentTime);
        testTimer.setText(String.valueOf(progress));
    }

    private void timeThreader() {
        int startTime = 30;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            for (int i = startTime; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int currentTime = i;
                Runnable uiRun = new Runnable() {
                    @Override
                    public void run() {
                        setProgressOfBar(30, currentTime);
                    }
                };
                runOnUiThread(uiRun);
            }
        };
        executorService.submit(runnable);
        executorService.shutdown();
        if (executorService.isShutdown()) {
            Toast.makeText(this, "Your time has been exhausted", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method invoked in onCreate method to initialize the first set of activities in the app
     */
    private void startUp() {
        Button previousButton = findViewById(R.id.previousButton);
        previousButton.setEnabled(false);
        setQuestion(0);
        TextView totalQuestionCounter = findViewById(R.id.totalQuestionCounter);
        totalQuestionCounter.setText(Integer.toString(mTotalQuestionsNumber));
    }

    private void setQuestion(int index) {
        TextView question = findViewById(R.id.question),
                currentQuestionCounter = findViewById(R.id.currentQuestionCounter);
        RadioButton option1 = findViewById(R.id.option1),
                option2 = findViewById(R.id.option2),
                option3 = findViewById(R.id.option3);
        Questions questions = new Questions();
        currentQuestionCounter.setText(Integer.toString(index + 1));
        String[] valuesToSet = questions.getQuestion(index).split("<fab>");
        question.setText(valuesToSet[0]);
        option1.setText(valuesToSet[1]);
        option2.setText(valuesToSet[2]);
        option3.setText(valuesToSet[3]);
//        setProgressOfBar(index);
    }

    /**
     * To group the correct answers fetched from a specified source into a unified single
     * dimensional array
     */
    private void groupCorrectAnswers() {
        Questions questions = new Questions();
        for (int i = 0; i < mTotalQuestionsNumber; i++) {
            mCorrectAnswersArray[i] = questions.getQuestion(i).split("<fab>")[4];
        }
    }

    /**
     * Handles the submit button click action
     *
     * @param view
     */
    public void submitButtonClicked(View view) {
        submitAction();
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("totalQuestions", mTotalQuestionsNumber);
        intent.putExtra("correctAnswers", mTestScore);
        intent.putExtra("score", mTestScore);
        startActivity(intent);

    }

    public void submitAction() {
        for (int i = 0; i < mTotalQuestionsNumber; i++) {
            if (mCorrectAnswersArray[i].equals(mUserAnswersArray[i])) {
                mTestScore += 1;
            }
            System.out.println("User chose " + mUserAnswersArray[i]);
        }

        Runnable uiRun = () -> Toast.makeText(MainActivity.this, "You scored " + mTestScore, Toast.LENGTH_LONG).show();
        runOnUiThread(uiRun);
        System.out.println("You scored " + mTestScore);
    }

    /**
     * To handle action performed when the next button is clicked,
     * it invokes a setQuestion method which initializes and display
     * questions for a particular index(an index which is derived from
     * addition of +1 to the current index)
     *
     * @param view The view whose action is being handled
     */
    public void nextButtonClicked(View view) {
        Button nextButton = findViewById(R.id.nextButton),
                previousButton = findViewById(R.id.previousButton),
                submitButton = findViewById(R.id.submitButton);
        TextView currentQuestionCounter = findViewById(R.id.currentQuestionCounter);
        int currentQuestionIndex = Integer.decode(currentQuestionCounter.getText().toString()) - 1;
        int nextQuestionIndex = currentQuestionIndex + 1;
        setQuestion(nextQuestionIndex);
        garbageCode(nextQuestionIndex);

        if (nextQuestionIndex == mTotalQuestionsNumber - 1) {
            nextButton.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.VISIBLE);
        }
        if (previousButton.isEnabled() == false) {
            previousButton.setEnabled(true);
        }

        for (int i = 0; i < mTotalQuestionsNumber; i++) {
            System.out.println("Correct " + mCorrectAnswersArray[i]);
        }

    }

    /**
     * To handle action performed when the previous button is clicked,
     * it invokes a setQuestion method which initializes and display
     * questions for a particular index(an index which is derived from
     * addition of -1 to the current index)
     *
     * @param view The view whose action is being handled
     */
    public void previousButtonClicked(View view) {
        TextView currentQuestionCounter = findViewById(R.id.currentQuestionCounter);
        Button previousButton = findViewById(R.id.previousButton),
                nextButton = findViewById(R.id.nextButton),
                submitButton = findViewById(R.id.submitButton);
        int currentQuestionIndex = Integer.decode(currentQuestionCounter.getText().toString()) - 1;
        int previousQuestionIndex = currentQuestionIndex - 1;
        setQuestion(previousQuestionIndex);
        garbageCode(previousQuestionIndex);

        if (previousQuestionIndex == 0) {
            previousButton.setEnabled(false);
        }
        if (submitButton.getVisibility() == View.VISIBLE) {
            submitButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void garbageCode(int index) {
        RadioButton optionA = findViewById(R.id.option1),
                optionB = findViewById(R.id.option2),
                optionC = findViewById(R.id.option3);
        RadioGroup rg = findViewById(R.id.optionsRadioGroup);
        String optionAtext = optionA.getText().toString(),
                optionBtext = optionB.getText().toString(),
                optionCtext = optionC.getText().toString();
        String selectedAnswer = mUserAnswersArray[index];
        if (selectedAnswer == null) {
            rg.clearCheck();
        } else if (selectedAnswer.equals(optionAtext)) {
            rg.check(optionA.getId());
        } else if (selectedAnswer.equals(optionBtext)) {
            rg.check(optionB.getId());
        } else if (selectedAnswer.equals(optionCtext)) {
            rg.check(optionC.getId());
        } else {
            //do nothing
        }
    }

    private int getCorrectArrayIndex() {
        TextView currentQuestionView = findViewById(R.id.currentQuestionCounter);
        int currentQuestionIndex = Integer.parseInt(currentQuestionView.getText().toString());
        int userAnswerArrayIndex = currentQuestionIndex - 1;
        return userAnswerArrayIndex;
    }

    public void aClicked(View view) {
        RadioButton option1 = findViewById(R.id.option1);
        String optionText = option1.getText().toString();
        int indexForAnswerStorage = getCorrectArrayIndex();
        mUserAnswersArray[indexForAnswerStorage] = optionText;
    }

    public void bClicked(View view) {
        RadioButton option2 = findViewById(R.id.option2);
        String optionText = option2.getText().toString();
        int indexForAnswerStorage = getCorrectArrayIndex();
        mUserAnswersArray[indexForAnswerStorage] = optionText;
    }

    public void cClicked(View view) {
        RadioButton option3 = findViewById(R.id.option3);
        String optionText = option3.getText().toString();
        int indexForAnswerStorage = getCorrectArrayIndex();
        mUserAnswersArray[indexForAnswerStorage] = optionText;
    }


}
