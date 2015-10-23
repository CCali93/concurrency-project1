package edu.se342;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Curtis on 10/21/2015.
 */
public abstract class Leader<T> extends Person {
    private Queue<T> questionsAsked;

    public Leader(String name, CountDownLatch arriveAtWork) {
        super(name, arriveAtWork);
        this.questionsAsked = new LinkedBlockingQueue<T>();
    }

    public void requestAnswerForQuestion(T asker) {
        this.questionsAsked.offer(asker);
    }

    public abstract void notifySubordinatesOfMeeting();

    protected boolean hasQuestionsToAnswer() {
        return !questionsAsked.isEmpty();
    }

    protected T answerQuestion() {
        return this.questionsAsked.poll();
    }
}
