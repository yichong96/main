package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.Test;

import seedu.knowitall.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_CARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
