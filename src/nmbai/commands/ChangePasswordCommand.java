package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class ChangePasswordCommand extends Command {

    public ChangePasswordCommand() {
        super("chpw", "[current password] [new password] changes password to new password");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 2) {
            executor.changePassword(parameters.get(0), parameters.get(1));
        } else {
            executor.output("chpw command takes two parameters");
        }
    }

    @Override
    public Command copy() {
        return new ChangePasswordCommand();
    }
}
