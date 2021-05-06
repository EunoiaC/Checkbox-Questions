package com.aadyad.checkboxquestion;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.aadyad.checkboxquestion.Views.MultipleAnswerQuestion;
import com.aadyad.checkboxquestion.Views.MultipleChoiceQuestion;
import com.aadyad.checkboxquestion.Views.YesOrNoQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuestionList {

    String[] questions;
    ArrayList<Question> choiceQuestions;
    LinearLayout linearLayout;
    QuestionListSettings settings;
    Context context;
    int orientation;

    public QuestionList(ArrayList<Question> questions, QuestionListSettings settings, Context context) {
        choiceQuestions = questions;
        this.settings = settings;
        this.context = context;
        this.orientation = settings.getCheckBoxOrientation();
        linearLayout = new LinearLayout(context);
    }

    public QuestionList(String[] questions, QuestionListSettings settings, Context context) {
        this.questions = questions;
        this.settings = settings;
        this.orientation = settings.getCheckBoxOrientation();
        this.context = context;
        linearLayout = new LinearLayout(context);
    }

    public void createQuestionViews() {
        int i = 1;
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        if (questions == null) {
            for (Question q : choiceQuestions) {
                switch (q.type) {
                    case Question.MULTIPLE_CHOICE_QUESTION:
                        Log.d("TAG", "createQuestionViews: " + Arrays.toString(q.options));
                        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(context);
                        multipleChoiceQuestion.init(q.question, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), this.orientation, settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), q.correctAnswer, q.options);
                        linearLayout.addView(multipleChoiceQuestion);
                        break;
                    case Question.YES_OR_NO_QUESTION:
                        YesOrNoQuestion yesOrNoQuestion = new YesOrNoQuestion(context);
                        yesOrNoQuestion.init(q.question, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), this.orientation, settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), q.correctAnswer);
                        linearLayout.addView(yesOrNoQuestion);
                        break;
                    case Question.MULTIPLE_ANSWER_QUESTION:
                        MultipleAnswerQuestion multipleAnswerQuestion = new MultipleAnswerQuestion(context);
                        multipleAnswerQuestion.init(q.question, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), this.orientation, settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), q.options);
                        multipleAnswerQuestion.setCorrectAnswer(q.multipleCorrectAnswer);
                        linearLayout.addView(multipleAnswerQuestion);
                        break;
                    default:
                        throw new RuntimeException("Type " + q.type + " does not exist.");
                }
                i++;
            }
        } else {
            for (String q : questions) {
                i++;
                YesOrNoQuestion yesOrNoQuestion = new YesOrNoQuestion(context);
                yesOrNoQuestion.init(q, String.valueOf(i), settings.isNumEnabled(), settings.getSpacing(), this.orientation, settings.getCheckBoxLocation(), settings.getQuestionTextSize(), settings.getCheckBoxTextSize(), Question.NO_ANSWER);
                linearLayout.addView(yesOrNoQuestion);
            }
        }

    }

    public void setLayoutOrientation(int orientation) {
        this.orientation = orientation;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion;
            MultipleChoiceQuestion multipleChoiceQuestion;
            MultipleAnswerQuestion multipleAnswerQuestion;

            try {
                yesOrNoQuestion = (YesOrNoQuestion) getQuestion(i);
                yesOrNoQuestion.setCheckboxOrientation(orientation);
            } catch (ClassCastException ignored) {

            }

            try {
                multipleChoiceQuestion = (MultipleChoiceQuestion) getQuestion(i);
                multipleChoiceQuestion.setCheckboxOrientation(orientation);
            } catch (ClassCastException ignored) {

            }

            try {
                multipleAnswerQuestion = (MultipleAnswerQuestion) getQuestion(i);
                multipleAnswerQuestion.setCheckboxOrientation(orientation);
            } catch (ClassCastException ignored) {

            }
        }
        createQuestionViews();
    }

    public float getPercentageOfCorrectAnswers() {
        int correctAnswers = 0;
        int allAnswers = linearLayout.getChildCount();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion;
            MultipleChoiceQuestion multipleChoiceQuestion;
            MultipleAnswerQuestion multipleAnswerQuestion;

            try {
                yesOrNoQuestion = (YesOrNoQuestion) getQuestion(i);
                int answer = yesOrNoQuestion.getSelectedAnswer();
                Log.d("TAG", "answer: " + answer);
                int correctAnswer = choiceQuestions.get(i).correctAnswer;
                Log.d("TAG", "correct answer: " + correctAnswer);
                if (correctAnswer == Question.NO_ANSWER) {
                    allAnswers--;
                } else if (answer == correctAnswer) {
                    correctAnswers++;
                }
            } catch (ClassCastException ignored) {

            }

            try {
                multipleChoiceQuestion = (MultipleChoiceQuestion) getQuestion(i);
                int answer = multipleChoiceQuestion.getSelectedAnswer();
                Log.d("TAG", "answer: " + answer);
                int correctAnswer = choiceQuestions.get(i).correctAnswer;
                Log.d("TAG", "correct answer: " + correctAnswer);
                if (correctAnswer == Question.NO_ANSWER) {
                    allAnswers--;
                } else if (answer == correctAnswer) {
                    correctAnswers++;
                }
            } catch (Exception ignored) {

            }

            try {
                multipleAnswerQuestion = (MultipleAnswerQuestion) getQuestion(i);
                ArrayList<Integer> answer = multipleAnswerQuestion.getSelectedAnswers();
                Collections.sort(answer);
                Log.d("TAG", "answer: " + answer);
                ArrayList<Integer> correctAnswer = choiceQuestions.get(i).multipleCorrectAnswer;
                Collections.sort(correctAnswer);
                Log.d("TAG", "correct answer: " + correctAnswer);
                if (correctAnswer.size() == Question.NO_ANSWER || correctAnswer == null) {
                    allAnswers--;
                } else if (answer.equals(correctAnswer)) {
                    correctAnswers++;
                }
            } catch (ClassCastException ignored) {

            }
        }
        Log.d("TAG", "getPercentageOfCorrectAnswers: " + correctAnswers);
        Log.d("TAG", "getPercentageOfCorrectAnswers: " + allAnswers);
        return (float) correctAnswers / allAnswers;
    }

    public ArrayList<Object> getSelectedAnswers() {
        ArrayList<Object> answers = new ArrayList<>();
        Log.d("answers", "list size: " + linearLayout.getChildCount());
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion;
            MultipleChoiceQuestion multipleChoiceQuestion;
            MultipleAnswerQuestion multipleAnswerQuestion;

            try {
                yesOrNoQuestion = (YesOrNoQuestion) getQuestion(i);
                answers.add(yesOrNoQuestion.getSelectedAnswer());
            } catch (ClassCastException ignored) {

            }

            try {
                multipleChoiceQuestion = (MultipleChoiceQuestion) getQuestion(i);
                answers.add(multipleChoiceQuestion.getSelectedAnswer());
            } catch (Exception ignored) {

            }

            try {
                multipleAnswerQuestion = (MultipleAnswerQuestion) getQuestion(i);
                answers.add(multipleAnswerQuestion.getSelectedAnswers());
            } catch (Exception ignored) {

            }
        }
        return answers;
    }

    public boolean areAllQuestionsAnswered() {
        Log.d("answers", "list size: " + linearLayout.getChildCount());
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            YesOrNoQuestion yesOrNoQuestion;
            MultipleChoiceQuestion multipleChoiceQuestion;
            MultipleAnswerQuestion multipleAnswerQuestion;

            try {
                yesOrNoQuestion = (YesOrNoQuestion) getQuestion(i);
                if (yesOrNoQuestion.getSelectedAnswer() == 0) {
                    return false;
                }
            } catch (ClassCastException ignored) {

            }
            try {
                multipleChoiceQuestion = (MultipleChoiceQuestion) getQuestion(i);
                if (multipleChoiceQuestion.getSelectedAnswer() == 0) {
                    return false;
                }
            } catch (Exception ignored) {

            }

            try {
                multipleAnswerQuestion = (MultipleAnswerQuestion) getQuestion(i);
                if (multipleAnswerQuestion.getSelectedAnswers().size() == 0) {
                    return false;
                }
            } catch (Exception ignored) {

            }
        }
        return true;
    }

    public View getQuestion(int index) {
        YesOrNoQuestion yesOrNoQuestion = null;
        MultipleChoiceQuestion multipleChoiceQuestion = null;
        MultipleAnswerQuestion multipleAnswerQuestion = null;
        View v = linearLayout.getChildAt(index);
        try {
            yesOrNoQuestion = (YesOrNoQuestion) v;
        } catch (ClassCastException ignored) {

        }

        try {
            multipleChoiceQuestion = (MultipleChoiceQuestion) v;
        } catch (Exception ignored) {

        }

        try {
            multipleAnswerQuestion = (MultipleAnswerQuestion) v;
        } catch (Exception ignored) {

        }

        if (yesOrNoQuestion != null) {
            return yesOrNoQuestion;
        } else if (multipleChoiceQuestion != null) {
            return multipleChoiceQuestion;
        } else if (multipleAnswerQuestion != null) {
            return multipleAnswerQuestion;
        } else {
            return null;
        }
    }

    public void addOnAnswerChangedListener(int index, OnAnswerChangedListener r) {
        try {
            ((MultipleChoiceQuestion) getQuestion(index)).addOnAnswerChangedListener(r);
        } catch (Exception ignored) {

        }

        try {
            ((MultipleAnswerQuestion) getQuestion(index)).addOnAnswerChangedListener(r);
        } catch (Exception ignored) {

        }

        try {
            ((YesOrNoQuestion) getQuestion(index)).addOnAnswerChangedListener(r);
        } catch (Exception ignored) {

        }
    }

    public LinearLayout getQuestionViews() {
        return linearLayout;
    }
}
