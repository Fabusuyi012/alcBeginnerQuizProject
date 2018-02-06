package com.example.ayodejifabusuyi.quizapp;

/**
 * Created by Ayodeji Fabusuyi on 15/01/2018.
 */

public class UniversalParameters {
    private int totalQuestionsNumber = 3;
    private double timeAlloted = 3.0;

    public int getTotalQuestionsNumber() {
        return totalQuestionsNumber;
    }

    public void setTotalQuestionsNumber(int totalQuestionsNumber) {
        this.totalQuestionsNumber = totalQuestionsNumber;
    }

    public double getTimeAlloted() {
        return timeAlloted;
    }

    public void setTimeAlloted(double timeAlloted) {
        this.timeAlloted = timeAlloted;
    }
}
