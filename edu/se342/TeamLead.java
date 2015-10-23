package edu.se342;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Leader<Developer> {
    private int teamNumber;

    private Manager manager;
    private MeetingRoom conferenceRoom;
    private List<Developer> developmentTeam;

    public TeamLead(String leadName, MeetingRoom conferenceRoom) {
        super(leadName);
        this.conferenceRoom = conferenceRoom;
        developmentTeam = new ArrayList<Developer>();
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

    public void addDeveloper(Developer d) {
        this.developmentTeam.add(d);
    }

    @Override
    public void notifySubordinatesOfMeeting() {
        for (Developer dev : developmentTeam) {
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
