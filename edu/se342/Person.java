package edu.se342;

import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/23/15.
 */
public class Person extends Thread {
    private CountDownLatch arriveAtWork;

    public Person(String name, CountDownLatch arriveAtWork) {
        super(name);
        this.arriveAtWork = arriveAtWork;
    }

    public void run() {
        arriveAtWork.countDown();

        try {
            arriveAtWork.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void elapseTime(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
