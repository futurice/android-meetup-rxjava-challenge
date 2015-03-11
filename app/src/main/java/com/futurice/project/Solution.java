package com.futurice.project;

import rx.Observable;
import rx.Scheduler;

public class Solution {

    public static final String SECRET_SEQUENCE = "ABBABA"; // this is the password the user must input
    public static final long SEQUENCE_TIMEOUT = 4000; // this is the timeframe in milliseconds

    /**
     * Given two buttons (A & B) we want to print a "CORRECT!" message if a certain "secret sequence"
     * is clicked within a given timeframe. The "secret sequence" is the combination "ABBABA" that
     * has to be inputted within 4 seconds. In other words, the user must input the password, but
     * must do it quickly.
     *
     * @param inputStream an observable event stream of String, either "A" String or "B" String.
     * @param scheduler if you need a scheduler, use this one provided.
     * @return an observable event stream of String, that returns the secret sequence AT THE RIGHT
     * time according to the problem specification.
     */
    public static Observable<String> defineSuccessStream(Observable<String> inputStream, Scheduler scheduler) {
        // TODO Your code goes here. Return an Observable<String> to solve the problem.
        // You can use the statics SECRET_SEQUENCE and SEQUENCE_TIMEOUT here.
        return Observable.empty(); // TODO delete me and replace with your solution
    }

}
