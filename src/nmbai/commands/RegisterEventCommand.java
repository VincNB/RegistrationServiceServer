package nmbai.commands;


import nmbai.controls.CommandExecutor;

public class RegisterEventCommand extends Command {
    public RegisterEventCommand() {
        super("register", "[event name] [amount] registers the event to the current account if able");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 2) {
            executor.registerEvent(parameters.get(0), parameters.get(1));
        } else {
            executor.output("register command takes two parameters");
        }
    }

    @Override
    public Command copy() {
        return new RegisterEventCommand();
    }
}
