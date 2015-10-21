package edu.se342;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/16/15.
 */
public class Manager extends Thread {
    private CountDownLatch office;
    private List<TeamLead> teamLeaders;
    private MeetingRoom conferenceRoom;

    public Manager(MeetingRoom conferenceRoom) {
        super();
        this.office = new CountDownLatch(4);
        this.teamLeaders = new ArrayList<TeamLead>();

        this.conferenceRoom = conferenceRoom;
    }

    public void run() {
        try {
            arriveForMorningStandup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public synchronized void arriveForMorningStandup() throws InterruptedException {
        this.office.await();
    }

    public void addTeamLead(TeamLead teamLead) {
        this.teamLeaders.add(teamLead);
    }
}
