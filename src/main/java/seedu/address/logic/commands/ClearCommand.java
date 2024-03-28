package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.ui.DestructiveConfirmationWindow.handleDestructiveConfirmation;

import seedu.address.model.Model;
import seedu.address.model.NetConnect;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String CLEAR_SUCCESS_MESSAGE = "Address book has been cleared!";
    public static final String CLEAR_CANCELLED_MESSAGE = "Clear command cancelled";
    private static Model model;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        this.model = model;
        boolean isConfirmed = handleDestructiveConfirmation(false, true);
        if (!isConfirmed) {
            return new CommandResult(CLEAR_CANCELLED_MESSAGE, false, false);
        }
        model.setNetConnect(new NetConnect());
        return new CommandResult(CLEAR_SUCCESS_MESSAGE, false, false);
    }
}
