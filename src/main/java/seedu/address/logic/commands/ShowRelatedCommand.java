package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.filter.IdContainsDigitsPredicate;
import seedu.address.model.util.RelatedList;

/**
 * Finds and lists all persons in address book related to the person with the specified id.
 */
public class ShowRelatedCommand extends Command {

    public static final String COMMAND_WORD = "showrelated";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons related to person with the specified id.\n"
            + "Parameters: i/ID\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ID + "/1";
    public static final String MESSAGE_RELATED_TO = "Showing all persons related to:\n%1$s";

    private final Id id;

    public ShowRelatedCommand(Id id) {
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        RelatedList relatedList = model.getRelatedIdTuples();

        List<Integer> relatedIds = relatedList.getAllRelatedIds(relatedList, id);
        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(relatedIds);

        model.clearFilter();
        model.stackFilters(predicate);

        String output = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                + " " + String.format(MESSAGE_RELATED_TO, Messages.format(model.getPersonById(id)));

        return new CommandResult(output);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ShowRelatedCommand)) {
            return false;
        }

        ShowRelatedCommand otherCommand = (ShowRelatedCommand) other;
        return id.equals(otherCommand.id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .toString();
    }
}
