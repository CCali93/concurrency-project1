/**
 * Created by Curtis on 10/25/2015.
 */
public class Question<T> {
    private final long startTime;
    private final T asker;

    public Question(long start, T asker) {
        startTime = start;
        this.asker = asker;
    }

    public long getStartTime() {
        return startTime;
    }

    public T getAsker() {
        return asker;
    }
}
