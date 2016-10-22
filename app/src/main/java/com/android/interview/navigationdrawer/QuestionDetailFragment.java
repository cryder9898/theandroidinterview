package com.android.interview.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.interview.EditQuestionActivity;
import com.android.interview.FirebaseUtils;
import com.android.interview.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.android.interview.model.QA;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static com.android.interview.MainActivity.isAdmin;

public class QuestionDetailFragment extends Fragment implements FABActionInterface {

    private static final String TAG = "QuestionsDetailFragment";
    private static final String POSITION = "position";

    private View rootView;
    private TextView question;
    private TextView answer;
    private TextView url;
    private TextView timestamp;
    private static String mKey;
    private String listType;

    private AdView mAdView;
    private DatabaseReference ref;
    private ValueEventListener mValueEventListener;

    public QuestionDetailFragment (){}

    public static QuestionDetailFragment newInstance(String listType) {
        QuestionDetailFragment myFragment = new QuestionDetailFragment();
        Bundle args = new Bundle();
        args.putString("type", listType);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_question, container, false);
        listType = getArguments().getString("type");

        //init AdMob
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        question = (TextView) rootView.findViewById(R.id.question_detail_tv);
        answer = (TextView) rootView.findViewById(R.id.answer_detail_tv);
        url = (TextView) rootView.findViewById(R.id.url_detail_tv);
        timestamp = (TextView) rootView.findViewById(R.id.timestamp_detail_tv);

        ref = FirebaseUtils.getBaseRef().child(listType).child(mKey);
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

        return rootView;
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
    public void onDestroyView() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        ref.removeEventListener(mValueEventListener);
        super.onDestroyView();
    }

    public void initFragObject(String key) {
        mKey = key;
    }

    @Override
    public void fabOnClick() {
        if (isAdmin) {
            Intent intent = new Intent(getActivity(), EditQuestionActivity.class);
            intent.putExtra("key", mKey);
            intent.putExtra("type", listType);
            startActivity(intent);
        }
    }
}
