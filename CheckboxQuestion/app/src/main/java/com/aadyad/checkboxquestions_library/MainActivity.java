package com.aadyad.checkboxquestions_library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aadyad.checkboxquestion.OnAnswerChangedListener;
import com.aadyad.checkboxquestion.Question;
import com.aadyad.checkboxquestion.QuestionList;
import com.aadyad.checkboxquestion.QuestionListSettings;
import com.aadyad.checkboxquestion.Views.MultipleAnswerQuestion;
import com.aadyad.checkboxquestion.Views.MultipleChoiceQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    QuestionList questionList;
    JSONObject jsonObject;
    OkHttpClient client;
    String jsonData;
    ArrayList<Question> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearLayout = findViewById(R.id.questionLayout);

        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://opentdb.com/api.php?amount=20&category=9&difficulty=medium&type=multiple")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string();
                Log.d("json", "onResponse: " + jsonData);
                try {
                    jsonObject = new JSONObject(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray array = null;
                try {
                    array = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj;
                    String question = null;
                    String[] answers = null;
                    String correctAnswer = null;
                    try {
                        obj = (JSONObject) array.get(i);
                        question = Jsoup.parse(obj.getString("question")).text();
                        correctAnswer = Jsoup.parse(obj.getString("correct_answer")).text();
                        JSONArray jsonArray = obj.getJSONArray("incorrect_answers");
                        answers = new String[]{Jsoup.parse((String) jsonArray.get(0)).text(), Jsoup.parse((String) jsonArray.get(1)).text(), Jsoup.parse((String) jsonArray.get(2)).text(), Jsoup.parse(correctAnswer).text()};
                        Collections.shuffle(Arrays.asList(answers));
                        Log.d("TAG", "shuffled: " + Arrays.toString(answers));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(new Question(question, correctAnswer, Question.MULTIPLE_CHOICE_QUESTION, answers));
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        QuestionListSettings questionListSettings = new QuestionListSettings.SettingsBuilder()
                                .setCheckboxLocation(Question.LEFT)
                                .setCheckBoxOrientation(Question.FULL_VERTICAL)
                                .setNumberEnabled(true)
                                .setOptionTextSize(QuestionListSettings.TEXT_SIZE_DEFAULT)
                                .setQuestionTextSize(QuestionListSettings.TEXT_SIZE_DEFAULT)
                                .setSpacing(15)
                                .create();

                        list.add(new Question("Hi", new ArrayList<Integer>(Arrays.asList(1, 2, 3)), Question.MULTIPLE_ANSWER_QUESTION, "sa", "adasda", "adasd", "sdasd", "sahusaudsia"));
                        list.add(new Question("Yeet?", Question.NO_ANSWER, Question.YES_OR_NO_QUESTION));

                        questionList = new QuestionList(list, questionListSettings, getApplicationContext());
                        questionList.createQuestionViews();

                        for (int i = 0; i < questionList.getQuestionViews().getChildCount(); i++){
                            final int finalI = i;
                            final int finalI1 = i;
                            questionList.addOnAnswerChangedListener(i, new OnAnswerChangedListener() {
                                @Override
                                public void onAnswerChanged(int selectedAnswerIndex, String selectedAnswerText) {
                                    try {
                                        Toast.makeText(MainActivity.this, "" + ((MultipleChoiceQuestion) questionList.getQuestion(finalI1)).isAnswerCorrect(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnswerChanged(ArrayList<Integer> listOfSelectedAnswerIndexes) {
                                    try {
                                        Toast.makeText(MainActivity.this, "" + ((MultipleAnswerQuestion) questionList.getQuestion(finalI1)).isAnswerCorrect(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        linearLayout.addView(questionList.getQuestionViews());
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                Log.e("Error", "onFailure: ", e);
            }
        });
    }

    public void logAnswers(View v) {
        Log.d("Questions answered", "logAnswers: " + questionList.areAllQuestionsAnswered());
        Log.d("Questions answered", "percentage: " + questionList.getPercentageOfCorrectAnswers());
        String s = "";
        for (Object i : questionList.getSelectedAnswers()) {
            try {
                s += (int) i;
            } catch (Exception ignored) {
            }
            try {
                s += (ArrayList<Integer>) i;
            } catch (Exception ignored) {
            }
        }

        Log.d("answers", "logAnswers: " + s);
    }
}