import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * Represents a developer for a team
 */
public class Employee extends Person {
    private TeamLead teamLead;
    private MeetingRoom conferenceRoom;

    /**
     * Create a developer
     *
     * @param name - name of this dev
     */
    public Employee(String name, CountDownLatch arriveAtWork, MeetingRoom conferenceRoom) {
        super(name, arriveAtWork);
        this.conferenceRoom = conferenceRoom;
    }


    /**
     * Sets this devs team leader
     *
     * @param leader - Team lead for this dev
     */
    public void setTeamLead(TeamLead leader) {
        this.teamLead = leader;
    }


    /**
     * Simulation for this thread to run through 1 work day
     */
    public void run() {
        Random rand = new Random();

        try {
            super.run();

            // Wait for up to 30 mins
            elapseTime(rand.nextInt(TimeHelp.HALF_HOUR.ms() + 1));

            /*-------ARRIVE AT WORK-------*/
            System.out.printf("%s %s has arrived to the workplace\n", TimeTracker.currentTimeToString(), getName());

            /*--------WAIT UNTIL TEAM MEETING-------*/
            teamLead.arriveForMeeting(); // Wait until meeting
            while(!teamLead.isInConferenceRoom()) {
                elapseTime(TimeHelp.MINUTE.ms());
            }

            conferenceRoom.arriveInRoom(true);
            elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

            System.out.printf("%s %s is leaving Team Meeting\n", TimeTracker.currentTimeToString(), getName());

            /*--------WAIT UNTIL LUNCH BREAK-------*/
            System.out.printf("%s %s is developing until lunch break.\n", TimeTracker.currentTimeToString(), getName());
            // Wait until lunch
            while (TimeTracker.getCurrentTime() < TimeHelp.HOUR.ms() * 4) {
                int probability = rand.nextInt(5);

                if (probability == 1) {
                    askQuestion();
                }

                elapseTime(TimeHelp.MINUTE.ms());
            }

            /*--------LUNCH BREAK-------*/
            System.out.println(TimeTracker.currentTimeToString() + ": " + getName() + " goes on lunch.");

            int breakTime = (int)((Math.random() * TimeHelp.HALF_HOUR.ms()) + TimeHelp.HALF_HOUR.ms());
            elapseTime(breakTime); // Eat lunch for 30-60 minutes

            System.out.println(TimeTracker.currentTimeToString() + ": " + getName() + " finished eating lunch.");

            /*--------WAIT UNTIL 4pm MEETING-------*/
            System.out.printf("%s %s is developing until the all hands meeting.\n", TimeTracker.currentTimeToString(), getName());
            while (TimeTracker.getCurrentTime() < (TimeHelp.HOUR.ms() * 8)) { // If it isn't 4pm yet,\
                int probability = rand.nextInt(100);

                if (probability == 1) {
                    askQuestion();
                }

                Thread.sleep(TimeHelp.MINUTE.ms()); // Wait a minute
            }

            System.out.printf("%s %s is going to the all hands meeting\n", TimeTracker.currentTimeToString(), getName());

            conferenceRoom.arriveInRoom(false);
            elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

            System.out.printf("%s %s has finished last meeting.%n", TimeTracker.currentTimeToString(), getName());

            System.out.printf("%s %s is developing for the rest of the day\n", TimeTracker.currentTimeToString(), getName());
            int remainingWorkTime = (int)((Math.random() * 45) + 1);
            elapseTime(remainingWorkTime);

            System.out.printf("%s %s has left work for the day\n", TimeTracker.currentTimeToString(), getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * This dev asks a question to the Team Lead
     */
    private void askQuestion() {
        System.out.println(TimeTracker.currentTimeToString()
                + " " + getName() + " is asking a question");

        this.teamLead.requestAnswerForQuestion(this);

        System.out.println(TimeTracker.currentTimeToString()
                + " " + getName() + "'s question was answered.");
    }
}
