package utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDate dateNow() {
        return LocalDate.now();
    }
    public static LocalDateTime dateTimeNow() {
        Instant now = Instant.now();
        ZonedDateTime vietnam = now.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        return vietnam.toLocalDateTime();
    }

//    public static String toString(Date date, String format){
//        DateFormat dateFormat = new SimpleDateFormat(format);
//
//        return dateFormat.format(date);
//    }

    public static LocalDate changeLocalDateFormat(LocalDate date, String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        String dateStr = date.format(dtf);
        try {
            return LocalDate.parse(dateStr, dtf);
        }catch(Exception e){
            return LocalDate.now();
        }
    }
    public static LocalDate stringToLocalDate(String dateStr, String format){
        LocalDate date = LocalDate.parse(dateStr);
        return changeLocalDateFormat(date, format);
    }
    public static String dateTimeToStringWithFormat(LocalDateTime datetime, String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return datetime.format(dtf);
    }
    public static LocalDateTime stringToLocalDateTime(String dateStr){
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
}
