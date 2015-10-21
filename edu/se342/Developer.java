package edu.se342;

/**
 * Created by curtis on 10/16/15.
 */
public class Developer extends Thread {
    private TeamLead teamLead;

    public Developer(String s) {
            super(s);
    }

    public void run() {

    }

    public void setTeamLead(TeamLead teamLead) {
        this.teamLead = teamLead;
    }
}
