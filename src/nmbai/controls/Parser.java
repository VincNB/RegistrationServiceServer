package nmbai.controls;

import nmbai.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Parser {
    private final Map<ExecutorState, CommandWords> stateMap = new HashMap<>();
    private ExecutorState state = ExecutorState.LOGGED_OUT;

    public Parser() {
        CommandWords words = new CommandWords(new Command[]{
                new EventsCommand(), new ExitCommand(), new LoginCommand(), new NewAccountCommand()
        });
        stateMap.put(ExecutorState.LOGGED_OUT, words);

        words = new CommandWords(new Command[]{
                new AccountCommand(), new AddCommand(), new ChangePasswordCommand(), new EventsCommand(),
                new ExitCommand(), new LogoutCommand(), new RegisterEventCommand()
        });
        stateMap.put(ExecutorState.LOGGED_IN, words);
        words = new CommandWords(new Command[]{
                new EventsCommand(), new NewAccountCommand(), new AddEventCommand()
        });
        stateMap.put(ExecutorState.SERVER_CONTROLLER, words);
    }

    public Command parseCommand(String input) {
        String[] split = input.split(" ");
        Command command = stateMap.get(state).getCommand(split[0]);

        for (int i = 1; i < split.length; i++) {
            command.addParameter(split[i]);
        }
        return command;
    }

    public Consumer<ExecutorState> getListener() {
        return executorState -> state = executorState;
    }
}
