import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by curtis on 10/16/15.
 */
public class MeetingRoom {
    private boolean isReserved;
    private CyclicBarrier standupMeeting;
    private CountDownLatch allHandsMeeting;

    public MeetingRoom() {
        isReserved = false;
        standupMeeting = new CyclicBarrier(4);
        allHandsMeeting = new CountDownLatch(13);
    }

    public synchronized void reserve() {
        try {
            while (isReserved) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        standupMeeting.reset();

        isReserved = true;
    }

    public synchronized void leave() {
        isReserved = false;
        notifyAll();
    }

    public void arriveInRoom(boolean arrivingForStandup) throws BrokenBarrierException, InterruptedException {
        if(arrivingForStandup) {
            standupMeeting.await();
        } else {
            allHandsMeeting.countDown();
            allHandsMeeting.await();
        }
    }


}
