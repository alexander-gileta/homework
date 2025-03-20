import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ComputerClubAnalytics {

    public static void main(String[] args) {
        String[] sessions = {
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        };
        
        String averageTime = calculateAverageSessionTime(sessions);
        System.out.println(averageTime);
    }

    public static String calculateAverageSessionTime(String[] sessions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        List<Duration> durations = new ArrayList<>();

        for (String session : sessions) {
            String[] times = session.split(" - ");
            LocalDateTime start = LocalDateTime.parse(times[0], formatter);
            LocalDateTime end = LocalDateTime.parse(times[1], formatter);
            durations.add(Duration.between(start, end));
        }

        Duration totalDuration = durations.stream().reduce(Duration.ZERO, Duration::plus);
        long averageSeconds = totalDuration.getSeconds() / durations.size();
        Duration averageDuration = Duration.ofSeconds(averageSeconds);

        long hours = averageDuration.toHours();
        long minutes = averageDuration.toMinutes() % 60;

        return hours + "ч " + minutes + "м";
    }
}