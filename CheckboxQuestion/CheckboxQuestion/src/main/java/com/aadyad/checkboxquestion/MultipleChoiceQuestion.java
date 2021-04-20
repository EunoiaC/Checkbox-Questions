package com.aadyad.checkboxquestion;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class MultipleChoiceQuestion extends LinearLayout {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private int buttonClicked = 0; //0 is not clicked, 1 is no, 2 is yes

    public MultipleChoiceQuestion(Context context) {
        this(context, null);
    }

    public MultipleChoiceQuestion(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.multiple_choice_question, this, true);

        String title;
        String number;
        String a1;
        String a2;
        String a3;
        String a4;
        int spacing;
        float questionTextSize, optionTextSize, questionLayoutHeight, optionLayoutHeight;
        int boxLocation;
        int orientation;
        boolean numberEnabled;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MultipleChoiceQuestion, 0, 0);

        try {
            optionLayoutHeight = a.getFloat(R.styleable.MultipleChoiceQuestion_option_layout_height, QuestionListSettings.TEXT_SIZE_DEFAULT);
            questionTextSize = a.getFloat(R.styleable.MultipleChoiceQuestion_question_text_size, QuestionListSettings.TEXT_SIZE_DEFAULT);
            optionTextSize = a.getFloat(R.styleable.MultipleChoiceQuestion_option_text_size, QuestionListSettings.TEXT_SIZE_DEFAULT);
            questionLayoutHeight = a.getFloat(R.styleable.MultipleChoiceQuestion_question_layout_height, QuestionListSettings.TEXT_SIZE_DEFAULT);
            a1 = a.getString(R.styleable.MultipleChoiceQuestion_option_1);
            a2 = a.getString(R.styleable.MultipleChoiceQuestion_option_2);
            a3 = a.getString(R.styleable.MultipleChoiceQuestion_option_3);
            a4 = a.getString(R.styleable.MultipleChoiceQuestion_option_4);
            title = a.getString(R.styleable.MultipleChoiceQuestion_question_title);
            boxLocation = a.getInt(R.styleable.MultipleChoiceQuestion_checkbox_location, 0);
            orientation = a.getInt(R.styleable.MultipleChoiceQuestion_checkbox_orientation, 0);
            spacing = a.getInt(R.styleable.MultipleChoiceQuestion_spacing_between_boxes, 17);
            number = a.getString(R.styleable.MultipleChoiceQuestion_question_number);
            numberEnabled = a.getBoolean(R.styleable.MultipleChoiceQuestion_number_enabled, true);
        } finally {
            a.recycle();
        }

        init(title, number, numberEnabled, spacing, orientation, boxLocation, questionTextSize, optionTextSize, questionLayoutHeight, optionLayoutHeight, a1, a2, a3, a4);
    }

    // Setup views
    public void init(String title, String number, boolean numEnabled, int spacing, int orientation, int boxLocation, float questionTextSize, float optionTextSize, float questionLayoutHeight, float optionLayoutHeight, String... options) {
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        TextView questionNumber = (TextView) findViewById(R.id.question_number);
        final CheckBox option1 = (CheckBox) findViewById(R.id.answer1);
        final CheckBox option2 = (CheckBox) findViewById(R.id.answer2);
        final CheckBox option3 = (CheckBox) findViewById(R.id.answer3);
        final CheckBox option4 = (CheckBox) findViewById(R.id.answer4);

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
        } catch (Exception e) {

        }

        option1.setText(a1);
        option2.setText(a2);
        option3.setText(a3);
        option4.setText(a4);

//        if (a1 == null || a2 == null){
//            throw new RuntimeException("A MultipleChoiceQuestion must have 2 or more options!");
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

        View spacing1 = findViewById(R.id.spacing1);
        View layoutSpacing = findViewById(R.id.spacingLayouts);
        View spacing2 = findViewById(R.id.spacing2);
        LinearLayout layout = findViewById(R.id.multipleChoiceHolder);

        ViewGroup.LayoutParams layoutParams1 = spacing1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = spacing2.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = layoutSpacing.getLayoutParams();

        //Todo: add option to choose location of question text

        if (boxLocation == LEFT){
            layout.setGravity(Gravity.LEFT);
        } else if (boxLocation == CENTER){
            layout.setGravity(Gravity.CENTER);
        } else if (boxLocation == RIGHT){
            layout.setGravity(Gravity.RIGHT);
        }

        if (orientation == Question.HORIZONTAL){
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

        setQuestion(title);
        if (numEnabled) {
            setQuestionNumber(number);
            questionNumber.setVisibility(VISIBLE);
        } else {
            questionNumber.setVisibility(GONE);
        }

        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final CheckBox option1 = (CheckBox) findViewById(R.id.answer1);
        final CheckBox option2 = (CheckBox) findViewById(R.id.answer2);
        final CheckBox option3 = (CheckBox) findViewById(R.id.answer3);
        final CheckBox option4 = (CheckBox) findViewById(R.id.answer4);

        option1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);
                option1.setChecked(true);
                buttonClicked = 1;
            }
        });

        option2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setChecked(true);
                option3.setChecked(false);
                option4.setChecked(false);
                option1.setChecked(false);
                buttonClicked = 2;
            }
        });

        option3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setChecked(false);
                option3.setChecked(true);
                option4.setChecked(false);
                option1.setChecked(false);
                buttonClicked = 3;
            }
        });

        option4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(true);
                option1.setChecked(false);
                buttonClicked = 4;
            }
        });
    }

    public int getAnswer() {
        return buttonClicked;
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
}
