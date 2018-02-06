package com.example.ayodejifabusuyi.quizapp;

import java.util.HashMap;

/**
 * Created by Ayodeji Fabusuyi on 15/01/2018.
 */

public class Questions extends UniversalParameters {
    final int mTotalQuestionsNumber = getTotalQuestionsNumber();

    public String getQuestion(int index) {
        Questions questionSet = new Questions();
        HashMap<String, String[]>[] questionsArray = questionSet.setQuestions();
        HashMap<String, String[]> questions = questionsArray[index];
        String questionKey = questions.keySet().toString();
        String question = questionKey.substring(1, questionKey.length() - 1);
        String optionA = questions.get(question)[0];
        String optionB = questions.get(question)[1];
        String optionC = questions.get(question)[2];
        String cAns = questions.get(question)[3];
        return question + "<fab>" + optionA + "<fab>" + optionB + "<fab>" + optionC + "<fab>" + cAns;
    }

    public HashMap<String, String[]>[] setQuestions() {
        HashMap<String, String[]> hashMap1 = new HashMap<>(),
                hashMap2 = new HashMap<>(),
                hashMap3 = new HashMap<>();
        String[] options1 = {"A", "B", "C", "B"};
        hashMap1.put("Question1", options1);
        String[] options2 = {"E", "F", "G", "G"};
        hashMap2.put("Question2", options2);
        String[] options3 = {"I", "J", "K", "I"};
        hashMap3.put("Question3", options3);
        HashMap<String, String[]>[] hashMapArray = new HashMap[mTotalQuestionsNumber];
        hashMapArray[0] = hashMap1;
        hashMapArray[1] = hashMap2;
        hashMapArray[2] = hashMap3;
        return hashMapArray;
    }
}

