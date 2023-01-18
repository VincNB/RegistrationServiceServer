package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class AddEventCommand extends Command {
    public AddEventCommand() {
        super("addEvent", "adds a new event to the server");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 3) {
            executor.addEvent(parameters.get(0), parameters.get(1), parameters.get(2));
        } else {
            executor.output("addEvent command takes 2 parameters");
        }
    }

    @Override
    public Command copy() {
        return new AddEventCommand();
    }
}
