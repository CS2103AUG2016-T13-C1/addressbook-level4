package seedu.task.logic.commands;


/**
 * Lists all tasks in the task book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String OPTION_LIST_ALL = "/a";
    public static final String OPTION_LIST_COMPLETE = "/c";
    public static final String OPTION_LIST_PENDING = "/p";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks from the task book.\n"
            + "Parameters: [OPTION]\n"
            + "Example: " + COMMAND_WORD + " or " + COMMAND_WORD + " " + OPTION_LIST_ALL;

    public static final String MESSAGE_LIST_TODAY_SUCCESS = "Listed tasks due today";
    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_LIST_COMPLETE_SUCCESS = "Listed completed tasks";
    public static final String MESSAGE_LIST_PENDING_SUCCESS = "Listed pending tasks";
    public static final String MESSAGE_LIST_FAIL = "Unknown option specified for list command";

    private String option;


    public ListCommand(String option) {
        this.option = option;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        if (option.isEmpty()) {
            // TODO: implement list tasks due today
            return new CommandResult(MESSAGE_LIST_TODAY_SUCCESS);
        }
        
        switch (option) {
        case OPTION_LIST_ALL:
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
        case OPTION_LIST_COMPLETE:

            return new CommandResult(MESSAGE_LIST_COMPLETE_SUCCESS);
        case OPTION_LIST_PENDING:

            return new CommandResult(MESSAGE_LIST_PENDING_SUCCESS);
        default:
            assert false : "Parser should have eliminated invalid options";
        }
        
        return new CommandResult(MESSAGE_LIST_FAIL);
    }
}
