package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.interview.model.QA;
import com.google.firebase.analytics.FirebaseAnalytics;

public class EditQuestionActivity extends AppCompatActivity {

    private static final String QUESTION_EDITED_EVENT = "question edited";

    private Toolbar toolbar;
    private EditText editQuestion;
    private EditText editAnswer;
    private EditText editUrl;
    private QA mQA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        toolbar = (Toolbar) findViewById(R.id.edit_question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Question Here");

        editQuestion = (EditText) findViewById(R.id.edit_question_et);
        editAnswer = (EditText) findViewById(R.id.edit_answer_et);
        editUrl = (EditText) findViewById(R.id.edit_url_et);

        mQA = getIntent().getParcelableExtra("QA");

        editQuestion.setText(mQA.getQuestion());
        editAnswer.setText(mQA.getAnswer());
        editUrl.setText(mQA.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enter_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.enter_question) {
            if (editQuestion.getText().toString() != "") {
                QA qa = new QA(editQuestion.getText().toString(),
                        editAnswer.getText().toString(),
                        editUrl.getText().toString(),
                        FirebaseUtils.getCurrentUserId());
                FirebaseUtils.getReviewQuestionsRef().push().setValue(qa);
                FirebaseAnalytics.getInstance(this).logEvent(QUESTION_EDITED_EVENT, null);
                Toast.makeText(this,"Question Entered",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
