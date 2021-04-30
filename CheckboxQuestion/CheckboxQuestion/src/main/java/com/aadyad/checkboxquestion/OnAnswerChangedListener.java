package com.aadyad.checkboxquestion;

import java.util.ArrayList;

public interface OnAnswerChangedListener {
    public void onAnswerChanged(int selectedAnswerIndex, String selectedAnswerText);
    public void onAnswerChanged(ArrayList<Integer> listOfSelectedAnswerIndexes);
}
