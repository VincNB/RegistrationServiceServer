package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class EventsCommand extends Command {
    public EventsCommand() {
        super("registrationevents", "displays current registrationevents that can be registered");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 0) {
            executor.displayEvents();
        } else {
            executor.output("registrationevents command takes no parameters");
        }
    }

    @Override
    public Command copy() {
        return new EventsCommand();
    }
}
