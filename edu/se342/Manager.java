package edu.se342;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/16/15.
 */
public class Manager extends Leader<TeamLead> {
    private CountDownLatch office;
    private List<TeamLead> teamLeaders;
    private MeetingRoom conferenceRoom;

    private int elapsedTime;

    public Manager(MeetingRoom conferenceRoom, CountDownLatch arriveAtWork) {
        super("", arriveAtWork);
        this.office = new CountDownLatch(4);
        this.teamLeaders = new ArrayList<TeamLead>();

        this.conferenceRoom = conferenceRoom;

        this.elapsedTime = 0;
    }

    public void run() {
        super.run();

        System.out.println("Project Manager has arrived.");

        this.office.countDown();

        while (this.office.getCount() > 0) {
            System.out.println("Project Manager is performing administrivia.");

            elapseTime(TimeHelp.MINUTE.ms());
        }

        System.out.println("All team leads have arrived. Performing daily stand up");
        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 2);

        System.out.println("Project Manager is in an executive meeting");
        elapseTime(TimeHelp.HOUR.ms());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 4);

        while (hasQuestionsToAnswer()) {
            TeamLead questionAsker = answerQuestion();
            System.out.printf("Project Manager has answered a question from %s\n", questionAsker.getName());
        }

        System.out.println("Project Manager is going out to lunch");
        elapseTime(TimeHelp.HOUR.ms());
        System.out.println("Project Manager is back from lunch");

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 6);

        System.out.println("Project Manager is in another executive meeting");
        elapseTime(TimeHelp.HOUR.ms());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 8);

        conferenceRoom.reserve(13);
        notifySubordinatesOfMeeting();

        try {
            conferenceRoom.arriveInRoom();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 9);
        System.out.println("");
    }

    public synchronized void arriveForMorningStandup() throws InterruptedException {
        this.office.countDown();
        this.office.await();
    }

    public void addTeamLead(TeamLead teamLead) {
        this.teamLeaders.add(teamLead);
    }

    private void performRegularManagerialTasksUntil(int timeInMilliseconds) {
        while(this.elapsedTime < timeInMilliseconds) {
            TeamLead questionAsker = answerQuestion();

            if (questionAsker != null) {
                System.out.printf("Project Manager has answered a question from %s\n", questionAsker.getName());
            } else {
                System.out.println("Project Manager is performing administrivia");
            }

            elapseTime(TimeHelp.MINUTE.ms());
        }
    }

    @Override
    public void notifySubordinatesOfMeeting() {
        for(TeamLead teamLead : teamLeaders) {
            teamLead.notifySubordinatesOfMeeting();
        }
    }
}
