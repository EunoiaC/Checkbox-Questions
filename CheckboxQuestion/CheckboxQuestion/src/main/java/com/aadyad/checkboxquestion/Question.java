package com.aadyad.checkboxquestion;

import android.util.Log;

import java.util.ArrayList;

public class Question {

    //TODO: Create MultipleAnswerQuestions

    public static final String MULTIPLE_CHOICE_QUESTION = "MultipleChoiceQuestion";
    public static final String YES_OR_NO_QUESTION = "YesOrNoQuestion";
    public static final String MULTIPLE_ANSWER_QUESTION = "MultipleAnswerQuestion";
    public static final int LEFT = 0;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int FULL_VERTICAL = 3;
    public static final int NO_ANSWER = 0;
    public static final int SPLIT_VERTICAL = 1;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int AUTO = 2;

    String question;
    String type;
    int correctAnswer;
    ArrayList<Integer> multipleCorrectAnswer;
    String[] options = new String[]{"Yes", "No"};

    public Question(String question, int correctAnswer, String type, String... options) {
        this.question = question;
        this.type = type;
        if (options != null){
            this.options = options;
        }
        this.correctAnswer = correctAnswer;
    }

    public Question(String question, ArrayList<Integer> correctAnswer, String type, String... options) {
        this.question = question;
        this.type = type;
        if (options != null){
            this.options = options;
        }
        this.multipleCorrectAnswer = correctAnswer;
    }

    public Question(String question, String correctAnswer, String type, String... options) {
        this.question = question;
        this.type = type;
        if (options != null){
            this.options = options;
        }
        this.correctAnswer = getIndexOfString(correctAnswer) + 1;
    }

    private int getIndexOfString(String s) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(s)) {
                Log.d("Question", "getIndexOfString: " + i);
                return i;
            }
        }
        Log.d("TAG", "answer not found: " + s);
        return -1;
    }

}
