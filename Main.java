import java.util.concurrent.CountDownLatch;

/**
 * Created by austin on 10/18/15.
 */
public class Main {

    public static void main(String[] args) {
        CountDownLatch startSignal = new CountDownLatch(13);
        MeetingRoom conferenceRoom = new MeetingRoom();
        TimeTracker officeClock = new TimeTracker();

        Manager pm = new Manager(conferenceRoom, startSignal); //Create Manager
        pm.start();

        // Create Team Leaders

        for (int i = 1; i <= 3; i++) {
            TeamLead leader = new TeamLead(String.format("TeamLead %d%d", i, 1), conferenceRoom, startSignal); // TeamLead (Team, EmployeeNumber)
            leader.setManager(pm);
            leader.start();

            // Create developers for each lead
            for (int j = 2; j <= 4; j++) { // Developer number 2 - 4
                Employee dev = new Employee(
                    String.format("Developer %d%d", i, j),
                    startSignal,
                    conferenceRoom
                ); // Developer (TeamNumber, EmployeeNumber)
                dev.setTeamLead(leader);
                dev.start();
            }
        }

        try {
            officeClock.startDay();
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}