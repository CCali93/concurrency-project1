package edu.se342;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by curtis on 10/16/15.
 */
public class Manager extends Thread {
    private CountDownLatch office;

    public Manager() {
        this.office = new CountDownLatch(4);
    }

    public void run() {

    }

    public synchronized CountDownLatch getOffice() {
        return this.office;
    }
}
