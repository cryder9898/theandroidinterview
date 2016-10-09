package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class AddQuestionActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText addQuestion;
    EditText addAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        toolbar = (Toolbar) findViewById(R.id.add_question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addQuestion = (EditText) findViewById(R.id.enter_question_et);
        addAnswer = (EditText) findViewById(R.id.enter_answer_et);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
