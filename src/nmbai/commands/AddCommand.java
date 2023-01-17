package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class AddCommand extends Command {
    public AddCommand() {
        super("add", "add points to account");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 0) {
            executor.add();
        } else {
            executor.output("add command takes no parameters");
        }
    }

    @Override
    public Command copy() {
        return new AddCommand();
    }
}
