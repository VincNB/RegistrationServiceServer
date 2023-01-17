package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class NewAccountCommand extends Command {
    public NewAccountCommand() {
        super("new", "[username] [password] creates a new account with username and password");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 2) {
            executor.newAccount(parameters.get(0), parameters.get(1));
        } else {
            executor.output("new command takes two parameters");
        }
    }

    @Override
    public Command copy() {
        return new NewAccountCommand();
    }
}
