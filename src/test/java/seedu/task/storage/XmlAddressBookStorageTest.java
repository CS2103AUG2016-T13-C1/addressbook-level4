package seedu.task.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.FileUtil;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.task.Task;
import seedu.task.storage.XmlTaskListStorage;
import seedu.task.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlAddressBookStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskList(null);
    }

    private java.util.Optional<ReadOnlyTaskBook> readTaskList(String filePath) throws Exception {
        return new XmlTaskListStorage(filePath).readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskList("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskBook original = td.getTypicalAddressBook();
        XmlTaskListStorage xmlAddressBookStorage = new XmlTaskListStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveTaskList(original, filePath);
        ReadOnlyTaskBook readBack = xmlAddressBookStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.hoon));
        original.removeTask(new Task(TypicalTestTasks.assignment));
        xmlAddressBookStorage.saveTaskList(original, filePath);
        readBack = xmlAddressBookStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.ida));
        xmlAddressBookStorage.saveTaskList(original); //file path not specified
        readBack = xmlAddressBookStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(null, "SomeFile.xml");
    }

    private void saveTaskList(ReadOnlyTaskBook addressBook, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveTaskList(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(new TaskBook(), null);
    }


}
