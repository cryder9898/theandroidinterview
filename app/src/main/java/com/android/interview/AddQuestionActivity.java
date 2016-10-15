package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.android.interview.model.QA;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {

    private static final String QUESTION_ADDED_EVENT = "question added";

    Toolbar toolbar;
    EditText addQuestion;
    EditText addAnswer;
    EditText addUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        toolbar = (Toolbar) findViewById(R.id.add_question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.add_question_activity_title);

        addQuestion = (EditText) findViewById(R.id.add_question_et);
        addAnswer = (EditText) findViewById(R.id.add_answer_et);
        addUri = (EditText) findViewById(R.id.add_uri_et);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_question) {
            if ((addQuestion.getText().toString() != "") && (addAnswer.getText().toString() != "")) {
                QA qa = new QA(addQuestion.getText().toString(),
                        addAnswer.getText().toString(),
                        addUri.getText().toString());
                DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mFirebaseDatabaseReference.child(QA.UNDER_REVIEW).push().setValue(qa);
                addQuestion.setText("");
                addAnswer.setText("");
                FirebaseAnalytics.getInstance(this).logEvent(QUESTION_ADDED_EVENT, null);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
