/**
 * @author amc9732
 *         Helps convert real time to simulation time
 *         10ms simulation = 1min real time
 */
public enum TimeHelp {

    MINUTE(10),
    FIFTEEN_MINUTES(150),
    HOUR(600),
    HALF_HOUR(300),
    FULL_DAY(4800);

    private final int msTime;

    TimeHelp(int msTime) {
        this.msTime = msTime;
    }

    /**
     * Gets time in ms
     *
     * @return - the time in ms (as an int) that is represented in the firm
     */
    public int ms() {
        return msTime;
    }

}