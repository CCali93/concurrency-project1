package edu.se342;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Thread {
    private int teamNumber;

    private Manager manager;
    private MeetingRoom conferenceRoom;

    public TeamLead(Manager manager) {
        super();

        this.manager = manager;
    }

    public void run() {
        try {
            manager.getOffice().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
