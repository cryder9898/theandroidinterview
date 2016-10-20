package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.android.interview.model.QA;

public class AddQuestionActivity extends AppCompatActivity {

    private static final String QUESTION_ADDED_EVENT = "question added";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.enter_question) {
            if (addQuestion.getText().toString() != "") {
                QA qa = new QA(addQuestion.getText().toString(),
                        addAnswer.getText().toString(),
                        addUrl.getText().toString(),
                        FirebaseUtils.getCurrentUserId());
                FirebaseUtils.getReviewQuestionsRef().push().setValue(qa);
                FirebaseAnalytics.getInstance(this).logEvent(QUESTION_ADDED_EVENT, null);
                Toast.makeText(this,"Question will be reviewed by our Admins",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
