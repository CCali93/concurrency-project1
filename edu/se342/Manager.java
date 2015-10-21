package edu.se342;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/16/15.
 */
public class Manager extends Thread {
    private CountDownLatch office;
    private List<TeamLead> teamLeaders;

    public Manager() {
        this.office = new CountDownLatch(4);
    }

    public void run() {

    }

    public synchronized CountDownLatch getOffice() {
        return this.office;
    }

    public void setTeamLeaders(List<TeamLead> teamLeaders) {
        this.teamLeaders = teamLeaders;
    }
}
