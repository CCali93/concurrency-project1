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
    private Queue<Question<T>> questionsAsked;

    public Leader(String name, CountDownLatch arriveAtWork, StatsGatherer logger) {
        super(name, arriveAtWork, logger);
        questionsAsked = new LinkedBlockingQueue<Question<T>>();
    }

    public void requestAnswerForQuestion(T asker) {
        this.questionsAsked.offer(new Question(TimeTracker.getCurrentTime(), asker));
    }

    protected boolean hasQuestionsToAnswer() {
        return !questionsAsked.isEmpty();
    }

    protected Question<T> answerQuestion() {
        return this.questionsAsked.poll();
    }
}
