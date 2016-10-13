package seedu.task.logic.commands;

import seedu.task.model.TaskBook;

/**
 * Clears the task book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskBook.getEmptyTaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}