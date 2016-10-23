package com.android.interview.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.android.interview.AddQuestionActivity;
import com.android.interview.FirebaseUtils;
import com.android.interview.MainActivity;
import com.android.interview.R;
import com.android.interview.model.TestQuestions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.android.interview.model.QA;
import com.google.firebase.database.DatabaseReference;

public class QuestionsListFragment extends Fragment implements FABActionInterface{

    private static final String TAG = "QuestionsListFragment";
    private static final String QUESTION_DELETED_EVENT = "question_deleted";
    private static final String QUESTION_ADDED_EVENT = "question_added";

    private FirebaseAnalytics mFirebaseAnalytics;

    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private QAAdapter mQAAdapter;
    private String listType;

    public OnListItemClickListener mCallback;

    public QuestionsListFragment(){}

    public static QuestionsListFragment newInstance(String listType) {
        QuestionsListFragment myFragment = new QuestionsListFragment();
        Bundle args = new Bundle();
        args.putString("type", listType);
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
        listType = getArguments().getString("type");
        Log.d("CHRIS", listType);
        if (listType == MainActivity.UNDER_REVIEW) {
            getActivity().setTitle(getString(R.string.questions_list_fragment_review));
        } else {
            getActivity().setTitle(getString(R.string.app_name));
        }
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageRecyclerView);

        initRecyclerView();

        //TestQuestions.loadReviews();
        //TestQuestions.loadPublished();
        return rootView;
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mQAAdapter = new QAAdapter(QA.class,
                R.layout.item_question,
                QAAdapter.QAHolder.class,
                FirebaseUtils.getBaseRef().child(listType));

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mQAAdapter);

        mQAAdapter.setOnItemClickListener(new QAAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String key = mQAAdapter.getRef(position).getKey();
                mCallback.onQuestionSelected(listType, key);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("CHRIS",String.valueOf(MainActivity.isAdmin));
                if (MainActivity.isAdmin) {
                    String key = mQAAdapter.getRef(position).getKey();
                    String question = mQAAdapter.getItem(position).getQuestion();
                    mQAAdapter.getRef(position).removeValue();
                    Bundle params = new Bundle();
                    params.putString("key", key);
                    params.putString("question", question);
                    FirebaseAnalytics.getInstance(getActivity()).logEvent(QUESTION_DELETED_EVENT, params);
                    Toast.makeText(getActivity(), "Question Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void fabOnClick() {
        Intent intent = new Intent(getActivity(), AddQuestionActivity.class);
        startActivity(intent);
    }

    public interface OnListItemClickListener {
        void onQuestionSelected(String listType, String key);
    }
}
