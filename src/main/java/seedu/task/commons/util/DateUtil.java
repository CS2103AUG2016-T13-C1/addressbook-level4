package seedu.task.commons.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;

import seedu.task.model.task.TaskDate;

//@@author A0138704E
/**
 * A class for handling Dates
 */
public class DateUtil {
    
    public static final DateTimeFormatter localDateReadableFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");
    public static final DateTimeFormatter localTimeReadableFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //@@author A0161247J
    private static final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
    
    //@@author A0161247J
    public static boolean isValidDate(String date) {
        try {
            parseStringToLocalDateTime(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    
    
	/**
     * Parses a String into a LocalDateTime
     * @throws ParseException
     * @throws IndexOutOfBoundsException
     */
    //@@author A0161247J
    public static LocalDateTime parseStringToLocalDateTime(String strDate) throws DateTimeParseException, IndexOutOfBoundsException {
    	return parseDateString(strDate).get(0);
    }
    
    /**
     * Parses a String into a LocalDate
     * @throws ParseException
     */
    public static LocalDate parseStringToLocalDate(String strDate) throws DateTimeParseException {
    	return LocalDate.parse(strDate, localDateFormatter);
    }
    
    /**
     * Parses a String into a LocalDateTime with a specified time
     * @throws ParseException
     */
    public static LocalDateTime parseStringToLocalDateTimeWithSpecifiedTime(String strDate, String time) 
            throws DateTimeParseException {
        return parseStringToLocalDateTime(strDate + " " + time);
    }
    
    /**
     * Formats a LocalDateTime into a string
     */
    public static String formatLocalDateTimeToString(LocalDateTime date) {
        return date.format(localDateTimeFormatter);
    }
    
    public static String formatLocalDateTimeToReadableString(LocalDateTime date) {
        String dateString = date.format(localDateReadableFormatter);
        String timeString = date.format(localTimeReadableFormatter);

        return String.format("%s at %s", dateString, timeString);
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
     * Returns today's date as a LocalDate
     */
    public static LocalDate getTodayAsLocalDate() {
        LocalDate today = LocalDate.now();
        return today;
    }
    
    /**
     * Returns today's date as a LocalDateTime
     */
    public static LocalDateTime getTodayAsLocalDateTime() {
        LocalDateTime today = LocalDateTime.now();
        return today;
    }

    /**
     * Retrieves task dates from string using the Natty parser
     */
    //@@author A0161247J
    private static List<LocalDateTime> parseDateString(String args) throws DateTimeParseException {
        List<DateGroup> dateGroups = parser.parse(args);
        if (dateGroups.size() == 0) {
            throw new DateTimeParseException("Could not parse the string", args, 0);
        }
        
        DateGroup group = dateGroups.get(0);
        return extractLocalDates(group);
    }
    
    /**
     * Extracts the local dates as a list of LocalDateTime from a given DateGroup object
     */
    //@@author A0161247J
    private static List<LocalDateTime> extractLocalDates(DateGroup dateGroup) {
        List<Date> dates = dateGroup.getDates();
        
        List<LocalDateTime> localDates = new ArrayList<>();
        for (Date date : dates) {
            LocalDateTime local = LocalDateTime
                    .ofInstant(date.toInstant(), ZoneId.systemDefault());
            localDates.add(local);
        }
        return localDates;
    }
}
