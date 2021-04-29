package com.aadyad.checkboxquestion;

import java.util.ArrayList;

public interface OnAnswerChangedListener {
    public void onAnswerChanged(int answer);
    public void onAnswerChanged(ArrayList<Integer> answer);
}
