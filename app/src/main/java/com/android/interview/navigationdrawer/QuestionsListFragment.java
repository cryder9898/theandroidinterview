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

import com.android.interview.FirebaseUtils;
import com.android.interview.MainActivity;
import com.android.interview.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.android.interview.model.QA;
import com.google.firebase.database.DatabaseReference;

public class QuestionsListFragment extends Fragment {

    private static final String TAG = "QuestionsListFragment";
    private static final String QUESTION_DELETED_EVENT = "question_deleted";
    private static final String QUESTION_ADDED_EVENT = "question_added";
    private static DatabaseReference adapterRef = FirebaseUtils.getPublishedQuestionsRef();
    private String type;

    private FirebaseAnalytics mFirebaseAnalytics;

    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private QAAdapter mQAAdapter;

    public OnListItemClickListener mCallback;

    public QuestionsListFragment(){}

    public static QuestionsListFragment newInstance(boolean isPublished) {
        QuestionsListFragment myFragment = new QuestionsListFragment();
        Bundle args = new Bundle();
        args.putBoolean("type", isPublished);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mCallback = (OnListItemClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_questions, container, false);
        if (adapterRef.equals(FirebaseUtils.getReviewQuestionsRef())) {
            getActivity().setTitle(getString(R.string.questions_list_fragment_review));
        } else {
            getActivity().setTitle(getString(R.string.app_name));
        }
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageRecyclerView);

        initRecyclerView();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        mFirebaseAnalytics.logEvent(QUESTION_ADDED_EVENT, null);
        return rootView;
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mQAAdapter = new QAAdapter(QA.class,
                R.layout.item_question,
                QAAdapter.QAHolder.class,
                adapterRef);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mQAAdapter);

        //TestQuestions.loadQuestions();

        mQAAdapter.setOnItemClickListener(new QAAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                mCallback.onQuestionSelected(mQAAdapter.getItem(position));
                //mCallback.onQuestionSelected(position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("CHRIS",String.valueOf(MainActivity.isAdmin));
                if (MainActivity.isAdmin) {
                    mQAAdapter.getRef(position).removeValue();
                    mFirebaseAnalytics.logEvent(QUESTION_DELETED_EVENT, null);
                    Toast.makeText(getContext(), "Question Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setReferenceToAdapter(String s) {
        if (s == QA.PUBLISHED) {
            adapterRef = FirebaseUtils.getPublishedQuestionsRef();
        } else {
            adapterRef = FirebaseUtils.getReviewQuestionsRef();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnListItemClickListener {
        void onQuestionSelected(QA qa);
    }
}
