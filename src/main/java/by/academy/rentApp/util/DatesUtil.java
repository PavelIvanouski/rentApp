package by.academy.rentApp.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class DatesUtil {

    public static boolean chekDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        long hours = ChronoUnit.HOURS.between(startDate, endDate);
        if (hours < 0 || hours > 168) {
            return false;
        }
        if (startDate.compareTo(LocalDateTime.now()) < 0 ) {
            return false;
        }
        return true;
    }


    public static double returnDifferenceInHours(OffsetDateTime startDate, OffsetDateTime endDate) {
        return Math.round(ChronoUnit.HOURS.between(startDate, endDate)*100)/100.00;
    }
}
