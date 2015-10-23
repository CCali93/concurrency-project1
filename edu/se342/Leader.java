package edu.se342;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by Curtis on 10/21/2015.
 */
public abstract class Leader<T> extends Thread {
    private Queue<T> questionsAsked;

    public Leader(String name) {
        super(name);
        this.questionsAsked = new LinkedList<T>();
    }

    public void requestAnswerForQuestion(T asker) {
        this.questionsAsked.add(asker);
    }

    public abstract void notifySubordinatesOfMeeting();

    protected boolean hasQuestionsToAnswer() {
        return !questionsAsked.isEmpty();
    }

    protected T answerQuestion() {
        return this.questionsAsked.poll();
    }

}
