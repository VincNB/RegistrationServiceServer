package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class NewAccountCommand extends Command {
    public NewAccountCommand() {
        super("new", 2, "[username] [password] creates a new account with username and password");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.newAccount(parameters.get(0), parameters.get(1));
        }
    }

    @Override
    public Command copy() {
        return new NewAccountCommand();
    }
}
