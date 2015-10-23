package edu.se342;

import java.util.concurrent.BrokenBarrierException;

<<<<<<< HEAD
=======
import java.sql.Time;
import java.util.List;
>>>>>>> Create workflow for TeamLead
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 10/16/15.
 */
public class TeamLead extends Leader<Developer> {
    private CountDownLatch teamArrival;
    private Manager manager;
    private MeetingRoom conferenceRoom;
    private List<Developer> myDevs;

    public TeamLead(String leadName, MeetingRoom conferenceRoom, CountDownLatch arriveAtWork) {
        super(leadName, arriveAtWork);
        this.conferenceRoom = conferenceRoom;

        teamArrival = new CountDownLatch(4);
    }

    public void run() {
        super.run();

        int sleepTime = (int) (Math.random() * (TimeHelp.HALF_HOUR.ms() + 1));

        elapseTime(sleepTime);

        System.out.printf("%s has arrived at work\n", getName());

        try {
            manager.arriveForMorningStandup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s is in Morning Standup\n", getName());
        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());
        System.out.printf("%s has left Morning Standup\n", getName());

        teamArrival.countDown();

        while (teamArrival.getCount() > 0) {
            elapseTime(TimeHelp.MINUTE.ms());
        }

        conferenceRoom.reserve(4);
        notifySubordinatesOfMeeting();
        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());
        conferenceRoom.leave();

        performRegularTasks((int)(TimeHelp.HOUR.ms() * 4.5));

        int breakTime = (int)((Math.random() * TimeHelp.HALF_HOUR.ms()) + TimeHelp.HALF_HOUR.ms());
        System.out.printf("%s is taking a lunch break\n", getName());

        elapseTime(breakTime);

        System.out.printf("%s has returned from lunch break\n", getName());

        performRegularTasks((int)(TimeHelp.HOUR.ms() * 8));

        try {
            conferenceRoom.arriveInRoom();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elapseTime(TimeHelp.FIFTEEN_MINUTES.ms());

        int remainingWorkTime = (int)((Math.random() * 45) + 1);

        performRegularTasks(getElapsedTime() + remainingWorkTime);
        System.out.printf("%s has left work for the day\n", getName());
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addDeveloper(Developer dev) {
        this.myDevs.add(dev);
    }

    public void arriveForMeeting() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void performRegularTasks(int timeInMilliseconds) {
        while(getElapsedTime() < timeInMilliseconds) {
            Developer questionAsker = answerQuestion();

            if (questionAsker != null) {
                int probability = (int)((Math.random() * 1) + 1);

                if (probability == 1) {
                    System.out.printf(
                        "%s has answered a question from %s\n",
                        getName(),
                        questionAsker.getName()
                    );
                } else {
                    System.out.printf("%s cannot answer the question, %s asks the Project Manager");
                    manager.requestAnswerForQuestion(this);
                }
            } else {
                System.out.printf("%s is coding and testing\n", getName());
            }

            elapseTime(TimeHelp.MINUTE.ms());
        }
    }
}
