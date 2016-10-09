package com.google.firebase.codelab.friendlychat.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;
import java.util.HashMap;

public class QA {

    private String question;
    private String answer;
    private String tag;
    private HashMap<String, Object> timestampLastChanged;

    public QA (){}

    public QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
        //this.tag = tag;
    }

    public void setQuestion(String ques) {
        question = ques;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String ans) {
        answer = ans;
    }

    public String getAnswer() {
        return answer;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

   /* public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public long getTimestampLastChangedLong() {
        return (long) timestampLastChanged.get("timestamp");
    }*/
}
