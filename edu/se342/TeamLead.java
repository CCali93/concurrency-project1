package edu.se342;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Thread {
    private int teamNumber;

    private Manager manager;
    private MeetingRoom conferenceRoom;
    private List<Developer> developmentTeam;

    public TeamLead(String manager, MeetingRoom conferenceRoom) {
        super(manager);
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
}
