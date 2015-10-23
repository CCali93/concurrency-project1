package edu.se342;

import java.util.List;

import java.util.concurrent.BrokenBarrierException;

import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Leader<Developer> {
    private final CountDownLatch teamArrival;
    private int teamNumber;
    private Manager manager;
    private MeetingRoom conferenceRoom;
    private List<Developer> myDevs;

    public TeamLead(String leadName, MeetingRoom conferenceRoom) {
        super(leadName);
        this.conferenceRoom = conferenceRoom;
        teamArrival = new CountDownLatch(4);

        this.myDevs = new ArrayList<>();
    }

    public void run() {
        try {
            manager.arriveForMorningStandup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addDeveloper(Developer dev) {
        this.myDevs.add(dev);
    }

    public void arriveForMeeting() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

        int remainingWorkTime = (int)((Math.random() * 45) + 1);

        performRegularTasks(getElapsedTime() + remainingWorkTime);
        System.out.printf("%s has left work for the day\n", getName());
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addDeveloper(Developer dev) {
        this.myDevs.add(dev);
    }

    public void arriveForMeeting() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifySubordinatesOfMeeting() {
        for (Developer dev : myDevs) {
            //TODO: tell devs to go to the conference room
        }

        try {
            conferenceRoom.arriveInRoom();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
