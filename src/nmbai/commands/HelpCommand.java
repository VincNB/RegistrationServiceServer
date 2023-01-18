package nmbai.commands;

import nmbai.controls.CommandExecutor;
import nmbai.controls.CommandWords;

public class HelpCommand extends Command {
    private final CommandWords commandWords;

    public HelpCommand(CommandWords commandWords) {
        super("help", 0, "displays commands and what they do");
        this.commandWords = commandWords;
    }

    @Override
    public Command copy() {
        return new HelpCommand(this.commandWords);
    }

    @Override
    public void execute(CommandExecutor executor) {
        if (parameterCheck(executor)) {
            executor.output(commandWords.getDescription());
        }
    }
}
