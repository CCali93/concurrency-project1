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
        for (int i = 0; i < 3; i++) {
            TeamLead leader = new TeamLead("TeamLead " + (i + 1) + "1", conferenceRoom, startSignal); // TeamLead (Team, EmployeeNumber)
            leader.setManager(pm);
            // Create developers for each lead
            for (int j = 0; j < 3; j++) {
                // Developer (Team, EmployeeNumber)
                Developer dev = new Developer(String.format("Developer %d%d", (i + 1), (j + 2)));
                dev.setTeamLead(leader);
                leader.addDeveloper(dev);
            }

            pm.addTeamLead(leader);
        }
    }
}
