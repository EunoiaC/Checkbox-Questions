package com.aadyad.checkboxquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> questions = new ArrayList<>(Arrays.asList("Are you sad?", "Have you had cough or fever in the past few days.", "Are you cheese?"));

        QuestionListSettings questionListSettings = new QuestionListSettings.Builder()
                .setCheckboxLocation(YesOrNoQuestion.LEFT)
                .setCheckBoxOrientation(LinearLayout.HORIZONTAL)
                .setNumberEnabled(true)
                .setSpacing(30)
                .allowUnansweredQuestions(false)
                .create();

        QuestionList questionList = new QuestionList(questions, questionListSettings);
    }
}