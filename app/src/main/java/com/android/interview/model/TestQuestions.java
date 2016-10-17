package com.android.interview.model;

import com.android.interview.FirebaseUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestQuestions {

    public static QA qa1 = new QA("What is an Activity?",
            "An mCallback represents a single screen with a user interface just like window or frame of Java.Android" +
                    " mCallback is the subclass of ContextThemeWrapper class.",
            "https://www.tutorialspoint.com/android/android_acitivities.htm",
            FirebaseUtils.getCurrentUserId());
    public static QA qa2 = new QA("Write a Java Method that returns the first 100 prime numbers.",
            "for (i = 1; i <= 100; i++)         \n" +
                    "       { \t\t  \t  \n" +
                    "          int counter=0; \t  \n" +
                    "          for(num =i; num>=1; num--)\n" +
                    "\t  {\n" +
                    "             if(i%num==0)\n" +
                    "\t     {\n" +
                    " \t\tcounter = counter + 1;\n" +
                    "\t     }\n" +
                    "\t  }\n" +
                    "\t  if (counter ==2)\n" +
                    "\t  {\n" +
                    "\t     //Appended the Prime number to the String\n" +
                    "\t     primeNumbers = primeNumbers + i + \" \";\n" +
                    "\t  }\t\n" +
                    "       }\t",
            "http://beginnersbook.com/2014/01/java-program-to-display-prime-numbers/",
            FirebaseUtils.getCurrentUserId());

    public static void loadQuestions() {
        FirebaseUtils.getPublishedQuestionsRef().push().setValue(qa1);
        FirebaseUtils.getPublishedQuestionsRef().push().setValue(qa2);
    }
}
