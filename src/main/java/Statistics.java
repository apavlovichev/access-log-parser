import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> existingSitePage = new HashSet<>();
    private HashSet<String> notFoundPages = new HashSet<>();
    private HashMap<String, Integer> osCounts = new HashMap<>();
    private HashMap<String, Integer> browserCounts = new HashMap<>();

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
        if (log.getResponseCode() == 200) {
            existingSitePage.add(log.getPath());
        } else if (log.getResponseCode() == 404) {
            notFoundPages.add(log.getPath());
        }
        String os = log.getUserAgent().split(",")[0].replace("OS: ", "").trim();
        if (osCounts.containsKey(os)) {
            int currentCount = osCounts.get(os);
            osCounts.put(os, currentCount + 1);
        } else {
            osCounts.put(os, 1);
        }
        String browser = log.getUserAgent().split(",")[1].replace("Browser: ", "").trim();
        if (browserCounts.containsKey(browser)) {
            int currentCount = browserCounts.get(browser);
            browserCounts.put(browser, currentCount + 1);
        } else {
            browserCounts.put(browser, 1);
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

    public HashSet<String> getExistingPages() {
        return existingSitePage;
    }

    public HashSet<String> getNotFoundPages() {
        return new HashSet<>(notFoundPages);
    }

    public HashMap<String, Double> getOsStatistics() {
        HashMap<String, Double> osStats = new HashMap<>();
        int total = 0;

        Iterator<Integer> valueIterator = osCounts.values().iterator();
        while (valueIterator.hasNext()) {
            total += valueIterator.next();
        }

        if (total > 0) {
            Iterator<Map.Entry<String, Integer>> entryIterator = osCounts.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, Integer> entry = entryIterator.next();
                double ratio = (double) entry.getValue() / total;
                osStats.put(entry.getKey(), ratio);
            }
        }
        return osStats;
    }

    public HashMap<String, Double> getBrowserStatistics() {
        HashMap<String, Double> browserStats = new HashMap<>();
        int total = 0;

        Iterator<Integer> valueIterator = browserCounts.values().iterator();
        while (valueIterator.hasNext()) {
            total += valueIterator.next();
        }

        if (total > 0) {
            Iterator<Map.Entry<String, Integer>> entryIterator = browserCounts.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, Integer> entry = entryIterator.next();
                double ratio = (double) entry.getValue() / total;
                browserStats.put(entry.getKey(), ratio);
            }
        }
        return browserStats;
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
