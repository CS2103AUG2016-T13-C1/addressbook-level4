package guitests;

import guitests.guihandles.TaskListHandle;
import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

public class AddCommandTest extends TaskBookGuiTest {

    
    @Test
    //@@author A0161247J
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.report;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.powerpoint;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.report.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear /a");
        
        assertAddSuccess(td.assignment);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        int hours = LocalDateTime.now().getHour();
        

        commandBox.runCommand(td.movie.getAddCommand());
        assertResultMessageAlmost("New task added: watch The Accountant due 2 Aug 2015 at \\d{2}:\\d{2}");
        

        commandBox.runCommand(td.discussion.getAddCommand());
        assertResultMessageAlmost("New task added: group discussion start from 2 Feb 2020 at \\d{2}:\\d{2} to 25 Dec 2023 at \\d{2}:\\d{2}");
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskListHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
