package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class AccountCommand extends Command {
    public AccountCommand() {
        super("account", 0, "display account information");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.displayAccount();
        }
    }

    @Override
    public Command copy() {
        return new AccountCommand();
    }
}
