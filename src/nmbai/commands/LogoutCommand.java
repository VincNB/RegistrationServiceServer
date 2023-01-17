package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class LogoutCommand extends Command {
    public LogoutCommand() {
        super("logout", "logs out account");
    }

    @Override
    public Command copy() {
        return new LogoutCommand();
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 0) {
            executor.logout();
        } else {
            executor.output("logout command takes no parameters");
        }
    }
}
