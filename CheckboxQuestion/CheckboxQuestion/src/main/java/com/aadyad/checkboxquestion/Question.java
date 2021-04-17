package com.aadyad.checkboxquestion;

import android.util.Log;

public class Question {

    //TODO: Add type to question and create a customview which allows input instead of checkboxes.

    public static final int LEFT = 0;
    public static final int HORIZONTAL = 0;
    public static final int SPLIT_VERTICAL = 1;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int AUTO = 2;

    String question;
    int correctAnswer;
    String[] options;

    public Question(String question, int correctAnswer, String... options) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public Question(String question, String correctAnswer, String... options) {
        this.question = question;
        this.options = options;
        this.correctAnswer = getIndexOfString(correctAnswer) + 1;
    }

    private int getIndexOfString(String s) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

}
