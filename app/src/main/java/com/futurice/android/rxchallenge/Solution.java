package com.futurice.android.rxchallenge;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class Solution {

    public static final String SECRET_SEQUENCE = "ABBABA";
    // this is the password the user must input
    public static final long SEQUENCE_TIMEOUT = 4000; // this is the timeframe in milliseconds

    /**
     * Given two buttons (A & B) we want to print a "CORRECT!" message if a certain "secret
     * sequence"
     * is clicked within a given timeframe. The "secret sequence" is the combination "ABBABA" that
     * has to be inputted within 4 seconds. In other words, the user must input the password, but
     * must do it quickly.
     *
     * @param inputStream an observable event stream of String, either "A" String or "B" String.
     * @param scheduler   if you need a scheduler, use this one provided.
     * @return an observable event stream of String, that returns the secret sequence AT THE RIGHT
     * time according to the problem specification.
     */
    @NonNull
    public static Observable<String> defineSuccessStream(
            @NonNull final Observable<String> inputStream,
            @NonNull final Scheduler scheduler) {
        // TODO Your code goes here. Return an Observable<String> to solve the problem.
        // You can use the statics SECRET_SEQUENCE and SEQUENCE_TIMEOUT here.
        return inputStream.buffer(SECRET_SEQUENCE.length())
                          .timeout(SEQUENCE_TIMEOUT, TimeUnit.MILLISECONDS,
                                   Observable.<List<String>>empty(), scheduler)
                          .repeat(scheduler)
                          .map(new Func1<List<String>, String>() {
                              @Override
                              public String call(@NonNull final List<String> strings) {
                                  return concatToString(strings);
                              }
                          })
                          .filter(new Func1<String, Boolean>() {
                              @Override
                              public Boolean call(@NonNull final String s) {
                                  return SECRET_SEQUENCE.equals(s);
                              }
                          });
    }

    @NonNull
    private static String concatToString(@NonNull final Collection<String> stringCollection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : stringCollection) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

}
