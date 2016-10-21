package com.android.interview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

@IgnoreExtraProperties
public class QA implements Parcelable{

    public static final String PUBLISHED = "published";
    public static final String UNDER_REVIEW = "under_review";

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

    private QA(Parcel in) {
        question = in.readString();
        answer = in.readString();
        url = in.readString();
        uid = in.readString();
        favorite = (Boolean) in.readValue(null);
        timestampLastChanged = in.readHashMap(null);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(url);
        dest.writeString(uid);
        dest.writeValue(favorite);
        dest.writeMap(timestampLastChanged);
    }

    public static final Parcelable.Creator<QA> CREATOR
            = new Parcelable.Creator<QA>() {

        @Override
        public QA createFromParcel(Parcel in) {
            return new QA(in);
        }

        @Override
        public QA[] newArray(int size) {
            return new QA[size];
        }
    };
}
