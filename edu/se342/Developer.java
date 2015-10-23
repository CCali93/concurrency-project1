package edu.se342;

import java.util.Random;

/**
 * Represents a developer for a team
 */
public class Developer extends Thread {

    Random rand = new Random();
    private boolean locked = false;
    private long entered; // Time this dev gets to work
    private boolean arrived = false; // True if they have arrived
    private boolean askingQuestion = false; // True if dev is currently asking a question
    private String name;
    private TeamLead teamLead;


    /**
     * Create a developer
     *
     * @param name - name of this dev
     */
    public Developer(String name) {
        this.name = name;
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
        try {
            // Wait for up to 30 mins
            Thread.sleep(rand.nextInt(TimeHelp.HALF_HOUR.ms() + 1));


            /*-------ARRIVE AT WORK-------*/


            this.entered = TimeTracker.getCurrentTime();
            this.arrived = true;

            System.out.printf(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": %s has arrived to the workplace at %d%n", name, entered);


            /*--------WAIT UNTIL TEAM MEETING-------*/


            teamLead.arriveForMeeting(); // Wait until meeting

            System.out.printf(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": %s has arrived for Team Meeting%n", name);


            /*--------WAIT UNTIL TEAM MEETING IS FINISHED-------*/


            while (locked) {
                wait();
            }
            // TODO Team lead will release dev lock when meeting is over
            System.out.printf(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": %s is leaving Team Meeting%n", name);


            /*--------WAIT UNTIL LUNCH BREAK-------*/


            System.out.printf(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": %s is developing until lunch break.%n", name);
            // Wait until lunch
            while (TimeTracker.getCurrentTime() < TimeHelp.HOUR.ms() * 4) {
                synchronized (this) {
                    wait(TimeHelp.MINUTE.ms());
                    // TODO asking a question
                }
            }


            /*--------LUNCH BREAK-------*/


            lock(); // At lunch
            System.out.println(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": " + name + " goes on lunch.");

            Thread.sleep(TimeHelp.MINUTE.ms() * (30 + (int) (Math.random() * 30))); // Eat lunch for 30-60 minutes

            System.out.println(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": " + this.name + " finished eating lunch.");
            unlock(); // Finished lunch


            /*--------WAIT UNTIL 4pm MEETING-------*/


            System.out.printf(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": %s is developing until 4pm meeting.%n", name);
            while (TimeTracker.getCurrentTime() < (TimeHelp.HOUR.ms() * 8)) { // If it isn't 4pm yet,
                synchronized (this) {
                    Thread.sleep(TimeHelp.MINUTE.ms()); // Wait a minute
                    // TODO asking a question
                }
            }


            System.out.printf(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                    + ": %s has finished last meeting.%n", name);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Unlock this thread
     */
    public synchronized void unlock() {
        locked = false;
    }


    /**
     * Lock this thread
     */
    public synchronized void lock() {
        locked = true;
    }


    /**
     * This dev asks a question to the Team Lead
     */
    private void askQuestion() {
        System.out.println(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                + ": " + name + " is asking a question");

        this.askingQuestion = true;
        this.teamLead.requestAnswerForQuestion(this);
        this.askingQuestion = false;

        System.out.println(TimeTracker.timeToString(TimeTracker.getCurrentTime())
                + ": " + name + "'s question was answered.");
    }


    /**
     * Checks if this dev has arrived to work yet
     *
     * @return true if arrived, false otherwise
     */
    public boolean hasArrived() {
        return this.arrived;
    }
}
