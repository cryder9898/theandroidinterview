package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class EditQuestionActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText addQuestion;
    EditText addAnswer;
    EditText addUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        toolbar = (Toolbar) findViewById(R.id.edit_question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.add_question_activity_title);

        addQuestion = (EditText) findViewById(R.id.add_question_et);
        addAnswer = (EditText) findViewById(R.id.add_answer_et);
        addUrl = (EditText) findViewById(R.id.add_url_et);
    }
}
