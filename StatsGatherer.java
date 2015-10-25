import java.util.HashMap;
import java.util.Map;

/**
 * Created by Curtis on 10/25/2015.
 */
public class StatsGatherer {
    private Map<String, Long> statistics;

    public StatsGatherer() {
        statistics = new HashMap<String, Long>();

        statistics.put("workingTime", 0L);
        statistics.put("lunchBreakTime", 0L);
        statistics.put("meetingTime", 0L);
        statistics.put("waitingForQuestionTime", 0L);
        statistics.put("numQuestionsAsked", 0L);
    }

    public synchronized void addWorkingTime(long milliseconds) {
        addToKey("workingTime", milliseconds);
    }

    public synchronized void addLunchBreakTime(long milliseconds) {
        addToKey("lunchBreakTime", milliseconds);
    }

    public synchronized void addMeetingTime(long milliseconds) {
        addToKey("meetingTime", milliseconds);
    }

    public synchronized void addWaitingForQuestionTime(long milliseconds) {
        addToKey("waitingForQuestionTime", milliseconds);
        addToKey("numQuestionsAsked", 1);
    }

    private void addToKey(String key, long valueToAdd) {
        long currentValue = statistics.get(key);
        statistics.put(key, currentValue + valueToAdd);
    }

    public void printStatsReport() {
        System.out.println("\n");
        System.out.println("Statistics for this Execution of the Program:");

        System.out.printf(
            "Average Time Spent Working: %d minutes\n",
            (statistics.get("workingTime") / 13) / TimeHelp.MINUTE.ms()
        );

        System.out.printf(
            "Average Time Spent in Meetings: %d minutes\n",
            (statistics.get("meetingTime") / 13) / TimeHelp.MINUTE.ms()
        );

        System.out.printf(
            "Average Time Spent on Lunch Break: %d minutes\n",
            (statistics.get("lunchBreakTime") / 13) / TimeHelp.MINUTE.ms()
        );

        double averageWaitTime =
            (double)(statistics.get("waitingForQuestionTime") / statistics.get("numQuestionsAsked"));

        System.out.printf(
            "Average Time Spent Waiting for a Question to be Answered: %.2f minutes\n",
             averageWaitTime / TimeHelp.MINUTE.ms()
        );
    }
}
