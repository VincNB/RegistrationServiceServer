package nmbai.commands;

import nmbai.controls.CommandExecutor;
import nmbai.controls.CommandWords;

public class HelpCommand extends Command {
    private final CommandWords commandWords;

    public HelpCommand(CommandWords commandWords) {
        super("help", "displays commands and what they do");
        this.commandWords = commandWords;
    }

    @Override
    public Command copy() {
        return new HelpCommand(this.commandWords);
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameters.size() == 0) {
            executor.output(commandWords.getDescription());
        } else {
            executor.output("help command takes no parameters");
        }
    }
}
