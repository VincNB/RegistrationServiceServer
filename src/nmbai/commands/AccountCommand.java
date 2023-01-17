package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class AccountCommand extends Command {
    public AccountCommand() {
        super("account", "display account information");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 0) {
            executor.displayAccount();
        } else {
            executor.output("account command takes no parameters");
        }
    }

    @Override
    public Command copy() {
        return new AccountCommand();
    }
}
