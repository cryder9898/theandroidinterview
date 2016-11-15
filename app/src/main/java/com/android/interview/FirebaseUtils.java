package com.android.interview;

import com.android.interview.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public static User getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return new User(user.getUid(), user.getDisplayName(), user.getEmail());
        }
        return null;
    }

    /*
    public void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        FirebaseUtils.getUsersRef().child(userId).setValue(user);
    }
    */

    public static DatabaseReference getAdminsRef() {
        return getBaseRef().child("administrators");
    }

    public static DatabaseReference getPublishedQuestionsRef() {
        return getBaseRef().child("published");
    }

    public static DatabaseReference getReviewQuestionsRef() {
        return getBaseRef().child("under_review");
    }
    public static DatabaseReference getUsersRef() {
        return getBaseRef().child("users");
    }
}
