public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgent) {
        if (userAgent == null || userAgent.equals("-")) {
            this.os = "Other";
            this.browser = "Other";
            return;
        }

        String os = "Other";

        if (userAgent.contains("Windows")) {
            os = "Windows";
        } else if (userAgent.contains("Macintosh")) {
            os = "macOS";
        } else if (userAgent.contains("Linux")) {
            os = "Linux";
        }

        String browser = "Other";

        if (userAgent.contains("Edge")) {
            browser = "Edge";
        } else if (userAgent.contains("Firefox")) {
            browser = "Firefox";
        } else if (userAgent.contains("Opera")) {
            browser = "Opera";
        } else if (userAgent.contains("Chrome")) {
            browser = "Chrome";
        }

        this.os = os;
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    @Override
    public String toString() {
        return "OS: " + os + ", Browser: " + browser;
    }
}
