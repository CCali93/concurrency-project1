package edu.se342;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.ArrayList;

import java.util.List;
import java.util.ArrayList;
>>>>>>> Create workflow for TeamLead
import java.util.List;

=======
import java.sql.Time;
import java.util.*;

>>>>>>> Workflow for everything except questions is working
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Leader<Developer> {
<<<<<<< HEAD
    private final CountDownLatch teamArrival;
    private CountDownLatch teamArrival;
=======
    private volatile boolean isInConferenceRoom;
>>>>>>> Workflow for everything except questions is working

    private CountDownLatch teamArrival;
    private Manager manager;
    private MeetingRoom conferenceRoom;

    public TeamLead(String leadName, MeetingRoom conferenceRoom, CountDownLatch arriveAtWork) {
        super(leadName, arriveAtWork);
        this.conferenceRoom = conferenceRoom;

<<<<<<< HEAD
        teamArrival = new CountDownLatch(4);

        this.myDevs = new ArrayList<>();
        teamArrival = new CountDownLatch(4);

=======
        teamArrival = new CountDownLatch(3);
        isInConferenceRoom = false;
>>>>>>> Workflow for everything except questions is working
    }

    public void run() {
        super.run();

        int sleepTime = (int) (Math.random() * (TimeHelp.HALF_HOUR.ms() + 1));

        elapseTime(sleepTime);

        System.out.printf("%s: %s has arrived at work\n", TimeTracker.currentTimeToString(), getName());

        try {
            manager.arriveForMorningStandup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: %s is in Morning Standup\n", TimeTracker.currentTimeToString(),getName());
        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());
        System.out.printf("%s: %s has left Morning Standup\n", TimeTracker.currentTimeToString(),getName());

        try {
            teamArrival.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        conferenceRoom.reserve();
        System.out.printf(
                "%s: %s has reserved the conference room for morning meeting\n",
                TimeTracker.currentTimeToString(),
                getName()
        );

        isInConferenceRoom = true;

        try {
            conferenceRoom.arriveInRoom(true);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());
        conferenceRoom.leave();
        isInConferenceRoom = false;

        performRegularTasks((int)(TimeHelp.HOUR.ms() * 4.5));

        int breakTime = (int)((Math.random() * TimeHelp.HALF_HOUR.ms()) + TimeHelp.HALF_HOUR.ms());
        System.out.printf("%s: %s is taking a lunch break\n", TimeTracker.currentTimeToString(), getName());
        elapseTime(breakTime);
        System.out.printf("%s: %s has returned from lunch break\n", TimeTracker.currentTimeToString(), getName());

        performRegularTasks(TimeHelp.HOUR.ms() * 8);

        System.out.printf(
            "%s: %s is going to the daily all hands meetings\n",
            TimeTracker.currentTimeToString(),
            getName()
        );

        try {
            conferenceRoom.arriveInRoom(false);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

        System.out.printf("%s: %s has left the all hands meeting\n", TimeTracker.currentTimeToString(), getName());

        int remainingWorkTime = (int)((Math.random() * 45) + 1);

        performRegularTasks(TimeTracker.getCurrentTime() + remainingWorkTime);
        System.out.printf("%s: %s has left work for the day\n", TimeTracker.currentTimeToString(), getName());
    }

    public synchronized boolean isInConferenceRoom() {
        return isInConferenceRoom;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void notifySubordinatesOfMeeting() {
        for (Developer dev : myDevs) {
            //TODO: tell devs to go to the conference room
        }

        try {
            conferenceRoom.arriveInRoom();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void performRegularTasks(long timeInMilliseconds) {
        boolean stillCodingAndTesting = false;

        while(TimeTracker.getCurrentTime() < timeInMilliseconds) {
            Developer questionAsker = answerQuestion();

            if (questionAsker != null) {
                stillCodingAndTesting = false;

                int probability = (int)((Math.random() * 1) + 1);

                if (probability == 1) {
                    System.out.printf(
                        "%s: %s has answered a question from %s\n",
                        TimeTracker.currentTimeToString(),
                        getName(),
                        questionAsker.getName()
                    );
                } else {
                    System.out.printf("%s: %s cannot answer the question, %s asks the Project Manager\n",
                        TimeTracker.currentTimeToString(),
                        getName(),
                        getName()
                    );
                    manager.requestAnswerForQuestion(this);
                }
            } else if (questionAsker == null && !stillCodingAndTesting) {
                System.out.printf("%s: %s is coding and testing\n", TimeTracker.currentTimeToString(), getName());
                stillCodingAndTesting = true;
            }

            elapseTime(TimeHelp.MINUTE.ms());
        }
    }
}
