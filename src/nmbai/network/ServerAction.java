package nmbai.network;

import nmbai.commands.Command;
import nmbai.controls.CommandExecutor;

public class ServerAction {
    private final CommandExecutor executor;
    private final Command command;

    public ServerAction(CommandExecutor executor, Command command) {
        this.executor = executor;
        this.command = command;
    }

    public void execute() {
        command.execute(executor);
    }
}
