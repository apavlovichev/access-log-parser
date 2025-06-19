import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry log) {
        this.totalTraffic += log.getResponseSize();
        LocalDateTime logTime = log.getTime();
        if (this.minTime == null || logTime.isBefore(this.minTime)) {
            this.minTime = logTime;
        }
        if (this.maxTime == null || logTime.isAfter(this.maxTime)) {
            this.maxTime = logTime;
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) {
            return 0.0;
        }
        Duration duration = Duration.between(minTime, maxTime);
        double hoursBetween = duration.toSeconds() / 3600.0;
        return (double) totalTraffic / hoursBetween;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
}
