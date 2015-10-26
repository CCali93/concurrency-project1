/**
 * Created by austin on 10/23/15.
 */
public class TimeTracker {
    private static long startTime; // System time to represent 8am
    private long firstStart; // Time that the first meeting of the day has started
    private long lastStart; // Time that the last meeting of the day has started


    /**
     * Gets the number of milliseconds since the start of the day
     *
     * @return time since the start of the day in milliseconds
     */
    public static synchronized long getCurrentTime() {
        return (System.currentTimeMillis() - startTime);
    }

    /**
     * Return the string value of a time, given how many milliseconds it is into the day
     *
     * @param time Milliseconds since start of day
     * @return string representation of time
     */
    public synchronized static String timeToString(long time) {
        String timePeriod; // Morning or night (AM/PM)
        String hourString; // Hours as a string
        String minuteString; // Minutes as a string

        long hour = ((time / TimeHelp.HOUR.ms()) + 8); // Hours passed since 8am start time

        if (hour > 11) { // Past noon
            timePeriod = ("pm");
        } else { // Before noon
            timePeriod = ("am");
        }

        if (hour > 12) {
            hour -= 12; // Keep hour in 12 hour format (1:00pm instead of 13:00)
        }

        hourString = Long.toString(hour);

        int minutes = (int) ((time % TimeHelp.HOUR.ms()) / TimeHelp.MINUTE.ms()); // Strip hours, get minutes
        minuteString = String.format("%02d", minutes);

        return (hourString + ":" + minuteString + "" + timePeriod); // hour:minutes am/pm
    }

    public synchronized static String currentTimeToString() {
        return timeToString(getCurrentTime());
    }

    /**
     * Sets start time when simulation begins
     */
    public void startDay() {
        startTime = System.currentTimeMillis();
    }


}
