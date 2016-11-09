package com.android.interview.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.interview.AddQuestionActivity;
import com.android.interview.BaseActivity;
import com.android.interview.FirebaseUtils;
import com.android.interview.PagerAdapter;
import com.android.interview.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.android.interview.model.QA;

public class QuestionsListFragment extends Fragment implements FABAction {

    private static final String TAG = "QuestionsListFragment";
    private static final String QUESTION_DELETED_EVENT = "question_deleted";
    private static final String QUESTION_ADDED_EVENT = "question_added";

    private FirebaseAnalytics mFirebaseAnalytics;

    private View rootView;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private QuestionListAdapter mQLAdapter;
    private String category;


    public OnListItemClickListener mCallback;

    public QuestionsListFragment(){}

    public static QuestionsListFragment newInstance(String categories) {
        QuestionsListFragment myFragment = new QuestionsListFragment();
        Bundle args = new Bundle();
        args.putString("cat", categories);
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
        getActivity().setTitle(getString(R.string.app_name));
        if (BaseActivity.getListType() == BaseActivity.UNDER_REVIEW) {
            getActivity().setTitle(getString(R.string.questions_list_fragment_review));
        }
        category = getArguments().getString("cat");
        Log.d(TAG, category);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mQLAdapter = new QuestionListAdapter(QA.class,
                R.layout.item_question,
                QuestionListAdapter.QAHolder.class,
                FirebaseUtils.getBaseRef().child(BaseActivity.getListType()).child(category));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mQLAdapter);

        mQLAdapter.setOnItemClickListener(new QuestionListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String key = mQLAdapter.getRef(position).getKey();
                mCallback.onQuestionSelected(key);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                if (BaseActivity.isAdmin()) {
                    String key = mQLAdapter.getRef(position).getKey();
                    String question = mQLAdapter.getItem(position).getQuestion();
                    mQLAdapter.getRef(position).removeValue();
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
        mQLAdapter.cleanup();
    }

    @Override
    public void fabOnClick() {
        Intent intent = new Intent(getActivity(), AddQuestionActivity.class);
        startActivity(intent);
    }

    public interface OnListItemClickListener {
        void onQuestionSelected(String key);
    }
}
