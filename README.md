# CheckboxQuestions
CheckboxQuestions is a library that provides with different forms of asking questions. So far there are [YesOrNoQuestions](#YesOrNoQuestions) and [MultipleChoiceQuestions](#MultipleChoiceQuestions) and 

# YesOrNoQuestions
YesOrNoQuestions are a simple form of question which show a question with a number, and only allow a yes or no as an answer, while MultipleChoiceQuestions allow [anything](## Options) as a option. To use it in an XML layout just use the following code:
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
