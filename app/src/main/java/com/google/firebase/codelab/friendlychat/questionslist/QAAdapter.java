package com.google.firebase.codelab.friendlychat.questionslist;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.codelab.friendlychat.model.QA;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.database.DatabaseReference;

public class QAAdapter extends FirebaseRecyclerAdapter<QA, QAAdapter.QAHolder> {

    private static ClickListener clickListener;

    public QAAdapter(Class<QA> modelClass, int modelLayout, Class<QAHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(QAHolder viewHolder, QA model, int position) {
        viewHolder.question.setText(model.getQuestion());
       // viewHolder.timestamp.setText(String.valueOf(model.getTimestampLastChangedLong()));
    }

    public static class QAHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

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
        QAAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}