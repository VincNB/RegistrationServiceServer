package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class AddCommand extends Command {
    public AddCommand() {
        super("add",0, "add points to account");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if(parameterCheck(executor))
        {
            executor.add();
        }
    }

    @Override
    public Command copy() {
        return new AddCommand();
    }
}
