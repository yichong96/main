package seedu.knowitall.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import guitests.guihandles.StageHandle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.knowitall.logic.LogicManager;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.storage.CardFolderStorage;
import seedu.knowitall.storage.JsonCardFolderStorage;
import seedu.knowitall.storage.JsonUserPrefsStorage;
import seedu.knowitall.storage.StorageManager;

/**
 * Contains tests for closing of the {@code MainWindow}.
 */
public class MainWindowCloseTest extends GuiUnitTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private MainWindow mainWindow;
    private EmptyMainWindowHandle mainWindowHandle;
    private Stage stage;

    @Before
    public void setUp() throws Exception {
        JsonCardFolderStorage jsonCardFolderStorage = new JsonCardFolderStorage(temporaryFolder.newFile().toPath());
        List<CardFolderStorage> jsonCardFolderStorageList = new ArrayList<>();
        jsonCardFolderStorageList.add(jsonCardFolderStorage);
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storageManager = new StorageManager(jsonCardFolderStorageList, jsonUserPrefsStorage);
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new LogicManager(
                    new ModelManager(this.getClass().getName()), storageManager));
            mainWindowHandle = new EmptyMainWindowHandle(stage);
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void close_menuBarExitButton_allWindowsClosed() {
        mainWindowHandle.clickOnMenuExitButton();
        // The application will exit when all windows are closed.
        assertEquals(Collections.emptyList(), guiRobot.listWindows());
    }

    @Test
    public void close_externalRequest_exitAppRequestEventPosted() {
        mainWindowHandle.clickOnMenuHelpButton();
        assertTrue(HelpWindowHandle.isWindowPresent());
        mainWindowHandle.closeMainWindowExternally();
        // The application will exit when all windows are closed.
        assertEquals(Collections.emptyList(), guiRobot.listWindows());
    }

    /**
     * A handle for an empty {@code MainWindow}. The components in {@code MainWindow} are not initialized.
     */
    private class EmptyMainWindowHandle extends StageHandle {

        private EmptyMainWindowHandle(Stage stage) {
            super(stage);
        }

        /**
         * Closes the {@code MainWindow} by clicking on the menu bar's exit button.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Closes the {@code MainWindow} through an external request {@code MainWindow} (e.g pressing the 'X' button on
         * the {@code MainWindow} or closing the app through the taskbar).
         */
        private void closeMainWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }

        /**
         * Opens the {@code HelpWindow} by clicking on the menu bar's help button.
         */
        private void clickOnMenuHelpButton() {
            guiRobot.clickOn("Help");
            guiRobot.clickOn("F1");
        }
    }
}
