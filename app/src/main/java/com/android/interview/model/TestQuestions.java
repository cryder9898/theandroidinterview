package com.android.interview.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by cwryd on 10/12/2016.
 */

public class TestQuestions {

    public final QA qa1 = new QA("What is an Activity?",
            "An activity represents a single screen with a user interface just like window or frame of Java.Android" +
                    " activity is the subclass of ContextThemeWrapper class.",
            "https://www.tutorialspoint.com/android/android_acitivities.htm");
    public final QA qa2 = new QA("Write a Java Method that returns the first 100 prime numbers.",
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
            "http://beginnersbook.com/2014/01/java-program-to-display-prime-numbers/");

    public void loadQuestions() {
        DatabaseReference fd = FirebaseDatabase.getInstance().getReference();
        fd.child("questions").push().setValue(qa1);
        fd.child("questions").push().setValue(qa2);
    }
}
