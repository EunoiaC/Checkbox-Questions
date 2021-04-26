# CheckboxQuestions
CheckboxQuestions is a library that provides with different forms of asking questions. So far there are [YesOrNoQuestions](#YesOrNoQuestions) and [MultipleChoiceQuestions](#MultipleChoiceQuestions). These questions utilize checkboxes to make an elegant looking UI that will fit seamlessly into your app.

#### Table of Contents

<ul>
	<li><a href="#Images">Images</a>
	<ul>
		<li><a href="#XML-demo">XML demo</li></li>
		<li><a href="#QuestionList-demo">QuestionList demo</li></li>
	</ul>
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
    <li><a href="#MultipleAnswerQuestions">MultipleAnswerQuestions</a></li>
    <li><a href="#Questions">Questions</a></li>
</ul>

# Images

## XML demo
<img width="320" alt="demo" src="https://user-images.githubusercontent.com/68039511/115741228-673bad80-a344-11eb-939c-f63d8c4be54c.png">

(Second question number is Two instead of 2 as to showcase you can put anything you want as a number)

## QuestionList demo

https://user-images.githubusercontent.com/68039511/115741726-d87b6080-a344-11eb-911c-4c9279c36e7d.mov

(Above video gets questions from an API)


# Implementation

### The latest stable release is `v1.3.5`

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
    implementation 'com.github.Cyber-cp:CheckboxQuestions:LATEST_RELEASE'
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
	    <version>LATEST_RELEASE</version>
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

Now we want to create our list of questions. There are 2 ways to do this. If you want simple yes or no questionaire, just create a String array full of questions.

```java
String[] string = new String[]{"Is 9+2 = 11?", "Are you happy?", "Did you eat breakfast?"};
```

If you want to make questions with a correct answer, create an ArrayList of [Questions](#question).

```java
ArrayList<Question> list = new ArrayList<>();

list.add(new Question("How are you?", 0, Question.MULTIPLE_CHOICE_QUESTION, "Good", "Bad"));
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
Returns an Object ArrayList filled with the selected answers. You can loop through the arraylist using `for(Object answer : answers)` and you can try to cast each answer to a specific object, like `(int) answer`.

### `getQuestion(int index)`
Returns a view that can either be a MultipleChoiceQuestion or YesOrNoQuestion. You can cast the view to a specific type of question and do any methods you want with it.
```java
((MultipleChoiceQuestion) questionList.getQuestion(1)).setCheckedOption(0);
```
### `addOnValueChangedRunnable(int index, Runnable r)`
Adds a runnable to a question retrieved by the index. 


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

## Methods

### `setCheckedOption(String option)`
Allows you to choose which option is checked using the option text.

### `setCheckedOption(int option)`
Allows you to choose which option is checked using the index of the option (Starts at 1, NOT 0).

### `setCheckboxOrientation(int orientation)`
Sets the orientation of the checkboxes.

### `setQuestionTextSize(float questionTextSize)`
Sets the textsize of the question.

### `setOptionTextSize(float optionTextSize)`
Sets the textsize for the options.

### `getAnswer()`
Returns an int of the selected answer.

### `setQuestion(String question)`
Sets the question text.

### `setQuestionNumber(String number)`
Sets the question number.

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

## Methods

### `setCheckedOption(String option)`
Allows you to choose which option is checked using the option text.

### `setCheckedOption(int option)`
Allows you to choose which option is checked using the index of the option (Starts at 1, NOT 0).

### `doOnValueChanged(Runnable runnable)`
Runs a runnable when a new option is selected.

### `setCheckboxOrientation(int orientation)`
Sets the orientation of the checkboxes.

### `setQuestionTextSize(float questionTextSize)`
Sets the textsize of the question.

### `setOptionTextSize(float optionTextSize)`
Sets the textsize for the options.

### `getAnswer()`
Returns an int of the selected answer.

### `setQuestion(String question)`
Sets the question text.

# MultipleAnswerQuestions
```xml
<com.aadyad.checkboxquestion.MultipleAnswerQuestion
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:question_title="Which equations are equivalent to 90 + 30"
        app:question_number="10"
        app:number_enabled="true"
        app:option_text_size="20"
        app:question_text_size="25"
        app:spacing_between_boxes="30"
        app:checkbox_orientation="full_vertical"
        app:option_1="91 + 29"
        app:option_2="50 + 70"
        app:option_3="100 + 20"
        app:option_4="99 + 890980"/>
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

## Methods

### `setCheckedOption(String option)`
Allows you to choose which option is checked using the option text.

### `setCheckedOption(int option)`
Allows you to choose which option is checked using the index of the option (Starts at 1, NOT 0).

### `doOnValueChanged(Runnable runnable)`
Runs a runnable when a new option is selected.

### `setCheckboxOrientation(int orientation)`
Sets the orientation of the checkboxes.

### `setQuestionTextSize(float questionTextSize)`
Sets the textsize of the question.

### `setOptionTextSize(float optionTextSize)`
Sets the textsize for the options.

### `getAnswer()`
Returns an Integer ArrayList of the selected answer.

### `setQuestion(String question)`
Sets the question text.

# Questions
Questions are an object that allow you to make a QuestionList full of Multiple Choice Questions.
There are 3 constructors for the Question object, which means there are 2 ways to define your Question object

One way:
```java
Question q = new Question("What is the slope intercept equation of a line?", 2, Question.MULTIPLE_CHOICE_QUESTION, "x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y");
```
The first argument that was passed is the question, The third arg is the type of question, the fourth arg is a String array full of possible answers, and the second arg is the index (this index STARTS at 1, NOT 0) of the correct answer in the array. 

The fourth arg can also be written as:
```java
new String[]{"x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y"}
```

Another way to create a Question is:
```java
Question q = new Question("What is the slope intercept equation of a line?", "y = mx + b", Question.MULTIPLE_CHOICE_QUESTION, "x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y");
```

The final way is:
```java
Question q = new Question("What is the slope intercept equation of a line?", new ArrayList<Integer>(Arrays.asList(1, 2, 3)), Question.MULTIPLE_ANSWER_QUESTION, "x = yb + m", "y = mx + b", "m = yx + b", "b = mx + y");
```

In this example, the second arg is an Integer ArrayList of the correct answers. Everything else stays the same.

If there is no answer, the second arg can be set to Question.NO_ANSWER (or 0), or null.
