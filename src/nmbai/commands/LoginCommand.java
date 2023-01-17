package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class LoginCommand extends Command {
    public LoginCommand() {
        super("login", "[username] [password] logs in to specified account");
    }

    @Override
    public Command copy() {
        return new LoginCommand();
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 2) {
            executor.login(parameters.get(0), parameters.get(1));
        } else {
            executor.output("login command takes two parameters");
        }
    }
}
