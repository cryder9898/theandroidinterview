package com.android.interview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

@IgnoreExtraProperties
public class QA {

    private String question;
    private String answer;
    private String url;
    private boolean favorite;
    private String username;
    private HashMap<String, Object> timestampLastChanged;
    private String uid;

    public QA(){}

    public QA(String question, String answer, String url, String uid) {
        this.question = question;
        this.answer = answer;
        this.url = url;
        this.uid = uid;
        favorite = false;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
