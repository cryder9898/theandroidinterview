package com.android.interview.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.interview.EditQuestionActivity;
import com.android.interview.MainActivity;
import com.android.interview.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.android.interview.model.QA;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static com.android.interview.MainActivity.isAdmin;

public class QuestionDetailFragment extends Fragment implements FABActionInterface {

    private static final String POSITION = "position";

    private View rootView;
    private TextView question;
    private TextView answer;
    private TextView url;
    private TextView timestamp;
    private static QA mQA;

    private AdView mAdView;

    public QuestionDetailFragment (){}

    public static QuestionDetailFragment newInstance(int position) {
        QuestionDetailFragment myFragment = new QuestionDetailFragment();
        Bundle args = new Bundle();
        args.putInt(QuestionDetailFragment.POSITION, position);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_question, container, false);

        //init AdMob
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        question = (TextView) rootView.findViewById(R.id.question_detail_tv);
        answer = (TextView) rootView.findViewById(R.id.answer_detail_tv);
        url = (TextView) rootView.findViewById(R.id.url_detail_tv);
        timestamp = (TextView) rootView.findViewById(R.id.timestamp_detail_tv);
        question.setText(mQA.getQuestion());
        answer.setText(mQA.getAnswer());
        url.setText(mQA.getUrl());
        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyyy");
        timestamp.setText(sfd.format(new Date(mQA.getTimestampLastChangedLong())));

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
        super.onDestroyView();
    }

    public void setObjectForView(QA qa) {
        mQA = qa;
    }

    @Override
    public void fabOnClick() {
        if (isAdmin) {
            Intent intent = new Intent(getActivity(), EditQuestionActivity.class);
            startActivity(intent);
        }
    }
}
