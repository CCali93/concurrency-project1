import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by Curtis on 10/21/2015.
 */
public abstract class Leader<T> extends Person {
    private Queue<T> questionsAsked;

    public Leader(String name, CountDownLatch arriveAtWork) {
        super(name, arriveAtWork);
        questionsAsked = new LinkedBlockingQueue<T>();
    }

    public void requestAnswerForQuestion(T asker) {
        this.questionsAsked.offer(asker);
    }

    protected boolean hasQuestionsToAnswer() {
        return !questionsAsked.isEmpty();
    }

    protected T answerQuestion() {
        return this.questionsAsked.poll();
    }
}
