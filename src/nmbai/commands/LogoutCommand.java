package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class LogoutCommand extends Command {
    public LogoutCommand() {
        super("logout", 0, "logs out account");
    }

    @Override
    public Command copy() {
        return new LogoutCommand();
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.logout();
        }

    }
}
