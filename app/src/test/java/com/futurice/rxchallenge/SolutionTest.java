package com.futurice.rxchallenge;

import com.futurice.android.rxchallenge.Solution;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

public class SolutionTest {

    @Test
    public void test_solutionOperatorFindsSimpleSecretSequence() throws Exception {
        TestScheduler sched = new TestScheduler();
        TestSubscriber<String> sub = new TestSubscriber<>();
        PublishSubject<String> o = PublishSubject.create();
        Solution.defineSuccessStream(o, sched).subscribe(sub);

        // send events with simulated time increments
        sched.advanceTimeTo(0, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(50, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(100, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(150, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(200, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(250, TimeUnit.MILLISECONDS);
        o.onNext("A");

        sub.assertNoErrors();
        sub.assertValue("ABBABA");
    }

    @Test
    public void test_solutionOperatorIgnoresSecretSequenceIfNotWithinTimeout() throws Exception {
        TestScheduler sched = new TestScheduler();
        TestSubscriber<String> sub = new TestSubscriber<>();
        PublishSubject<String> o = PublishSubject.create();
        Solution.defineSuccessStream(o, sched).subscribe(sub);

        // send events with simulated time increments
        sched.advanceTimeTo(0, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(50, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(100, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(150, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(200, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(8000, TimeUnit.MILLISECONDS);
        o.onNext("A");

        sub.assertNoErrors();
        sub.assertNoValues();
    }

    @Test
    public void test_solutionOperatorIgnoresWrongSequences() throws Exception {
        TestScheduler sched = new TestScheduler();
        TestSubscriber<String> sub = new TestSubscriber<>();
        PublishSubject<String> o = PublishSubject.create();
        Solution.defineSuccessStream(o, sched).subscribe(sub);

        // send events with simulated time increments
        sched.advanceTimeTo(0, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(50, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(100, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(150, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(200, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(250, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(300, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(350, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(400, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(450, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(500, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(550, TimeUnit.MILLISECONDS);
        o.onNext("B");

        sub.assertNoErrors();
        sub.assertNoValues();
    }

    @Test
    public void test_solutionOperatorFindsSecretSequenceAsASuffix() throws Exception {
        TestScheduler sched = new TestScheduler();
        TestSubscriber<String> sub = new TestSubscriber<>();
        PublishSubject<String> o = PublishSubject.create();
        Solution.defineSuccessStream(o, sched).subscribe(sub);

        // send events with simulated time increments
        sched.advanceTimeTo(0, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(50, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(100, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(150, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(200, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(250, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(300, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(350, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(400, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(450, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(500, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(550, TimeUnit.MILLISECONDS);
        o.onNext("A");

        sub.assertNoErrors();
        sub.assertValue("ABBABA");
    }

    @Test
    public void test_solutionOperatorIgnoresSecretSequenceIfNotWithinTimeoutOfFirstInput()
            throws Exception {
        TestScheduler sched = new TestScheduler();
        TestSubscriber<String> sub = new TestSubscriber<>();
        PublishSubject<String> o = PublishSubject.create();
        Solution.defineSuccessStream(o, sched).subscribe(sub);

        // send events with simulated time increments
        sched.advanceTimeTo(0, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(100, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(200, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(300, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(400, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(4200, TimeUnit.MILLISECONDS);
        o.onNext("A");

        sub.assertNoErrors();
        sub.assertNoValues();
    }

    @Test
    public void test_solutionOperatorFindsSecretSequenceWithinTightTimeout() throws Exception {
        TestScheduler sched = new TestScheduler();
        TestSubscriber<String> sub = new TestSubscriber<>();
        PublishSubject<String> o = PublishSubject.create();
        Solution.defineSuccessStream(o, sched).subscribe(sub);

        // send events with simulated time increments
        sched.advanceTimeTo(0, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(100, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(200, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(300, TimeUnit.MILLISECONDS);
        o.onNext("A");
        sched.advanceTimeTo(400, TimeUnit.MILLISECONDS);
        o.onNext("B");
        sched.advanceTimeTo(3900, TimeUnit.MILLISECONDS);
        o.onNext("A");

        sub.assertNoErrors();
        sub.assertValue("ABBABA");
    }

}
