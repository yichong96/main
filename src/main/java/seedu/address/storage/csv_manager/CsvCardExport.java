package seedu.address.storage.csv_manager;

import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Manages the exporting of flashcard folders
 */
public class CsvCardExport {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Hints";



    private List<ReadOnlyCardFolder> cardFolders;
    private String filename;

    public CsvCardExport(List<ReadOnlyCardFolder> cardFolders, String filename) {
        this.cardFolders = cardFolders;
        this.filename = filename;
    }

    private void writeFoldersToCSV() throws IOException {
        String filepath = getFilePath();
        FileWriter fileWriter = new FileWriter(filepath);

        // get card folder objects
        for (ReadOnlyCardFolder readOnlyCardFolder : cardFolders) {
            List<Card> cardList = readOnlyCardFolder.getCardList();
            String foldername = readOnlyCardFolder.getFolderName();
            fileWriter.append(foldername);
            for (Card card : cardList) {
                String cardRepresentation = getCardString(card);
                fileWriter.append(cardRepresentation);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
    }

    private String getFilePath() throws IOException {
        String defaultFilePath = new File("./").getCanonicalPath();
        return defaultFilePath + "/" + filename;
    }

    private String getCardString(Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(card.getQuestion() + COMMA_DELIMITTER)
                .append(card.getAnswer() + COMMA_DELIMITTER);
        card.getHints().forEach(hint -> stringBuilder.append(hint.hintName)
                .append(COMMA_DELIMITTER));
        return stringBuilder.toString();
    }





}
