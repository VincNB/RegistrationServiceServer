package nmbai.commands;

import nmbai.controls.CommandExecutor;

public class EventsCommand extends Command {
    public EventsCommand() {
        super("registrationevents", 0, "displays current registrationevents that can be registered");
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.displayEvents();
        }
    }

    @Override
    public Command copy() {
        return new EventsCommand();
    }
}
