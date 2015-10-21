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
        for (int i = 0; i < 3; i++) {
            TeamLead leader = new TeamLead("TeamLead " + (i + 1) + 1, null); // TeamLead (Team, EmployeeNumber)
            leader.setManager(pm);
            leads.add(leader);

            // Create developers for each lead
            for (int j = 0; j < 3; j++) {
                // Developer (Team, EmployeeNumber)
                Developer dev = new Developer("Developer " + (i + 1) + (j + 2));
                dev.setTeamLead(leader);
                leader.addDeveloper(dev);
            }

            pm.addTeamLead(leader);
        }
    }
}
