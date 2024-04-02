package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.NetConnectPredicate;

/**
 * Finds and lists all persons in NetConnect whose information matches any of the given arguments.
 * Keyword matching is case-insensitive.
 * Find command supports finding by: name, tag, and role.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose information matches any of the given arguments.\n"
            + "Only one type of argument can be given per " + COMMAND_WORD + " command.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME]... "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_ROLE + "ROLE]... \n"
            + "Examples: \n"
            + COMMAND_WORD + " n/alice n/bob n/charlie\n"
            + COMMAND_WORD + " t/friends t/colleagues\n"
            + COMMAND_WORD + " role/client";

    private final NetConnectPredicate<Person> predicate;

    public FindCommand(NetConnectPredicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.stackFilters(predicate);
        String output = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                + "\n" + model.printFilters();
        return new CommandResult(output);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
