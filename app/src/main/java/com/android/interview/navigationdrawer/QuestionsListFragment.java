package com.android.interview.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.interview.R;
import com.android.interview.model.TestQuestions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.android.interview.model.QA;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionsListFragment extends Fragment {

    private static final String TAG = "QuestionsListFragment";
    private static final String QUESTIONS_CHILD = "questions";
    private static final String QUESTION_DELETED_EVENT = "question_deleted";
    private static final String QUESTION_ADDED_EVENT = "question_added";

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAnalytics mFirebaseAnalytics;

    private View rootView;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private QAAdapter mFirebaseAdapter;

    private boolean isPublished = true;

    public OnListItemClickListener activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (OnListItemClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_questions, container, false);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mMessageRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageRecyclerView);

        initRecyclerView();
//        TestQuestions tq = new TestQuestions();
//        tq.loadQuestions();
        FirebaseAnalytics.getInstance(getContext()).logEvent(QUESTION_ADDED_EVENT, null);
        return rootView;
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseAdapter = new QAAdapter(QA.class,
                R.layout.item_question,
                QAAdapter.QAHolder.class,
                mFirebaseDatabaseReference.child(QA.PUBLISHED));

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
        mFirebaseAdapter.setOnItemClickListener(new QAAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
                activity.setDetails(mFirebaseAdapter.getItem(position));
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemLongClick pos = " + position);
                mFirebaseAdapter.getRef(position).removeValue();
                mFirebaseAnalytics.logEvent(QUESTION_DELETED_EVENT, null);
                Toast.makeText(getContext(),"Question Deleted",Toast.LENGTH_SHORT).show();

            }
        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnListItemClickListener {
        void setDetails(QA qa);
    }
}
