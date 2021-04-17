# CheckboxQuestions
CheckboxQuestions is a library that provides with different forms of asking questions. So far there are [YesOrNoQuestions](#YesOrNoQuestions) and [MultipleChoiceQuestions](#MultipleChoiceQuestions) and 

# QuestionsList
This is the easiest way to add questions. This works by adding questions to a layout defined in your layout xml file.
Here is an example layout file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <LinearLayout
            android:id="@+id/questionLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center|top"
            android:orientation="vertical">

            <Button
                android:id="@+id/getAnswers"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:onClick="logAnswers"
                android:text="Press me!" />
        </LinearLayout>

    </ScrollView>


</androidx.constraintlayout.widget.ConstraintLayout>
```
(The button is to get answers, the scrollview is in case there are too many questions and they go offscreen)

We mainly only care about the linear layout in the scrollview.

```java
LinearLayout linearLayout = findViewById(R.id.questionLayout);
```
Now we want to create our QuestionList, but first you have to give it some settings so the view is displayed to your likings.

```java
QuestionListSettings questionListSettings = new QuestionListSettings.SettingsBuilder()
                                .setCheckboxLocation(Question.LEFT)
                                .setCheckBoxOrientation(Question.SPLIT_VERTICAL)
                                .setNumberEnabled(false)
                                .setOptionTextSize(20)
                                .setQuestionTextSize(24)
                                .setSpacing(15)
                                .create();
```
The `setCheckboxLocation()` allows you to choose whether the options for the question are to the left, center, or right of the screen.
The `setCheckBoxOrientation()` allows you to choose whether the options for the question are stacked or sideways to eachother.
The `setNumberEnabled()` allows you to choose whether the questions have a visible number.
The `setSpacing()` allows you to choose how far apart the options are from eachother.
The `setQuestionTextSize()` and `setOptionTextSize()` allows you to choose the text size for the question and th options respectively.

Now we want to create our list of questions. There are 2 ways to do this. If you want simple yes or no questions, just create a String array full of questions.

```java
String[] string = new String[]{"Is 9+2 = 11?", "Are you happy?", "Did you eat breakfast?"};
```

If you want to use multiple choice questions, create an ArrayList of [Questions](#question).

```java
ArrayList<Question> list = new ArrayList<>();

list.add(new Question("How are you?", 0, "Good", "Bad"));
```

# YesOrNoQuestions
YesOrNoQuestions are a simple form of question which show a question with a number, and only allow a yes or no as an answer, while MultipleChoiceQuestions allow [anything](##Options) as a option. To use it in an XML layout just use the following code:
```xml
<com.aadyad.checkboxquestion.YesOrNoQuestion
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        app:number_enabled="true"
        app:spacing_between_boxes="35"
        app:checkbox_location="center"
        app:checkbox_orientation="horizontal"
        app:question_number="1"
        app:question_title="Do you have a cold?"
        app:question_text_size="20"
        app:option_text_size="18" />
```

## Number Enabled
The `number_enabled` attribute allows you to make the number visible or not.

## Spacing Between Boxes
The `spacing_between_boxes` attribute allows you to choose the spacing between the checkboxes.

## Checkbox Location
The `checkbox_location` attribute allows you to choose whether the checkboxes are to the left, center, or right of the screen. To specify where they are just use `app:checkbox_location="left"`, `app:checkbox_location="center"`, or`app:checkbox_location="right"`.

## Checkbox Orientation
The `checkbox_orientation` attribute allows you to choose whether the checkboxes are stacked or if they are horizontal. To stack them use `app:checkbox_orientation="split_vertical"` to use the horizontally use `app:checkbox_orientation="horizontal"`

## Question Number
The `question_number` attribute allows you to set the number of the question.


## Question Title
The `question_title` attribute allows you to set the question text.

## Question Text Size
The `question_text_size` attribute allows you to set the question text size.

## Option Text Size
The `option_text_size` attribute allows you to set the option text size.

# MultipleChoiceQuestions
