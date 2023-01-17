package nmbai.controls;


import nmbai.commands.Command;
import nmbai.commands.HelpCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private static final String COMMAND_FORMAT = "[%s] - [%s]%n";
    private final Map<String, Command> commands = new HashMap<>();

    private final Command INVALID_COMMAND = new Command("", "") {
        @Override
        public void execute(CommandExecutor executor) {
            executor.output("Command is not recognized.");
        }

        @Override
        public Command copy() {
            return this;
        }
    };

    public CommandWords(Command[] commands) {
        setCommands(commands);
    }

    public void setCommands(Command[] newCommands) {
        commands.clear();
        for (Command command : newCommands) {
            commands.put(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(this);
        commands.put(help.getName(), help);
    }

    public Command getCommand(String commandName) {
        return commands.getOrDefault(commandName, INVALID_COMMAND).copy();
        //copy has to be returned because a command may be requested more than once while it is already on the driver buffer
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder();
        for (Command command : commands.values()) {
            builder.append(String.format(COMMAND_FORMAT, command.getName(), command.getDescription()));
        }
        return builder.toString();
    }
}
