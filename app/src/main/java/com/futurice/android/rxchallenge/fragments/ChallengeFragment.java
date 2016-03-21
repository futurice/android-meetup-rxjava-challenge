package com.futurice.android.rxchallenge.fragments;

import com.futurice.rxchallenge.R;
import com.futurice.android.rxchallenge.Solution;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChallengeFragment extends Fragment {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Observable<String> inputStream;
    private Observable<String> sequenceStream;
    private Observable<String> successStream;

    private View buttonA;
    private View buttonB;
    private TextView resultTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputStream = defineInputStream();
        sequenceStream = defineSequenceStream(inputStream);
        successStream = Solution.defineSuccessStream(inputStream, Schedulers.immediate());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonA = getView().findViewById(R.id.buttonA);
        buttonB = getView().findViewById(R.id.buttonB);
        resultTextView = (TextView) getView().findViewById(R.id.result);
    }

    private Observable<String> defineInputStream() {
        Observable<String> inputA = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                buttonA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscriber.onNext("A");
                    }
                });
            }
        }).publish().refCount();
        Observable<String> inputB = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                buttonB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscriber.onNext("B");
                    }
                });
            }
        }).publish().refCount();
        return Observable.merge(inputA, inputB);
    }

    private static Observable<String> defineSequenceStream(Observable<String> inputStream) {
        return inputStream.scan(
                "", new Func2<String, String, String>() {
                    @Override
                    public String call(String s1, String s2) {
                        String concatenation = (s1 + s2);
                        if (concatenation.length() <= 6) {
                            return concatenation;
                        } else {
                            return concatenation.substring(
                                    concatenation.length() - Solution.SECRET_SEQUENCE.length());
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeResultTextView();
    }

    private void subscribeResultTextView() {
        Observable<String> correctStream = successStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return "CORRECT!";
            }
        });
        compositeSubscription.add(
                Observable.merge(sequenceStream, correctStream)
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(new Action1<String>() {
                              @Override
                              public void call(String s) {
                                  resultTextView.setText(s);
                              }
                          }));
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeSubscription.clear();
    }
}
