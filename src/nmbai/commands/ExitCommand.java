package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "logs out of account and exits program");
    }

    @Override
    public Command copy() {
        return new ExitCommand();
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 0) {
            executor.quit();
        } else {
            executor.output("exit command takes no parameters");
        }

    }
}
