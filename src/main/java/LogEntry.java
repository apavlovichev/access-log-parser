import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ipAddr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String requestLog) {
        String regex = "^([\\d.]+) (\\S+) (\\S+) \\[(.+?)\\] \"(\\S+) (.+?) (HTTP/\\d\\.\\d)\" (\\d{3}) (\\d+) \"([^\"]*)\" \"([^\"]*)\"$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestLog);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Неверный формат лога: " + requestLog);
        }
        try {
        this.ipAddr = matcher.group(1);
        this.time = parseDateTime(matcher.group(4));
        this.method = HttpMethod.valueOf(matcher.group(5));
        this.path = matcher.group(6);
        this.responseCode = Integer.parseInt(matcher.group(8));
        this.responseSize = Integer.parseInt(matcher.group(9));
        this.referer = "-".equals(matcher.group(10)) ? null : matcher.group(10);
        this.userAgent = new UserAgent(matcher.group(11));
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка разбора лога: " + e.getMessage(), e);
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public boolean isBot() {
        return userAgent.isBot(userAgent.toString());
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent.toString();
    }

    public enum HttpMethod {
        GET, POST, PUT, PATCH, DELETE
    }

}
