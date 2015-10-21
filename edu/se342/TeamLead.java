package edu.se342;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Thread {
    private int teamNumber;

    private Manager manager;
    private MeetingRoom conferenceRoom;

    public TeamLead(String manager) {
        super(manager);

    }

    public void run() {
        try {
            manager.getOffice().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addDeveloper(Developer d) {
    }
}
