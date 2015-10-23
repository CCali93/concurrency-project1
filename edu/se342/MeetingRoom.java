package edu.se342;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by curtis on 10/16/15.
 */
public class MeetingRoom {
    private boolean isReserved;
    private CyclicBarrier waitingForMeeting;

    public MeetingRoom() {
        isReserved = false;
        waitingForMeeting = new CyclicBarrier(4);
    }

    public synchronized void reserve(int attending) {
        try {
            while (isReserved) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(attending != waitingForMeeting.getParties()) {
            waitingForMeeting = new CyclicBarrier(attending);
        }

        isReserved = true;

        notifyAll();
    }

    public synchronized void leave() {
        isReserved = false;
        notifyAll();
    }

    public synchronized void arriveInRoom() throws BrokenBarrierException, InterruptedException {
        waitingForMeeting.await();
    }


}
