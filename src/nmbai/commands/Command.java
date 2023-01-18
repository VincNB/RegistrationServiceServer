package nmbai.commands;

import nmbai.controls.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected final List<String> parameters = new ArrayList<>();
    private final String name;
    private final String description;
    private final int parameterCount;

    public Command(String name, int parameterCount, String description) {
        this.name = name;
        this.parameterCount = parameterCount;
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

    protected boolean parameterCheck(CommandExecutor executor) {
        if (parameters.size() != parameterCount) {
            executor.output(String.format("%s takes %d parameter(s).", this.name, this.parameterCount));
            return false;
        }
        return true;
    }

    public abstract void execute(CommandExecutor executor);

    public abstract Command copy();
}
