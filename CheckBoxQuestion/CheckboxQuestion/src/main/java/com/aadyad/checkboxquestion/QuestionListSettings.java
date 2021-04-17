package com.aadyad.checkboxquestion;

import android.text.Layout;
import android.view.Gravity;

public class QuestionListSettings {

    private int checkBoxLocation;
    private int checkBoxOrientation;
    private int spacing;
    private boolean numEnabled;

    public QuestionListSettings(Builder builder){
        checkBoxLocation = builder.checkBoxLocation;
        checkBoxOrientation = builder.checkBoxOrientation;
        spacing = builder.spacing;
        numEnabled = builder.numEnabled;
    }

    static class Builder {
        private int checkBoxLocation;
        private int checkBoxOrientation;
        private int spacing;
        private boolean numEnabled;
        private boolean allowUnansweredQuestions;

        public Builder setCheckboxLocation(final int checkBoxLocation) {
            this.checkBoxLocation = checkBoxLocation;
            return this;
        }

        public Builder setCheckBoxOrientation(final int checkBoxOrientation) {
            this.checkBoxOrientation = checkBoxOrientation;
            return this;
        }

        public Builder setSpacing(final int spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder setNumberEnabled(final boolean numEnabled) {
            this.numEnabled = numEnabled;
            return this;
        }

        public Builder allowUnansweredQuestions(final boolean allowUnansweredQuestions){
            this.allowUnansweredQuestions = allowUnansweredQuestions;
            return this;
        }

        public QuestionListSettings create() {
            return new QuestionListSettings(this);
        }
    }
}
