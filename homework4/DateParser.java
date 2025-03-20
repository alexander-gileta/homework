import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateParser {

    interface DateHandler {
        Optional<LocalDate> handle(String dateString);
    }

    static class DateFormatHandler implements DateHandler {
        private final DateTimeFormatter formatter;

        public DateFormatHandler(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public Optional<LocalDate> handle(String dateString) {
            try {
                return Optional.of(LocalDate.parse(dateString, formatter));
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        }
    }

    static class RelativeDateHandler implements DateHandler {
        @Override
        public Optional<LocalDate> handle(String dateString) {
            LocalDate today = LocalDate.now();
            switch (dateString.toLowerCase()) {
                case "today":
                    return Optional.of(today);
                case "tomorrow":
                    return Optional.of(today.plusDays(1));
                case "yesterday":
                    return Optional.of(today.minusDays(1));
                default:
                    if (dateString.endsWith("days ago")) {
                        String days = dateString.split(" ")[0];
                        return Optional.of(today.minusDays(Long.parseLong(days)));
                    }
                    return Optional.empty();
            }
        }
    }

    public static Optional<LocalDate> parseDate(String string) {
        DateHandler chain = createChain();
        return chain.handle(string);
    }

    private static DateHandler createChain() {
        DateHandler dateFormatHandler1 = new DateFormatHandler(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateHandler dateFormatHandler2 = new DateFormatHandler(DateTimeFormatter.ofPattern("M/d/yyyy"));
        DateHandler dateFormatHandler3 = new DateFormatHandler(DateTimeFormatter.ofPattern("M/d/yy"));
        DateHandler relativeDateHandler = new RelativeDateHandler();

        return (dateString) -> {
            Optional<LocalDate> result = dateFormatHandler1.handle(dateString);
            if (result.isPresent()) return result;

            result = dateFormatHandler2.handle(dateString);
            if (result.isPresent()) return result;

            result = dateFormatHandler3.handle(dateString);
            if (result.isPresent()) return result;

            return relativeDateHandler.handle(dateString);
        };
    }

    public static void main(String[] args) {
        System.out.println(parseDate("2020-10-10"));
        System.out.println(parseDate("2020-12-2"));
        System.out.println(parseDate("1/3/1976"));
        System.out.println(parseDate("1/3/20"));
        System.out.println(parseDate("tomorrow"));
        System.out.println(parseDate("today"));
        System.out.println(parseDate("yesterday"));
        System.out.println(parseDate("1 day ago"));
        System.out.println(parseDate("2234 days ago"));
        System.out.println(parseDate("invalid date"));
    }
}