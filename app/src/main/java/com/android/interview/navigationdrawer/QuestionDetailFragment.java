package com.android.interview.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.interview.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.android.interview.model.QA;

/**
 * Created by cwryd on 10/12/2016.
 */

public class QuestionDetailFragment extends Fragment {

    private View rootView;
    private TextView questionET;
    private TextView answerET;
    private TextView urlET;
    private QA mQA;

    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_detail_question, container, false);

        // Initialize and request AdMob ad.
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        questionET = (TextView) rootView.findViewById(R.id.question_detail_tv);
        answerET = (TextView) rootView.findViewById(R.id.answer_detail_tv);
        urlET = (TextView) rootView.findViewById(R.id.url_detail_tv);
        answerET.setText(mQA.getAnswer());
        questionET.setText(mQA.getQuestion());
        urlET.setText(mQA.getUrl());
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
}
