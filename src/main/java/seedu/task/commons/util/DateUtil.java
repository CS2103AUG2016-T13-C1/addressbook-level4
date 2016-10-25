package seedu.task.commons.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.task.model.task.TaskDate;

/**
 * A class for handling Dates
 * @author Vivian
 *
 */
public class DateUtil {
    
    private static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	/**
     * Parses a String into a LocalDateTime
     * @throws ParseException
     */
    public static LocalDateTime parseStringToLocalDateTime(String strDate) throws DateTimeParseException {
        return LocalDateTime.parse(strDate, localDateTimeFormatter);
    }
    
    /**
     * Formats a LocalDateTime into a string
     */
    public static String formatLocalDateTimeToString(LocalDateTime taskDate) {
        return taskDate.format(localDateTimeFormatter);
    }
    
    /**
     * Formats a LocalDate into a string
     */
    public static String formatLocalDateToString(LocalDate date) {
        return date.format(localDateFormatter);
    }
    
    /**
     * Converts a given TaskDate into a string.
     */
    public static String convertTaskDateToJaxbString(TaskDate taskDate) {
        if (taskDate == null) {
            return "";
        }
        return taskDate.toString();
    }
    
    /**
     * Converts a given string into a TaskDate.
     */
    public static TaskDate convertJaxbStringToTaskDate(String strDate) {
        try {
            LocalDateTime date = parseStringToLocalDateTime(strDate);
            return new TaskDate(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Returns true if a taskDate falls on the same day as the given local date
     */
    public static boolean isEqual(TaskDate taskDate, LocalDate date) {
        if (taskDate == null) {
            return false;
        }
        LocalDate ldOfTaskDate = taskDate.getTaskDate().toLocalDate();
        return ldOfTaskDate.isEqual(date);
    }
    
    /**
     * Returns today's date
     */
    public static LocalDate getToday() {
        LocalDate today = LocalDate.now();
        return today;
    }

}
