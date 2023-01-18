package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class ChangePasswordCommand extends Command {

    public ChangePasswordCommand() {
        super("chpw", 2, "[current password] [new password] changes password to new password");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.changePassword(parameters.get(0), parameters.get(1));
        }
    }

    @Override
    public Command copy() {
        return new ChangePasswordCommand();
    }
}
