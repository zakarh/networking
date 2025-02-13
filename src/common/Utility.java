package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String getTimestamp() {
        return "[" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + "]";
    }
}
