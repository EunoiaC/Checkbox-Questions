package com.aadyad.checkboxquestion;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionList {

    String[] questions;
    ArrayList<Question> choiceQuestions;
    LinearLayout linearLayout;
    QuestionListSettings settings;
    Context context;
    ArrayList<YesOrNoQuestion> yesOrNoQuestions;

    public QuestionList(ArrayList<Question> questions, QuestionListSettings settings, Context context){
        choiceQuestions = questions;
        this.settings = settings;
        this.context = context;
        linearLayout = new LinearLayout(context);
    }

    public QuestionList(String[] questions, QuestionListSettings settings, Context context){
        this.questions = questions;
        this.settings = settings;
        this.context = context;
        linearLayout = new LinearLayout(context);
    }

    public void createQuestionViews(){
        int i = 0;
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        if (questions == null){
            for (Question q : choiceQuestions){
                if (q.type.equals(Question.MULTIPLE_CHOICE_QUESTION)) {
                    Log.d("TAG", "createQuestionViews: " + Arrays.toString(q.options));
                    MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(context);
                    multipleChoiceQuestion.init(q.question, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), settings.getCheckBoxOrientation(), settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), settings.getQuestionLayoutHeight(), settings.getOptionLayoutHeight(), q.options);
                    linearLayout.addView(multipleChoiceQuestion);
                }else if (q.type.equals(Question.YES_OR_NO_QUESTION)){
                    YesOrNoQuestion yesOrNoQuestion = new YesOrNoQuestion(context);
                    yesOrNoQuestion.init(q.question, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), settings.getCheckBoxOrientation(), settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), settings.getQuestionLayoutHeight(), settings.getOptionLayoutHeight());
                    linearLayout.addView(yesOrNoQuestion);
                } else if (q.type.equals(Question.MULTIPLE_ANSWER_QUESTION)){
                    //Todo: Add code for multipleanswerquestion
                } else{
                    throw new RuntimeException("Type " + q.type + " does not exist.");
                }
                i++;
            }
        } else {
            for (String q : questions) {
                i++;
                YesOrNoQuestion yesOrNoQuestion = new YesOrNoQuestion(context);
                yesOrNoQuestion.init(q, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), settings.getCheckBoxOrientation(), settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), settings.getQuestionLayoutHeight(), settings.getOptionLayoutHeight());
                linearLayout.addView(yesOrNoQuestion);
            }
        }

    }

    public float getPercentageOfCorrectAnswers(){
        int correctAnswers = 0;
        int allAnswers = linearLayout.getChildCount();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion = null;
            MultipleChoiceQuestion multipleChoiceQuestion = null;
            View v = linearLayout.getChildAt(i);
            try {
                yesOrNoQuestion = (YesOrNoQuestion) v;
            } catch (ClassCastException e) {
                multipleChoiceQuestion = (MultipleChoiceQuestion) v;
            }
            if (yesOrNoQuestion != null){

            }else {
                int answer = multipleChoiceQuestion.getAnswer();
                Log.d("TAG", "answer: " + answer);
                int correctAnswer = choiceQuestions.get(i).correctAnswer;
                Log.d("TAG", "correct answer: " + correctAnswer);
                if (correctAnswer == 0){
                    allAnswers--;
                } else if (answer == correctAnswer){
                    correctAnswers++;
                }
            }
        }
        Log.d("TAG", "getPercentageOfCorrectAnswers: " + correctAnswers);
        Log.d("TAG", "getPercentageOfCorrectAnswers: " + allAnswers);
        return (float) correctAnswers/allAnswers;
    }

    public ArrayList<Integer> getAnswers(){
        ArrayList<Integer> answers = new ArrayList<>();
        Log.d("answers", "list size: " + linearLayout.getChildCount());
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion = null;
            MultipleChoiceQuestion multipleChoiceQuestion = null;
            View v = linearLayout.getChildAt(i);
            try {
                yesOrNoQuestion = (YesOrNoQuestion) v;
            } catch (ClassCastException e) {
                multipleChoiceQuestion = (MultipleChoiceQuestion) v;
            }
            if (yesOrNoQuestion != null){
                answers.add(yesOrNoQuestion.getAnswer());
            }else {
                answers.add(multipleChoiceQuestion.getAnswer());
            }
        }
        return answers;
    }

    public boolean areAllQuestionsAnswered(){
        ArrayList<Integer> answers = new ArrayList<>();
        Log.d("answers", "list size: " + linearLayout.getChildCount());
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion = null;
            MultipleChoiceQuestion multipleChoiceQuestion = null;
            View v = linearLayout.getChildAt(i);
            try {
                yesOrNoQuestion = (YesOrNoQuestion) v;
            } catch (ClassCastException e) {
                multipleChoiceQuestion = (MultipleChoiceQuestion) v;
            }
            if (yesOrNoQuestion != null){
                if (yesOrNoQuestion.getAnswer() == 0){
                    return false;
                }
            }else {
                if (multipleChoiceQuestion.getAnswer() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public LinearLayout getQuestionViews(){
        return linearLayout;
    }
}
