package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.interview.model.QA;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class EditQuestionActivity extends AppCompatActivity {

    private static final String TAG = "EditQuestionActivity";
    private static final String QUESTION_PUBLISHED_EVENT = "question published";

    private Toolbar toolbar;
    private EditText editQuestion;
    private EditText editAnswer;
    private EditText editUrl;
    private String mKey;
    private String mType;

    private DatabaseReference mRef;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        toolbar = (Toolbar) findViewById(R.id.edit_question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit question to publish");

        editQuestion = (EditText) findViewById(R.id.edit_question_et);
        editAnswer = (EditText) findViewById(R.id.edit_answer_et);
        editUrl = (EditText) findViewById(R.id.edit_url_et);

        mKey = getIntent().getStringExtra("key");
        mType = getIntent().getStringExtra("type");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QA qa = dataSnapshot.getValue(QA.class);
                editQuestion.setText(qa.getQuestion());
                editAnswer.setText(qa.getAnswer());
                editUrl.setText(qa.getUrl());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef = FirebaseUtils.getBaseRef().child(mType).child(mKey);
        mRef.addValueEventListener(valueEventListener);
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
                // Adds to published
                FirebaseUtils.getPublishedQuestionsRef().push().setValue(qa);
                // Removes from review
                FirebaseUtils.getReviewQuestionsRef().child(mKey).removeValue();
                FirebaseAnalytics.getInstance(this).logEvent(QUESTION_PUBLISHED_EVENT, null);
                Toast.makeText(this,"Question published to all users",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mRef.removeEventListener(valueEventListener);
        super.onDestroy();
    }
}
