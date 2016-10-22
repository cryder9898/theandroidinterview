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

    public static QA qa3 = new QA("Write Java program to print the fibonacci series",
            "Not using recursion:\n" +
            "class FibonacciExample1{  \n" +
                    "public static void main(String args[])  \n" +
                    "{    \n" +
                    " int n1=0,n2=1,n3,i,count=10;    \n" +
                    " System.out.print(n1+\" \"+n2);//printing 0 and 1    \n" +
                    "    \n" +
                    " for(i=2;i<count;++i)//loop starts from 2 because 0 and 1 are already printed    \n" +
                    " {    \n" +
                    "  n3=n1+n2;    \n" +
                    "  System.out.print(\" \"+n3);    \n" +
                    "  n1=n2;    \n" +
                    "  n2=n3;    \n" +
                    " }    \n" +
                    "  \n" +
                    "}}  \n\n" +
                    "Using recursion" +
                    "class FibonacciExample2{  \n" +
                    " static int n1=0,n2=1,n3=0;    \n" +
                    " static void printFibonacci(int count){    \n" +
                    "    if(count>0){    \n" +
                    "         n3 = n1 + n2;    \n" +
                    "         n1 = n2;    \n" +
                    "         n2 = n3;    \n" +
                    "         System.out.print(\" \"+n3);   \n" +
                    "         printFibonacci(count-1);    \n" +
                    "     }    \n" +
                    " }    \n" +
                    " public static void main(String args[]){    \n" +
                    "  int count=10;    \n" +
                    "  System.out.print(n1+\" \"+n2);//printing 0 and 1    \n" +
                    "  printFibonacci(count-2);//n-2 because 2 numbers are already printed   \n" +
                    " }  \n" +
                    "}  ",
            "http://www.javatpoint.com/fibonacci-series-in-java",
            FirebaseUtils.getCurrentUserId());

    public static void loadReviews() {
        FirebaseUtils.getReviewQuestionsRef().push().setValue(qa1);
        FirebaseUtils.getReviewQuestionsRef().push().setValue(qa2);
        FirebaseUtils.getReviewQuestionsRef().push().setValue(qa3);
    }

    public static void loadPublished() {
        FirebaseUtils.getPublishedQuestionsRef().push().setValue(qa1);
        FirebaseUtils.getPublishedQuestionsRef().push().setValue(qa2);
        FirebaseUtils.getPublishedQuestionsRef().push().setValue(qa3);
    }


    public static void main (String... args) {

    }
}
