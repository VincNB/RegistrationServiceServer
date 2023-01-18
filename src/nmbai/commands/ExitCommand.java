package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", 0, "logs out of account and exits program");
    }

    @Override
    public Command copy() {
        return new ExitCommand();
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.quit();
        }
    }
}
