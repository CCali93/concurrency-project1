package edu.se342;

import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/23/15.
 */
public class Person extends Thread {
    private CountDownLatch arriveAtWork;
    private int elapsedTime;

    public Person(String name, CountDownLatch arriveAtWork) {
        super(name);
        arriveAtWork = arriveAtWork;
        elapsedTime = 0;
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

        elapsedTime += milliseconds;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
