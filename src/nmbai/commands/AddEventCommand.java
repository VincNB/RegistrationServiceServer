package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class AddEventCommand extends Command {
    public AddEventCommand() {
        super("addevent", 3, "[event name] [event cost] [event capacity] to add a new RegistrationEvent");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.addEvent(parameters.get(0), parameters.get(1), parameters.get(2));
        }
    }

    @Override
    public Command copy() {
        return new AddEventCommand();
    }
}
