package edu.se342;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by austin on 10/18/15.
 */
public class Main {

    public static void main(String[] args) {
        CountDownLatch startSignal = new CountDownLatch(13);
        MeetingRoom conferenceRoom = new MeetingRoom();

        Manager pm = new Manager(conferenceRoom, startSignal); //Create Manager

        // Create Team Leaders

        for (int i = 1; i <= 3; i++) {
            TeamLead leader = new TeamLead(String.format("TeamLead %d%d", i, 1), conferenceRoom, startSignal); // TeamLead (Team, EmployeeNumber)
            leader.setManager(pm);
            pm.addTeamLead(leader);

            // Create developers for each lead
            for (int j = 2; j <= 4; j++) { // Developer number 2 - 4
                Developer dev = new Developer(String.format("Developer %d%d", (i + 1), (j + 2))); // Developer (TeamNumber, EmployeeNumber)
                dev.setTeamLead(leader);
                leader.addDeveloper(dev);
            }
        }
    }
}