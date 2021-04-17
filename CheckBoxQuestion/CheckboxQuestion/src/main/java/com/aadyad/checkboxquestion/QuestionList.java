package com.aadyad.checkboxquestion;

import java.util.ArrayList;

public class QuestionList {

    ArrayList<String> questions;
    QuestionListSettings settings;

    public QuestionList(ArrayList<String> questions, QuestionListSettings settings){
        this.questions = questions;
        this.settings = settings;
    }
}
