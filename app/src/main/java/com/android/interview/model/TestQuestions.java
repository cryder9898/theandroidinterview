package com.android.interview.model;

import com.android.interview.BaseActivity;
import com.android.interview.FirebaseUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestQuestions {

    public static QA qa1 = new QA(FirebaseUtils.getCurrentUserId(),"What is an Activity?",
            "An mCallback represents a single screen with a user interface just like window or frame of Java.Android" +
                    " mCallback is the subclass of ContextThemeWrapper class.",
            "https://www.tutorialspoint.com/android/android_acitivities.htm");

    public static QA qa2 = new QA(FirebaseUtils.getCurrentUserId(),
            "Write a Java Method that returns the first 100 prime numbers.",
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

    public static QA qa3 = new QA(FirebaseUtils.getCurrentUserId(),
            "Write Java program to print the fibonacci series",
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
            "http://www.javatpoint.com/fibonacci-series-in-java");

    public static QA qa4 = new QA(FirebaseUtils.getCurrentUserId(),
            "What is a Java interface?",
            "A Java interface is a bit like a class, except a Java interface can only contain method signatures and fields. " +
                    "A Java interface cannot contain an implementation of the methods, only the signature (name, parameters and exceptions) of the method. " +
             "You can use interfaces in Java as a way to achieve polymorphism.",
            "http://tutorials.jenkov.com/java/interfaces.html");

    public static QA qa5 = new QA(FirebaseUtils.getCurrentUserId(),
            "How does a HashMap work?",
            "A HashMap stores data into multiple (array of) singly linked lists of entries (also called buckets or bins). "+
            "All the lists are registered in an array of Entry (Entry<K,V>[] array) and the default capacity"+
            "of this inner array is 16. In Java 8, the HashMap is implemented using TreeNodes if the size of the LinkedList grows"+
            "more then 6 Nodes. When using a HashMap, you need to find a hash function for your keys that spreads the keys into the most possible buckets. "+
            "To do so, you need to avoid hash collisions. The String Object is a good key because of it has good hash function. Integers are also good " +
            "because their hashcode is their own value.",
            "http://coding-geek.com/how-does-a-hashmap-work-in-java/");

    public static QA qa6 = new QA(FirebaseUtils.getCurrentUserId(),
            "Write a program to find the GCD (Greatest Common Divisor)",
            "private static long gcd(long a, long b)\n" +
                    "{\n" +
                    "    while (b > 0)\n" +
                    "    {\n" +
                    "        long temp = b;\n" +
                    "        b = a % b; // % is remainder\n" +
                    "        a = temp;\n" +
                    "    }\n" +
                    "    return a;\n" +
                    "}\n" +
                    "\n" +
                    "private static long gcd(long[] input)\n" +
                    "{\n" +
                    "    long result = input[0];\n" +
                    "    for(int i = 1; i < input.length; i++) result = gcd(result, input[i]);\n" +
                    "    return result;\n" +
                    "}",
            "http://stackoverflow.com/questions/4201860/how-to-find-gcd-lcm-on-a-set-of-numbers");

    public static QA qa7 = new QA(FirebaseUtils.getCurrentUserId(),
            "What are the parts that make up an Android App?",
            "An Android App consists of Activities, Services, Content Providers and Broadcast Receivers. " +
            "There is also the manifest file that declares the components to the Android system and there is " +
            "a res folder where all resources are located. Messages are sent to different components in an Intent" +
            "object. The Intent object defines a message to activate either a specific component or a specific " +
            "type of component",
            "https://developer.android.com/guide/components/fundamentals.html");

    public static QA qa8 = new QA(FirebaseUtils.getCurrentUserId(),
            "Detect a cycle in a linked list. Note that the head pointer may be 'null' if the list is empty.",
            "/*\n" +
                    "Detect a cycle in a linked list. Note that the head pointer may be 'null' if the list is empty.\n" +
                    "\n" +
                    "A Node is defined as: \n" +
                    "    class Node {\n" +
                    "        int data;\n" +
                    "        Node next;\n" +
                    "    }\n" +
                    "*/\n" +
                    "\n" +
                    "boolean hasCycle(Node head) {\n" +
                    "    Set<Node> seen = new HashSet<>();\n" +
                    "    while (head != null) {\n" +
                    "        seen.add(head);\n" +
                    "        head = head.next;\n" +
                    "        if (seen.contains(head)) return true;\n" +
                    "    }\n" +
                    "    return false;\n" +
                    "}\n",
            "https://www.hackerrank.com/challenges/ctci-linked-list-cycle");

    public static void loadReviews() {
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.ANDROID).push().setValue(qa1);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.CODING).push().setValue(qa2);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.CODING).push().setValue(qa3);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.JAVA).push().setValue(qa4);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.JAVA).push().setValue(qa5);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.CODING).push().setValue(qa6);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.ANDROID).push().setValue(qa7);
        FirebaseUtils.getReviewQuestionsRef().child(BaseActivity.CODING).push().setValue(qa8);

    }

    public static void loadPublished() {
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.ANDROID).push().setValue(qa1);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.CODING).push().setValue(qa2);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.CODING).push().setValue(qa3);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.JAVA).push().setValue(qa4);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.JAVA).push().setValue(qa5);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.CODING).push().setValue(qa6);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.ANDROID).push().setValue(qa7);
        FirebaseUtils.getPublishedQuestionsRef().child(BaseActivity.CODING).push().setValue(qa8);
    }
}
