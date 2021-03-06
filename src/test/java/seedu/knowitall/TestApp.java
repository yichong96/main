package seedu.knowitall;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.knowitall.commons.core.Config;
import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.commons.exceptions.DataConversionException;
import seedu.knowitall.logic.LogicManager;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.storage.CardFolderStorage;
import seedu.knowitall.storage.JsonCardFolderStorage;
import seedu.knowitall.storage.JsonUserPrefsStorage;
import seedu.knowitall.storage.StorageManager;
import seedu.knowitall.storage.UserPrefsStorage;
import seedu.knowitall.testutil.TestUtil;
import seedu.knowitall.ui.UiManager;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyCardFolder> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyCardFolder> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonCardFolderStorage jsonCardFolderStorage = new JsonCardFolderStorage(saveFileLocation);
            try {
                jsonCardFolderStorage.saveCardFolder(initialDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
    }

    @Override
    public void init() throws Exception {
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        List<CardFolderStorage> cardFolderStorageList = new ArrayList<>();

        Path cardFolderFilesPath = userPrefs.getcardFolderFilesPath();
        cardFolderStorageList.add(new JsonCardFolderStorage(cardFolderFilesPath));

        storage = new StorageManager(cardFolderStorageList, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setcardFolderFilesPath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the card folder data stored inside the storage file.
     */
    public CardFolder readFirstStorageCardFolder() {
        try {
            List<ReadOnlyCardFolder> folders = new ArrayList<>();
            storage.readCardFolders(folders);
            return new CardFolder(folders.get(0));
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the CardFolder format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        } catch (Exception e) {
            throw new AssertionError("Unknown error encountered.", e);
        }
    }

    /**
     * Returns the file path of the storage files.
     */
    public Path getStorageSaveLocation() {
        return saveFileLocation;
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager(Collections.singletonList(model.getActiveCardFolder()),
                new UserPrefs());
        copy.enterFolder(model.getActiveCardFolderIndex());
        ModelHelper.setFilteredList(copy, model.getFilteredCards());
        return copy;
    }

    /**
     * Makes model enter folder at {@code index}
     */
    public void enterFolder(int index) {
        model.enterFolder(index);
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
