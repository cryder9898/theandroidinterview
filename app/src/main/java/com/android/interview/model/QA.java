package com.android.interview.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class QA {

    private String question;
    private String answer;
    private String url;
    private boolean favorite;
    private String username;
    private String uid;
    private HashMap<String, Object> timestampLastChanged;
    private List<Boolean> categories;

    public QA(){}

    public QA(String uid, String question, String answer, String url) {
        this.uid = uid;
        this.question = question;
        this.answer = answer;
        this.url = url;
        this.favorite = false;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUid() {
        return uid;
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
