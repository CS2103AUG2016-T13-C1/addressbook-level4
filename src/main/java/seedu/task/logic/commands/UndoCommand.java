package seedu.task.logic.commands;

import seedu.task.model.task.UniqueTaskList;

/**
 * Undo last task applied to our task book
 */
public class UndoCommand extends Command{
	public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo last task applied to our task book. "  
    + "\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Last task undone";
    public static final String MESSAGE_EMPTY = "There is no task to undo";

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.undo();
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (UniqueTaskList.TaskNotFoundException e) {
            return new CommandResult(MESSAGE_EMPTY);
        }

    }
}
