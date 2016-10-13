package com.google.firebase.codelab.friendlychat.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.model.QA;

/**
 * Created by cwryd on 10/12/2016.
 */

public class QuestionDetailFragment extends Fragment {

    private View rootView;
    private TextView questionET;
    private TextView answerET;
    private TextView urlET;
    private QA mQA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_detail_question, container, false);

        questionET = (TextView) rootView.findViewById(R.id.question_detail_tv);
        answerET = (TextView) rootView.findViewById(R.id.answer_detail_tv);
        urlET = (TextView) rootView.findViewById(R.id.url_detail_tv);
        answerET.setText(mQA.getAnswer());
        questionET.setText(mQA.getQuestion());
        urlET.setText(mQA.getUrl());
        return rootView;
    }

    public void setObjectForView(QA qa) {
        mQA = qa;
    }
}
