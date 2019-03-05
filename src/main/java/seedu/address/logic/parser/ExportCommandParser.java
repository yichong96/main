package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLDERNAME;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input for export command arguments and creates a new export command object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_FILENAME, PREFIX_FOLDERNAME);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_FOLDERNAME, PREFIX_FILENAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        return null;
    }
}
