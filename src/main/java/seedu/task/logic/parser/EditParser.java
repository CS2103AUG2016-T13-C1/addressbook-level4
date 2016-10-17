package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.util.DateUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.task.TaskDate;

/**
 * Parser class used to parse an edit command
 */
public class EditParser extends Parser{
	private final String DEADLINE_FLAG = "-d";
	private final String EVENT_FLAG = "-e";
	private final Pattern EDIT_ARGS_FORMAT = Pattern.compile("\\s*(?<index>\\d+)\\s*(?<flag>-\\S)\\s*(?<arguments>.*)\\s*");
	private final Pattern DEADLINE_ARG_FORMAT = Pattern.compile("\\s*/e(?<endDate>\\d{2}-\\d{2}-\\d{4})\\s*");
	private final Pattern EVENT_ARG_FORMAT = Pattern.compile("\\s*/s(?<startDate>\\d{2}-\\d{2}-\\d{4})\\s*/e(?<endDate>\\d{2}-\\d{2}-\\d{4})\\s*");
	
	/**
     * Parses arguments in the context of the edit task command.
     * @param args full command args string
     * @return the prepared command
     */
	@Override
	public Command parseCommand(String args) {
		final Matcher matcher = EDIT_ARGS_FORMAT.matcher(args);
		if (!matcher.matches()) {
			return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    		EditCommand.MESSAGE_USAGE));
		}
		
		String indexArg = matcher.group("index").trim();
		String flagArg = matcher.group("flag").trim();
		String arguments = matcher.group("arguments").trim();
		
        Command toReturn = null;
        boolean hasException = false;
        
        try {
        	final int index = tryParseIndex(indexArg);
        	
        	if (isDeadlineFlag(flagArg)) {
        		toReturn = createDeadlineTask(index, arguments);
        	} else if (isEventFlag(flagArg)) {
        		toReturn = createEventTask(index, arguments);
        	} else {
        		throw new IllegalArgumentException();
        	}
        	
        } catch (NullPointerException e) {
        	hasException = true;
        } catch (ParseException e) {
        	hasException = true;
        } catch (IndexOutOfBoundsException e) {
        	hasException = true;
        } catch (IllegalArgumentException e) {
        	hasException = true;
        }
        
        if (hasException) {
        	toReturn = new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    		EditCommand.MESSAGE_USAGE));
        }
        
        return toReturn;
    }
	
	/**
	 * Method used to retrieve the index from a string argument 
	 * @param argIndex
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	private int tryParseIndex(String argIndex) throws NullPointerException, IllegalArgumentException {
        Optional<Integer> index = parseIndex(argIndex);
        if(!index.isPresent()){
            System.out.println("invalid index");
            throw new IllegalArgumentException();
        }
        
        return index.get();
	}
	
	private boolean isDeadlineFlag(String arg) throws NullPointerException {
		return arg.equals(DEADLINE_FLAG);
	}
	
	private boolean isEventFlag(String arg) throws NullPointerException {
		return arg.equals(EVENT_FLAG);
	}
	
	/**
	 * Creates a DeadlineTask given a list of arguments expects args to have the form
	 * "/e00-00-0000"
	 * 
	 * @throws ParseException 
	 * @throws IllegalArgumentException
	 */
	private Command createDeadlineTask(int index, String args) throws IllegalArgumentException, ParseException {
		Matcher dateMatcher = DEADLINE_ARG_FORMAT.matcher(args);
		
		if (!dateMatcher.matches()) {
			throw new IllegalArgumentException();
		}
		
		String endDateTime = dateMatcher.group("endDate");
        Date endDate = DateUtil.parseStringToDate(endDateTime);
        return new EditCommand(index, new TaskDate(endDate));
	}
	
	/**
	 * Creates an EventTask given a list of arguments
	 * @throws ParseException
	 * @throws IllegalArgumentException 
	 */
	private Command createEventTask(int index, String args) throws ParseException, IllegalArgumentException {
		Matcher dateMatcher = EVENT_ARG_FORMAT.matcher(args);
		
		if (!dateMatcher.matches()) {
			throw new IllegalArgumentException();
		}
		
		String startDateTime = dateMatcher.group("startDate");
        String endDateTime = dateMatcher.group("endDate");
        Date startDate = DateUtil.parseStringToDate(startDateTime);
        Date endDate = DateUtil.parseStringToDate(endDateTime);
        return new EditCommand(index, new TaskDate(startDate), new TaskDate(endDate));
	}
}
