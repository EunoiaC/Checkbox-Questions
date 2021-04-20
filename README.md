# CheckboxQuestions
CheckboxQuestions is a library that provides with different forms of asking questions. So far there are [YesOrNoQuestions](#YesOrNoQuestions) and [MultipleChoiceQuestions](#MultipleChoiceQuestions).

#### Table of Contents

<ul>
	<li><a href="#Images">Images</a>
	<li><a href="#Implementation">Implementation</a>
        <ul>
            <li><a href="#For-Gradle">Gradle</li>
            <li><a href="#For-Maven">Maven</li>
        </ul>
    	</li>
    <li><a href="#QuestionsList">QuestionsList</a>
        <ul>
            <li><a href="#Settings-Builder">Settings Builder</li>
            <li><a href="#List-of-questions">List of questions</li>
            <li><a href="#Creating-the-QuestionList">Initialization</li>
            <li><a href="#Getting-the-questions-and-diplaying-them">Displaying views</li>
            <li><a href="#All-methods">Methods</li>
        </ul>
    </li>
    <li><a href="#YesOrNoQuestions">YesOrNoQuestions</a></li>
    <li><a href="#MultipleChoiceQuestions">MultipleChoiceQuestions</a></li>
    <li><a href="#Questions">Questions</a></li>
</ul>

# Images


# Implementation

## For Gradle
In your project level build.gradle first add JitPack:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
In your app level build.gradle add:
```gradle
dependencies {
    implementation 'com.github.Cyber-cp:CheckboxQuestions:v1.2.0'
}
```

## For Maven
First add JitPack to your build file:
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Then add the dependancy:
```xml
	<dependency>
	    <groupId>com.github.Cyber-cp</groupId>
	    <artifactId>CheckboxQuestions</artifactId>
	    <version>v1.2.0</version>
	</dependency>
```



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

## Settings Builder

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

## List of questions

Now we want to create our list of questions. There are 2 ways to do this. If you want simple yes or no questions, just create a String array full of questions.

```java
String[] string = new String[]{"Is 9+2 = 11?", "Are you happy?", "Did you eat breakfast?"};
```

If you want to use multiple choice questions, create an ArrayList of [Questions](#question).

```java
ArrayList<Question> list = new ArrayList<>();

list.add(new Question("How are you?", 0, "Good", "Bad"));
```
(You can add as many questions as you like)

## Creating the QuestionList

Next we want to create our Question List using the settings you created and the list/array you created.

```java
QuestionList questionList = new QuestionList(list, questionListSettings, context);
```

## Getting the questions and diplaying them

Now if you want to create the views, just call `createQuestionViews()`.

```java
questionList.createQuestionViews();
```

To add the views to your layout, you can use:
```java
linearLayout.addView(questionList.getQuestionViews());
``` 

## All methods

### `createQuestionViews()`
Generates all the question views. They are stored in a LinearLayout.

### `getQuestionViews()`
Returns a LinearLayout full of question views. 

### `areAllQuestionsAnswered()`
Returns a boolean that is true if all the questions are answered, and false if not all are answered.

### `getPercentageOfCorrectAnswers()`
Returns a decimal value of how many answers are correct.

### `getAnswers()`
Returns an Integer ArrayList filled with the selected answers. (1 for option 1, 2 for option 2, 3 for option 3, and 4 for option 4).

# YesOrNoQuestions
YesOrNoQuestions are a simple form of question which show a question with a number, and only allow a yes or no as an answer, while MultipleChoiceQuestions allow [anything](##Options) as an option. To use it in an XML layout just use the following code:
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
```xml
<com.aadyad.checkboxquestion.MultipleChoiceQuestion
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:question_title="What is the slope-intercept equation of a line?"
        app:question_number="10"
        app:number_enabled="true"
        app:option_text_size="20"
        app:question_text_size="25"
        app:spacing_between_boxes="30"
        app:checkbox_orientation="full_vertical"
        app:option_1="x = ym + b"
        app:option_2="y = mx + b"
        app:option_3="y = mb * x"
        app:option_4="b = my / x"/>
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

## Options
The `option_1` attribute lets you set the text for option 1.
The `option_2` attribute lets you set the text for option 2.
The `option_3` attribute lets you set the text for option 3.
The `option_4` attribute lets you set the text for option 4.

# Questions
Questions are an object that allow you to make a QuestionList full of Multiple Choice Questions.
There are 2 constructors for the Question object, which means there are 2 ways to define your Question object

One way:
```java
Question q = new Question("What is the slope intercept equation of a line?", 2, "x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y");
```
The first argument that was passed is the question, the third arg is a String array full of possible answers, and the second arg is the index (this index STARTS at 1, NOT 0) of the correct answer in the array.

The third arg can also be written as:
```java
new String[]{"x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y"}
```

Another way to create a Question is:
```java
Question q = new Question("What is the slope intercept equation of a line?", "y = mx + b", "x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y");
```
In this example, the second arg is a string of the correct answer. Everything else stays the same.

If there is no answer, the second arg can be set to Question.NO_ANSWER (or 0).
