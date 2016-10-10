package com.google.firebase.codelab.friendlychat.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;
import java.util.HashMap;

@IgnoreExtraProperties
public class QA {

    private String question;
    private String answer;
    private String uri;
    private boolean favorite;
    private HashMap<String, Object> timestampLastChanged;


    public QA(){}

    public QA(String question, String answer, String uri) {
        this.question = question;
        this.answer = answer;
        this.uri = uri;
        favorite = false;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean fav) {
        favorite = fav;
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

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    @Exclude
    public long getTimestampLastChangedLong() {
        return (long) timestampLastChanged.get("timestamp");
    }
}
