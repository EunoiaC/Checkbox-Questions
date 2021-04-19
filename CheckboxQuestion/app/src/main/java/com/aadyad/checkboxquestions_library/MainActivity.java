package com.aadyad.checkboxquestions_library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.aadyad.checkboxquestion.Question;
import com.aadyad.checkboxquestion.QuestionList;
import com.aadyad.checkboxquestion.QuestionListSettings;

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
                .url("https://opentdb.com/api.php?amount=20&category=9&difficulty=easy&type=multiple")
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
                        correctAnswer = obj.getString("correct_answer");
                        JSONArray jsonArray = obj.getJSONArray("incorrect_answers");
                        answers = new String[]{Jsoup.parse((String) jsonArray.get(0)).text(), Jsoup.parse((String) jsonArray.get(1)).text(), Jsoup.parse((String) jsonArray.get(2)).text(), Jsoup.parse(correctAnswer).text()};
                        Collections.shuffle(Arrays.asList(answers));
                        Log.d("TAG", "shuffled: " + Arrays.toString(answers));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(new Question(question, correctAnswer, answers));
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        QuestionListSettings questionListSettings = new QuestionListSettings.SettingsBuilder()
                                .setCheckboxLocation(Question.LEFT)
                                .setCheckBoxOrientation(Question.SPLIT_VERTICAL)
                                .setNumberEnabled(false)
                                .setOptionTextSize(20)
                                .setQuestionTextSize(24)
                                .setSpacing(15)
                                .create();

                        questionList = new QuestionList(list, questionListSettings, getApplicationContext());
                        questionList.createQuestionViews();

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
        for (int i : questionList.getAnswers()) {
            s += i;
        }

        Log.d("answers", "logAnswers: " + s);
    }
}