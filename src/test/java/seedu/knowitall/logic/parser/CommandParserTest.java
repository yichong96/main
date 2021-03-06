package seedu.knowitall.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.knowitall.logic.commands.AddCommand;
import seedu.knowitall.logic.commands.AnswerCommand;
import seedu.knowitall.logic.commands.ClearCommand;
import seedu.knowitall.logic.commands.DeleteCommand;
import seedu.knowitall.logic.commands.EditCommand;
import seedu.knowitall.logic.commands.EditCommand.EditCardDescriptor;
import seedu.knowitall.logic.commands.EndCommand;
import seedu.knowitall.logic.commands.ExitCommand;
import seedu.knowitall.logic.commands.HelpCommand;
import seedu.knowitall.logic.commands.HistoryCommand;
import seedu.knowitall.logic.commands.ListCommand;
import seedu.knowitall.logic.commands.NextCommand;
import seedu.knowitall.logic.commands.RedoCommand;
import seedu.knowitall.logic.commands.RevealCommand;
import seedu.knowitall.logic.commands.SearchCommand;
import seedu.knowitall.logic.commands.SelectCommand;
import seedu.knowitall.logic.commands.TestCommand;
import seedu.knowitall.logic.commands.UndoCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.QuestionContainsKeywordsPredicate;
import seedu.knowitall.testutil.CardBuilder;
import seedu.knowitall.testutil.CardUtil;
import seedu.knowitall.testutil.EditCardDescriptorBuilder;

public class CommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CommandParser parser = new CommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        Card card = new CardBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(CardUtil.getAddCommand(card));
        assertEquals(new AddCommand(card), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_CARD), command);
    }

    @Test
    public void parseCommand_test() throws Exception {
        assertTrue(parser.parseCommand(TestCommand.COMMAND_WORD) instanceof TestCommand);
        assertTrue(parser.parseCommand(TestCommand.COMMAND_WORD + " 3") instanceof TestCommand);
    }

    @Test
    public void parseCommand_next() throws Exception {
        assertTrue(parser.parseCommand(NextCommand.COMMAND_WORD) instanceof NextCommand);
        assertTrue(parser.parseCommand(NextCommand.COMMAND_WORD + " 3") instanceof NextCommand);
    }

    @Test
    public void parseCommand_answer() throws Exception {
        String attemptedAnswerInput = "foo";
        AnswerCommand command = (AnswerCommand) parser.parseCommand(
                AnswerCommand.COMMAND_WORD + " " + attemptedAnswerInput);
        assertEquals(new AnswerCommand(new Answer(attemptedAnswerInput)), command);
    }

    @Test
    public void parseCommand_reveal() throws Exception {
        assertTrue(parser.parseCommand(RevealCommand.COMMAND_WORD) instanceof RevealCommand);
        assertTrue(parser.parseCommand(RevealCommand.COMMAND_WORD + " 3") instanceof RevealCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Card card = new CardBuilder().build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(card).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CARD.getOneBased() + " " + CardUtil.getEditCardDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_CARD, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_end() throws Exception {
        assertTrue(parser.parseCommand(EndCommand.COMMAND_WORD) instanceof EndCommand);
        assertTrue(parser.parseCommand(EndCommand.COMMAND_WORD + " 3") instanceof EndCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new QuestionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_CARD), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
