package nmbai.commands;

import nmbai.controls.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected final List<String> parameters = new ArrayList<>();
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public abstract void execute(CommandExecutor executor);
    public abstract Command copy();
}
