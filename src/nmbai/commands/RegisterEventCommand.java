package nmbai.commands;


import nmbai.controls.CommandExecutor;

public class RegisterEventCommand extends Command {
    public RegisterEventCommand() {
        super("register", 2, "[event name] [amount] registers the event to the current account if able");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.registerEvent(parameters.get(0), parameters.get(1));
        }
    }

    @Override
    public Command copy() {
        return new RegisterEventCommand();
    }
}
