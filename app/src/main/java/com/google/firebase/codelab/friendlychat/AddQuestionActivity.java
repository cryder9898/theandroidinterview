package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.codelab.friendlychat.model.QA;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {

    private static final String QUESTIONS_CHILD = "questions";
    private static final String MESSAGE_SENT_EVENT = "question added";

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
        toolbar.setTitle("Add New Question");

        addQuestion = (EditText) findViewById(R.id.enter_question_et);
        addAnswer = (EditText) findViewById(R.id.enter_answer_et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_question) {
            QA qa = new QA(addQuestion.getText().toString(), addAnswer.getText().toString());
            DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mFirebaseDatabaseReference.child(QUESTIONS_CHILD).push().setValue(qa);
            addQuestion.setText("");
            addAnswer.setText("");
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            mFirebaseAnalytics.logEvent(MESSAGE_SENT_EVENT, null);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
