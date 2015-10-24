package edu.se342;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/16/15.
 */
public class Manager extends Leader<TeamLead> {
    private CountDownLatch office;
    private MeetingRoom conferenceRoom;

    private int elapsedTime;

    public Manager(MeetingRoom conferenceRoom, CountDownLatch arriveAtWork) {
        super("", arriveAtWork);
        this.office = new CountDownLatch(3);

        this.conferenceRoom = conferenceRoom;

        this.elapsedTime = 0;
    }

    public void run() {
        super.run();

        System.out.printf("%s: Project Manager has arrived\n", TimeTracker.currentTimeToString());

        System.out.printf("%s: Project Manager is performing administrivia\n", TimeTracker.currentTimeToString());
        try {
            this.office.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: All team leads have arrived. Performing daily stand up\n", TimeTracker.currentTimeToString());
        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 2);

        System.out.printf("%s: Project Manager is in an executive meeting\n", TimeTracker.currentTimeToString());
        elapseTime(TimeHelp.HOUR.ms());
        System.out.printf("%s: Project Manager has left an executive meeting\n", TimeTracker.currentTimeToString());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 4);

        while (hasQuestionsToAnswer()) {
            TeamLead questionAsker = answerQuestion();
            System.out.printf("%s: Project Manager has answered a question from %s\n",
                TimeTracker.currentTimeToString(),
                questionAsker.getName()
            );
        }

        System.out.printf("%s: Project Manager is going out to lunch\n", TimeTracker.currentTimeToString());
        elapseTime(TimeHelp.HOUR.ms());
        System.out.printf("%s: Project Manager is back from lunch\n", TimeTracker.currentTimeToString());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 6);

        System.out.printf("%s: Project Manager is in an executive meeting\n", TimeTracker.currentTimeToString());
        elapseTime(TimeHelp.HOUR.ms());
        System.out.printf("%s: Project Manager has left an executive meeting\n", TimeTracker.currentTimeToString());

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 8);

        System.out.printf("%s: Project Manager heading to the  all hands meeting\n", TimeTracker.currentTimeToString());

        conferenceRoom.reserve();

        try {
            conferenceRoom.arriveInRoom(false);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());
        conferenceRoom.leave();

        performRegularManagerialTasksUntil(TimeHelp.HOUR.ms() * 9);
        System.out.printf("%s: Project Manager has left work\n", TimeTracker.currentTimeToString());
    }

    public void arriveForMorningStandup() throws InterruptedException {
        this.office.countDown();
        this.office.await();
    }

    private void performRegularManagerialTasksUntil(long timeInMilliseconds) {
        boolean stillPerformingAdministrivia = false;

        while(TimeTracker.getCurrentTime() < timeInMilliseconds) {
            TeamLead questionAsker = answerQuestion();

            if (questionAsker != null) {
                System.out.printf("%s: Project Manager has answered a question from %s\n",
                    TimeTracker.currentTimeToString(),
                    questionAsker.getName()
                );
                stillPerformingAdministrivia = false;
            } else if (questionAsker == null && !stillPerformingAdministrivia) {
                System.out.printf("%s: Project Manager is performing administrivia\n", TimeTracker.currentTimeToString());
                stillPerformingAdministrivia = true;
            }

            elapseTime(TimeHelp.MINUTE.ms());
        }
    }
}
