package edu.se342;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 10/18/15.
 */
public class Main {

    public static void main(String[] args) {
        List<TeamLead> leads = new ArrayList<>(); // List of Team Leads
        Manager pm = new Manager(null); //Create Manager

        // Create Team Leaders
        for (int i = 1; i <= 4; i++) {
            TeamLead leader = new TeamLead("TeamLead " + i + 1, null); // TeamLead (Team, EmployeeNumber)
            leader.setManager(pm);
            leads.add(leader);

            pm.addTeamLead(leader);

            // Create developers for each lead
            for (int j = 2; j <= 4; j++) { // Developer number 2 - 4
                Developer dev = new Developer("Developer " + i + j); // Developer (TeamNumber, EmployeeNumber)
                dev.setTeamLead(leader);
                leader.addDeveloper(dev);
            }
        }


    }
}
