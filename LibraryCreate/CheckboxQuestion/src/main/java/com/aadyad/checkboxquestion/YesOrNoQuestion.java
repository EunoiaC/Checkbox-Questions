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

import androidx.annotation.Nullable;

public class YesOrNoQuestion extends LinearLayout {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private int buttonClicked = 0; //0 is not clicked, 1 is no, 2 is yes

    public YesOrNoQuestion(Context context) {
        this(context, null);
    }

    public YesOrNoQuestion(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.yes_or_no_question, this, true);

        String title;
        String number;
        int spacing;
        int boxLocation;
        int orientation;
        float questionTextSize;
        float checkBozTextSize;
        float questionLayoutHeight;
        float optionLayoutHeight;
        boolean numberEnabled;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.YesOrNoQuestion, 0, 0);

        try {
            optionLayoutHeight = a.getFloat(R.styleable.YesOrNoQuestion_option_layout_height, QuestionListSettings.TEXT_SIZE_DEFAULT);
            questionTextSize = a.getFloat(R.styleable.YesOrNoQuestion_question_text_size, QuestionListSettings.TEXT_SIZE_DEFAULT);
            checkBozTextSize = a.getFloat(R.styleable.YesOrNoQuestion_option_text_size, QuestionListSettings.TEXT_SIZE_DEFAULT);
            questionLayoutHeight = a.getFloat(R.styleable.YesOrNoQuestion_question_layout_height, QuestionListSettings.TEXT_SIZE_DEFAULT);
            title = a.getString(R.styleable.YesOrNoQuestion_question_title);
            boxLocation = a.getInt(R.styleable.YesOrNoQuestion_checkbox_location, 0);
            orientation = a.getInt(R.styleable.YesOrNoQuestion_checkbox_orientation, 0);
            spacing = a.getInt(R.styleable.YesOrNoQuestion_spacing_between_boxes, 17);
            number = a.getString(R.styleable.YesOrNoQuestion_question_number);
            numberEnabled = a.getBoolean(R.styleable.YesOrNoQuestion_number_enabled, true);
        } finally {
            a.recycle();
        }

        init(title, number, numberEnabled, spacing, orientation, boxLocation, questionTextSize, checkBozTextSize, questionLayoutHeight, optionLayoutHeight);
    }

    // Setup views
    public void init(String title, String number, boolean numEnabled, int spacing, int orientation, int boxLocation, float questionSize, float checkBoxSize, float questionLayoutHeight, float optionLayoutHeight) {
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        TextView questionNumber = (TextView) findViewById(R.id.question_number);
        View space = findViewById(R.id.spacing);
        LinearLayout layout = findViewById(R.id.checkBoxHolder);

        //Todo: Set height if size == auto.
        //Todo: Also use height

        final CheckBox yes = (CheckBox) findViewById(R.id.yes);
        final CheckBox no = (CheckBox) findViewById(R.id.no);
        if (!(questionSize == QuestionListSettings.TEXT_SIZE_DEFAULT)){
            questionTitle.setTextSize(questionSize);
            questionNumber.setTextSize(questionSize);
        }
        if (!(checkBoxSize == QuestionListSettings.TEXT_SIZE_DEFAULT)){
            yes.setTextSize(checkBoxSize);
            no.setTextSize(checkBoxSize);
        }

        ViewGroup.LayoutParams layoutParams = space.getLayoutParams();

        if (boxLocation == LEFT){
            layout.setGravity(Gravity.LEFT);
        } else if (boxLocation == CENTER){
            layout.setGravity(Gravity.CENTER);
        } else if (boxLocation == RIGHT){
            layout.setGravity(Gravity.RIGHT);
        }

        if (orientation == 0){
            layoutParams.width = spacing;
            layoutParams.height = 0;
            space.setLayoutParams(layoutParams);
            layout.setOrientation(HORIZONTAL);
        } else if (orientation == 1){
            layout.setOrientation(VERTICAL);
            layoutParams.height = spacing;
            layoutParams.width = 0;
            space.setLayoutParams(layoutParams);
        }

        questionTitle.setText(title);
        if (numEnabled){
            questionNumber.setText(number + ". ");
            questionNumber.setVisibility(VISIBLE);
        } else{
            questionNumber.setVisibility(GONE);
        }

        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final CheckBox yes = (CheckBox) findViewById(R.id.yes);
        final CheckBox no = (CheckBox) findViewById(R.id.no);

        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setChecked(false);
                yes.setChecked(true);
                buttonClicked = 2;
            }
        });

        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setChecked(true);
                yes.setChecked(false);
                buttonClicked = 1;
            }
        });
    }

    public int getAnswer(){
        return buttonClicked;
    }

    public void setQuestion(String question){
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        questionTitle.setText(question);
    }

    public void setTextSize(float questionSize, float checkBoxSize, float height){
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        TextView questionNumber = (TextView) findViewById(R.id.question_number);
        final CheckBox yes = (CheckBox) findViewById(R.id.yes);
        final CheckBox no = (CheckBox) findViewById(R.id.no);
        if (!(questionSize == QuestionListSettings.TEXT_SIZE_DEFAULT)){
            questionTitle.setTextSize(questionSize);
            questionNumber.setTextSize(questionSize);
        }
        if (!(checkBoxSize == QuestionListSettings.TEXT_SIZE_DEFAULT)){
            yes.setTextSize(checkBoxSize);
            no.setTextSize(checkBoxSize);
        }

        //yes.setTextSize(size);
    }

    public void setQuestionNumber(String number){
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        questionTitle.setText(number + ". ");
    }
}