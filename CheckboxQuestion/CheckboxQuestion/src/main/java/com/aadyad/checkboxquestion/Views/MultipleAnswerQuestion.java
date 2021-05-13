package com.aadyad.checkboxquestion.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aadyad.checkboxquestion.OnAnswerChangedListener;
import com.aadyad.checkboxquestion.Question;
import com.aadyad.checkboxquestion.QuestionListSettings;
import com.aadyad.checkboxquestion.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MultipleAnswerQuestion extends LinearLayout {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    Context context;

    String[] allOptions;

    private OnAnswerChangedListener onAnswerChangedListener;

    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<Integer> selectedAnswers;

    LinearLayout layout;
    LinearLayout mainLayout;
    private int spacing;
    private ArrayList<Integer> correctAnswer;

    public MultipleAnswerQuestion(Context context) {
        this(context, null);
        this.context = context;
        this.onAnswerChangedListener = null;
    }

    public MultipleAnswerQuestion(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.onAnswerChangedListener = null;

        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.multiple_answer_question, this, true);

        String title;
        String number;
        String a1;
        String a2;
        String a3;
        String a4;
        int spacing;
        float questionTextSize, optionTextSize;
        int boxLocation;
        int orientation;
        boolean numberEnabled;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MultipleAnswerQuestion, 0, 0);

        try {
            //optionLayoutHeight = a.getFloat(R.styleable.MultipleAnswerQuestion_option_layout_height, QuestionListSettings.TEXT_SIZE_DEFAULT);
            questionTextSize = a.getFloat(R.styleable.MultipleAnswerQuestion_question_text_size, QuestionListSettings.TEXT_SIZE_DEFAULT);
            optionTextSize = a.getFloat(R.styleable.MultipleAnswerQuestion_option_text_size, QuestionListSettings.TEXT_SIZE_DEFAULT);
            //questionLayoutHeight = a.getFloat(R.styleable.MultipleAnswerQuestion_question_layout_height, QuestionListSettings.TEXT_SIZE_DEFAULT);
            a1 = a.getString(R.styleable.MultipleAnswerQuestion_option_1);
            a2 = a.getString(R.styleable.MultipleAnswerQuestion_option_2);
            a3 = a.getString(R.styleable.MultipleAnswerQuestion_option_3);
            a4 = a.getString(R.styleable.MultipleAnswerQuestion_option_4);
            title = a.getString(R.styleable.MultipleAnswerQuestion_question_title);
            boxLocation = a.getInt(R.styleable.MultipleAnswerQuestion_checkbox_location, 0);
            orientation = a.getInt(R.styleable.MultipleAnswerQuestion_checkbox_orientation, 0);
            spacing = a.getInt(R.styleable.MultipleAnswerQuestion_spacing_between_boxes, 17);
            number = a.getString(R.styleable.MultipleAnswerQuestion_question_number);
            numberEnabled = a.getBoolean(R.styleable.MultipleAnswerQuestion_number_enabled, true);
        } finally {
            a.recycle();
        }

        init(title, number, numberEnabled, spacing, orientation, boxLocation, questionTextSize, optionTextSize, a1, a2, a3, a4);
    }

    // Setup views
    public void init(String title, String number, boolean numEnabled, int spacing, int orientation, int boxLocation, float questionTextSize, float optionTextSize, String... options) {
        TextView questionNumber = (TextView) findViewById(R.id.question_number);
        final CheckBox option1 = (CheckBox) findViewById(R.id.answer1);
        final CheckBox option2 = (CheckBox) findViewById(R.id.answer2);
        final CheckBox option3 = (CheckBox) findViewById(R.id.answer3);
        final CheckBox option4 = (CheckBox) findViewById(R.id.answer4);

        selectedAnswers = new ArrayList<>();

        layout = findViewById(R.id.multipleChoiceHolder);
        mainLayout = findViewById(R.id.mainLayout);

        this.spacing = spacing;
        this.allOptions = options;

        checkBoxes.add(option1);
        checkBoxes.add(option2);
        checkBoxes.add(option3);
        checkBoxes.add(option4);

        setQuestionTextSize(questionTextSize);
        setOptionTextSize(optionTextSize);

        Log.d("TAG", "init: " + Arrays.toString(options));

        String a1 = "";
        String a2 = "";
        String a3 = "";
        String a4 = "";

        try {
            a1 = options[0];
            a2 = options[1];
            a3 = options[2];
            a4 = options[3];
        } catch (Exception ignored) {

        }

        option1.setText(a1);
        option2.setText(a2);
        option3.setText(a3);
        option4.setText(a4);

        LinearLayout templayout = null;

        if (options.length > 4){
            Log.d("TAG", "init: length greater than 4");
            for (int i = 4; i < options.length; i++){

                if (templayout == null){
                    templayout = new LinearLayout(context);
                    templayout.setOrientation(HORIZONTAL);
                }

                if (orientation == Question.HORIZONTAL){
                    throw new RuntimeException("Cannot have more than 4 options when horizontal. Try split_vertical or vertical.");
                } else if (orientation == Question.SPLIT_VERTICAL){
                    Log.d("TAG", "init: " + templayout.getChildCount());
                    if (templayout.getChildCount() == 0){
                        Log.d("TAG", "init: child count 0");
                        View spacer = new View(context);
                        spacer.setLayoutParams(new ViewGroup.LayoutParams(0, spacing));
                        layout.addView(spacer);

                        //Creating new checkbox
                        final CheckBox checkBox = new CheckBox(context);
                        checkBox.setTextSize(optionTextSize);
                        checkBox.setText(options[i]);
                        checkBoxes.add(checkBox);
                        final int finalI = i;

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Integer selectedAnswer = finalI + 1;
                                if (isChecked){
                                    //Add selected answer to integer arraylist
                                    selectedAnswers.add(selectedAnswer);
                                } else{
                                    //Remove selected answer to integer arraylist
                                    selectedAnswers.remove(selectedAnswer);
                                }
                                if (onAnswerChangedListener != null) {
                                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                                }
                            }
                        });

                        templayout.addView(checkBox);
                        View v = new View(context);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(spacing, 0);
                        v.setLayoutParams(params);
                        templayout.addView(v);

                        try {
                            String s = options[i + 1];
                        } catch (Exception e) {
                            e.printStackTrace();
                            layout.addView(templayout);
                        }
                    } else if (templayout.getChildCount() == 2){
                        Log.d("TAG", "init: child count 2");

                        //Creating new checkbox
                        final CheckBox checkBox = new CheckBox(context);
                        checkBox.setTextSize(optionTextSize);
                        checkBox.setText(options[i]);
                        checkBoxes.add(checkBox);
                        final int finalI = i;

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Integer selectedAnswer = finalI + 1;
                                if (isChecked){
                                    //Add selected answer to integer arraylist
                                    selectedAnswers.add(selectedAnswer);
                                } else{
                                    //Remove selected answer to integer arraylist
                                    selectedAnswers.remove(selectedAnswer);
                                }
                                if (onAnswerChangedListener != null) {
                                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                                }
                            }
                        });

                        templayout.addView(checkBox);
                        layout.addView(templayout);
                        templayout = null;
                    }
                } else if (orientation == Question.FULL_VERTICAL){
                    Log.d("TAG", "init: " + templayout.getChildCount());
                    if (templayout.getChildCount() == 0){
                        Log.d("TAG", "init: child count 0");

                        //Creating new checkbox
                        final CheckBox checkBox = new CheckBox(context);
                        checkBox.setTextSize(optionTextSize);
                        checkBox.setText(options[i]);
                        checkBoxes.add(checkBox);
                        final int finalI = i;

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Integer selectedAnswer = finalI + 1;
                                if (isChecked){
                                    //Add selected answer to integer arraylist
                                    selectedAnswers.add(selectedAnswer);
                                } else{
                                    //Remove selected answer to integer arraylist
                                    selectedAnswers.remove(selectedAnswer);
                                }
                                if (onAnswerChangedListener != null) {
                                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                                }
                            }
                        });

                        templayout.addView(checkBox);
                        View v = new View(context);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(0, spacing);
                        v.setLayoutParams(params);
                        layout.addView(v);
                        layout.addView(templayout);
                        templayout = null;
                    }
                }
            }
        }

//        if (a1 == null || a2 == null){
//            throw new RuntimeException("A MultipleAnswerQuestion must have 2 or more options!");
//        }

        if (a3 == null) {
            option3.setVisibility(GONE);
        } else{
            option3.setVisibility(VISIBLE);
        }
        if (a4 == null) {
            option4.setVisibility(GONE);
        } else{
            option4.setVisibility(VISIBLE);
        }

        if (option4.getText().toString().equals("")){
            option4.setVisibility(GONE);
        }
        if (option3.getText().toString().equals("")){
            option3.setVisibility(GONE);
        }

        //Todo: add option to choose location of question text

        setCheckBoxLocation(boxLocation);

        setCheckboxOrientation(orientation);

        setQuestion(title);
        if (numEnabled) {
            setQuestionNumber(number);
            questionNumber.setVisibility(VISIBLE);
        } else {
            questionNumber.setVisibility(GONE);
        }

        onFinishInflate();
    }

    private void setCheckBoxLocation(int location) {
        if (location == LEFT){
            mainLayout.setGravity(Gravity.LEFT);
        } else if (location == CENTER){
            mainLayout.setGravity(Gravity.CENTER);
        } else if (location == RIGHT){
            mainLayout.setGravity(Gravity.RIGHT);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final CheckBox option1 = (CheckBox) findViewById(R.id.answer1);
        final CheckBox option2 = (CheckBox) findViewById(R.id.answer2);
        final CheckBox option3 = (CheckBox) findViewById(R.id.answer3);
        final CheckBox option4 = (CheckBox) findViewById(R.id.answer4);

        option1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer selectedAnswer = 1;
                if (isChecked){
                    //Add selected answer to integer arraylist
                    selectedAnswers.add(selectedAnswer);
                } else{
                    //Remove selected answer to integer arraylist
                    selectedAnswers.remove(selectedAnswer);
                }
                if (onAnswerChangedListener != null) {
                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                }
            }
        });

        option2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer selectedAnswer = 2;
                if (isChecked){
                    //Add selected answer to integer arraylist
                    selectedAnswers.add(selectedAnswer);
                } else{
                    //Remove selected answer to integer arraylist
                    selectedAnswers.remove(selectedAnswer);
                }
                if (onAnswerChangedListener != null) {
                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                }
            }
        });

        option3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer selectedAnswer = 3;
                if (isChecked){
                    //Add selected answer to integer arraylist
                    selectedAnswers.add(selectedAnswer);
                } else{
                    //Remove selected answer to integer arraylist
                    selectedAnswers.remove(selectedAnswer);
                }
                if (onAnswerChangedListener != null) {
                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                }
            }
        });

        option4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer selectedAnswer = 4;
                if (isChecked){
                    //Add selected answer to integer arraylist
                    selectedAnswers.add(selectedAnswer);
                } else{
                    //Remove selected answer to integer arraylist
                    selectedAnswers.remove(selectedAnswer);
                }
                if (onAnswerChangedListener != null) {
                    onAnswerChangedListener.onAnswerChanged(selectedAnswers);
                }
            }
        });
    }

    public ArrayList<Integer> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setQuestion(String question) {
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        questionTitle.setText(question);
    }

    public void setOptionTextSize(float optionTextSize){
        final CheckBox option1 = (CheckBox) findViewById(R.id.answer1);
        final CheckBox option2 = (CheckBox) findViewById(R.id.answer2);
        final CheckBox option3 = (CheckBox) findViewById(R.id.answer3);
        final CheckBox option4 = (CheckBox) findViewById(R.id.answer4);

        if (optionTextSize == QuestionListSettings.TEXT_SIZE_AUTO){
            //Todo: add code to auto adjust size using textheight
        }else if (!(optionTextSize == QuestionListSettings.TEXT_SIZE_DEFAULT)){
            option1.setTextSize(optionTextSize);
            option2.setTextSize(optionTextSize);
            option3.setTextSize(optionTextSize);
            option4.setTextSize(optionTextSize);
        }
    }

    public void setQuestionTextSize(float questionTextSize){
        TextView questionNumber = (TextView) findViewById(R.id.question_number);
        TextView questionTitle = (TextView) findViewById(R.id.question_title);

        if (questionTextSize == QuestionListSettings.TEXT_SIZE_AUTO){
            //Todo: add code to auto adjust size using height
        } else if (!(questionTextSize == QuestionListSettings.TEXT_SIZE_DEFAULT)){
            questionTitle.setTextSize(questionTextSize);
            questionNumber.setTextSize(questionTextSize);
        }
    }

    public void setQuestionNumber(String number) {
        TextView questionNumber = (TextView) findViewById(R.id.question_number);
        questionNumber.setText(number + ". ");
    }

    public void setCheckboxOrientation(int orientation){
        View spacing1 = findViewById(R.id.spacing1);
        View layoutSpacing = findViewById(R.id.spacingLayouts);
        View spacing2 = findViewById(R.id.spacing2);

        Log.d("Orientation", "setLayoutOrientation: " + orientation);

        ViewGroup.LayoutParams layoutParams1 = spacing1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = spacing2.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = layoutSpacing.getLayoutParams();

        if (orientation == Question.HORIZONTAL){
            //Log.d("Orientation", "setLayoutOrientation: horizontal");
            layoutParams1.width = spacing;
            layoutParams2.width = spacing;
            layoutParams3.width = spacing;
            layoutParams1.height = 0;
            layoutParams2.height = 0;
            layoutParams3.height= 0;
            layoutSpacing.setLayoutParams(layoutParams3);
            spacing1.setLayoutParams(layoutParams1);
            spacing2.setLayoutParams(layoutParams2);
            layout.setOrientation(HORIZONTAL);
        } else if (orientation == Question.SPLIT_VERTICAL){
            layout.setOrientation(VERTICAL);
            layoutParams3.width = 0;
            layoutParams1.width = spacing;
            layoutParams2.width = spacing;
            layoutParams3.height = spacing;
            layoutParams1.height = 0;
            layoutParams2.height = 0;
            layoutSpacing.setLayoutParams(layoutParams3);
            spacing1.setLayoutParams(layoutParams1);
            spacing2.setLayoutParams(layoutParams2);
        } else if (orientation == Question.AUTO){
            //Todo: code auto orientation if a question goes out of view
        } else if (orientation == Question.FULL_VERTICAL){
            LinearLayout layout1 = findViewById(R.id.checkBoxHolder1);
            LinearLayout layout2 = findViewById(R.id.checkBoxHolder2);
            layout1.setOrientation(VERTICAL);
            layout2.setOrientation(VERTICAL);
            layout.setOrientation(VERTICAL);
            layoutParams3.width = 0;
            layoutParams1.width = 0;
            layoutParams2.width = 0;
            layoutParams3.height = spacing;
            layoutParams1.height = spacing;
            layoutParams2.height = spacing;
            layoutSpacing.setLayoutParams(layoutParams3);
            spacing1.setLayoutParams(layoutParams1);
            spacing2.setLayoutParams(layoutParams2);
        }
    }

    public void addOnAnswerChangedListener(OnAnswerChangedListener onAnswerChangedListener){
        this.onAnswerChangedListener = onAnswerChangedListener;
    }

    public void setCheckedOption(int option){
        for (int i = 0; i < checkBoxes.size(); i++){
            if (i == option - 1){
                checkBoxes.get(i).setChecked(true);
                break;
            } else{
                checkBoxes.get(i).setChecked(false);
            }
        }
    }

    public void setCheckedOption(String option){
        for (CheckBox checkBox : checkBoxes){
            if (checkBox.getText().equals(option)){
                checkBox.setChecked(true);
                break;
            } else{
                checkBox.setChecked(false);
            }
        }
    }

    public void setOptionText(int index, String text){
        checkBoxes.get(index).setText(text);
    }

    public CheckBox getCheckbox(int index){
        return checkBoxes.get(index);
    }

    public TextView getQuestionTitleTextView(){
        return findViewById(R.id.question_title);
    }

    public TextView getQuestionNumberTextView(){
        return findViewById(R.id.question_number);
    }

    public String[] getOptions(){
        return allOptions;
    }

    public void setCorrectAnswer(ArrayList<Integer> correctAnswer){
        Collections.sort(correctAnswer);
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<Integer> getCorrectAnswer(){
        return correctAnswer;
    }

    public boolean isAnswerCorrect() throws Exception{
        if (getCorrectAnswer() == null){
            throw new Exception("There is no correct answer for this question.");
        }
        Collections.sort(selectedAnswers);
        return getCorrectAnswer().equals(selectedAnswers);
    }
}
