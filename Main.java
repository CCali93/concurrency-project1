import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by austin on 10/18/15.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Person> employees = new ArrayList<Person>();

        CountDownLatch startSignal = new CountDownLatch(13);
        MeetingRoom conferenceRoom = new MeetingRoom();
        StatsGatherer statsLogger = new StatsGatherer();
        TimeTracker officeClock = new TimeTracker();

        Manager pm = new Manager(conferenceRoom, startSignal, statsLogger); //Create Manager
        //pm.start();
        employees.add(pm);

        // Create Team Leaders

        for (int i = 1; i <= 3; i++) {
            TeamLead leader = new TeamLead(
                String.format("TeamLead %d%d", i, 1),
                conferenceRoom,
                startSignal,
                statsLogger
            );
            leader.setManager(pm);
            employees.add(leader);

            // Create developers for each lead
            for (int j = 2; j <= 4; j++) { // Developer number 2 - 4
                Employee dev = new Employee(
                    String.format("Developer %d%d", i, j),
                    startSignal,
                    conferenceRoom,
                    statsLogger
                ); // Developer (TeamNumber, EmployeeNumber)
                dev.setTeamLead(leader);
                employees.add(dev);
            }
        }

        try {
            for(Person employee : employees) {
                employee.start();
            }

            officeClock.startDay();
            startSignal.await();

            for(Person employee : employees) {
                employee.join();
            }

            statsLogger.printStatsReport();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}