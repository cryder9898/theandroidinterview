package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.interview.model.QA;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class QuestionDetailActivity extends BaseActivity {

    private static final String TAG = "QuestionDetailActivity";
    private static final String POSITION = "position";

    private Toolbar toolbar;
    private TextView question;
    private TextView answer;
    private TextView url;
    private TextView timestamp;
    private String mKey;
    private String mCategory;


    private AdView mAdView;
    private DatabaseReference ref;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        toolbar = (Toolbar) findViewById(R.id.detail_question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mKey = getIntent().getStringExtra("key");
        mCategory = getIntent().getStringExtra("category");
        Log.d(TAG,mKey);
        Log.d(TAG,getListType());

        //init AdMob
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        question = (TextView) findViewById(R.id.question_detail_tv);
        answer = (TextView) findViewById(R.id.answer_detail_tv);
        url = (TextView) findViewById(R.id.url_detail_tv);
        timestamp = (TextView) findViewById(R.id.timestamp_detail_tv);
        ref = FirebaseUtils.getBaseRef().child(BaseActivity.getListType()).child(mCategory).child(mKey);
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QA qa = dataSnapshot.getValue(QA.class);
                question.setText(qa.getQuestion());
                answer.setText(qa.getAnswer());
                url.setText(qa.getUrl());
                SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyyy");
                timestamp.setText(sfd.format(new Date(qa.getTimestampLastChangedLong())));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(mValueEventListener);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        ref.removeEventListener(mValueEventListener);
        super.onDestroy();
    }
}
