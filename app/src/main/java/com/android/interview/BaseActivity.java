package com.android.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    public static final String PUBLISHED = "published";
    public static final String UNDER_REVIEW = "under_review";
    private static boolean admin = false;
    private static String listType = PUBLISHED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean a) {
        admin = a;
    }

    public static void setListType(String s) {
        listType = s;
    }

    public static String getListType() {
        return listType;
    }
}
