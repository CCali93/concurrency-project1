import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/23/15.
 */
public class Person extends Thread {
    private CountDownLatch arriveAtWork;
    private StatsGatherer logger;

    public Person(String name, CountDownLatch arriveAtWork, StatsGatherer logger) {
        super(name);
        this.arriveAtWork = arriveAtWork;
        this.logger = logger;
    }

    public void run() {
        arriveAtWork.countDown();

        try {
            arriveAtWork.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void elapseTime(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logWorkingTime(long milliseconds) {
        logger.addWorkingTime(milliseconds);
    }

    public void logLunchBreakTime(long milliseconds) {
        logger.addLunchBreakTime(milliseconds);
    }

    public void logMeetingTime(long milliseconds) {
        logger.addMeetingTime(milliseconds);
    }

    public void logWaitForQuestionsTime(long milliseconds) {
        logger.addWaitingForQuestionTime(milliseconds);
    }
}
