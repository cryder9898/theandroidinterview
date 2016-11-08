package com.android.interview.navigationdrawer;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.interview.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.android.interview.model.QA;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionListAdapter extends FirebaseRecyclerAdapter<QA, QuestionListAdapter.QAHolder> {

    private static ClickListener clickListener;

    public QuestionListAdapter(Class<QA> modelClass, int modelLayout, Class<QAHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public QuestionListAdapter(Class<QA> modelClass, int modelLayout, Class<QAHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(QAHolder viewHolder, QA model, int position) {
        viewHolder.question.setText(model.getQuestion());
        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyyy");
        viewHolder.timestamp.setText(sfd.format(new Date(model.getTimestampLastChangedLong())));
    }

    public static class QAHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnClickListener {

        private TextView question;
        private TextView timestamp;

        public QAHolder (View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            question = (TextView) itemView.findViewById(R.id.question_tv);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp_tv);

        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        QuestionListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}