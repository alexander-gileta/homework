import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FridayThe13thFinder {

    public static void main(String[] args) {
        int year = 2024;
        List<LocalDate> fridays13 = findFridays13(year);
        System.out.println(fridays13);
        
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalDate nextFriday13 = findNextFriday13(date);
        System.out.println(nextFriday13);
    }

    public static List<LocalDate> findFridays13(int year) {
        List<LocalDate> fridays13 = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 13);
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays13.add(date);
            }
        }
        return fridays13;
    }

    public static LocalDate findNextFriday13(LocalDate date) {
        LocalDate nextDate = date;
        while (true) {
            nextDate = nextDate.plusDays(1);
            if (nextDate.getDayOfMonth() == 13 && nextDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                return nextDate;
            }
        }
    }
}