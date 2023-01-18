package nmbai.controls;

import nmbai.accounts.AccountManager;
import nmbai.registrationevents.RegistrationEventManager;

import java.io.PrintStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class CommandExecutor {
    private final AccountManager accountManager;
    private final RegistrationEventManager registrationEventManager;
    private final Map<ExecutorState, ExecutorStateManager> stateManager = new EnumMap<>(ExecutorState.class);
    private Consumer<ExecutorState> parserListener;
    private PrintStream output;
    private String activeAccountUsername;
    private boolean running;
    private ExecutorState state;

    private CommandExecutor(AccountManager accountManager, RegistrationEventManager registrationEventManager) {
        this.accountManager = accountManager;
        this.registrationEventManager = registrationEventManager;
        this.running = true;
        this.state = ExecutorState.LOGGED_OUT;
        this.activeAccountUsername = "";
    }

    public static CommandExecutor getInstance(AccountManager accountManager, RegistrationEventManager registrationEventManager) {
        CommandExecutor executor = new CommandExecutor(accountManager, registrationEventManager);
        ExecutorStateManager[] managers = new ExecutorStateManager[]{
                new LoggedInExecutor(executor),
                new LoggedOutExecutor(executor),
                new ServerControllerExecutor(executor)
        };
        for (ExecutorStateManager manager : managers) {
            executor.stateManager.put(manager.getState(), manager);
        }
        return executor;
    }

    public void setState(ExecutorState state) {
        this.state = state;
        parserListener.accept(this.state);
    }

    public String getActiveAccountUsername() {
        return activeAccountUsername;
    }

    public void setActiveAccountUsername(String activeAccountUsername) {
        this.activeAccountUsername = activeAccountUsername;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public RegistrationEventManager getRegistrationEventManager() {
        return registrationEventManager;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

    public void setParserListener(Consumer<ExecutorState> parserListener) {
        this.parserListener = parserListener;
    }

    public void newAccount(String username, String password) {
        stateManager.get(this.state).newAccount(username, password);
    }

    public void login(String username, String password) {
        stateManager.get(this.state).login(username, password);
    }

    public void changePassword(String password, String newPassword) {
        stateManager.get(this.state).changePassword(password, newPassword);

    }

    public void logout() {
        stateManager.get(this.state).logout();
    }

    public void registerEvent(String eventName, String strAmount) {
        stateManager.get(this.state).registerEvent(eventName, strAmount);
    }

    public void displayEvents() {
        stateManager.get(this.state).displayEvents();
    }

    public void displayAccount() {
        stateManager.get(this.state).displayAccount();
    }

    public void add() {
        stateManager.get(this.state).add();
    }

    public void addEvent(String eventName, String eventCost, String eventCapacity) {
        stateManager.get(this.state).addEvent(eventName, eventCost, eventCapacity);
    }

    public void output(String message) {
        output.println(message);
    }

    public void quit() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
