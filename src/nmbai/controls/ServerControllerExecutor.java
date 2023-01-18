package nmbai.controls;

/**
 * Class allows the server owner/controller to execute their own commands. This shares many controls with LoggedOutExecutor
 * with added server functionality.
 */
public class ServerControllerExecutor implements ExecutorStateManager {

    private final CommandExecutor executor;

    public ServerControllerExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public ExecutorState getState() {
        return ExecutorState.SERVER_CONTROLLER;
    }

    @Override
    public void newAccount(String username, String password) {
        if (executor.getAccountManager().newAccount(username, password)) {
            executor.output("Successfully added new account with username " + username + ".");
        } else {
            executor.output("Could not add a new account with username " + username + ".");
        }
    }

    @Override
    public void login(String username, String password) {
        executor.output("Server controller can not login.");
    }

    @Override
    public void changePassword(String password, String newPassword) {
        executor.output("You must be logged in to change your password.");
    }

    @Override
    public void logout() {
        executor.output("You must be logged in to log out.");
    }

    @Override
    public void registerEvent(String eventName, String strAmount) {
        executor.output("You must be logged in register for events.");
    }

    @Override
    public void displayEvents() {
        executor.output(executor.getRegistrationEventManager().getDescription());
    }

    @Override
    public void displayAccount() {
        executor.output("You must be logged in to display account information.");
    }

    @Override
    public void add() {
        executor.output("You must be logged in to add points to an account.");
    }

    @Override
    public void addEvent(String eventName, String eventCost, String eventCapacity) {
        int cost = -1;
        int capacity = -1;
        try {
            cost = Integer.parseInt(eventCost);
            capacity = Integer.parseInt(eventCapacity);
        } catch (NumberFormatException ignored) {
            //empty
        }
        if (cost >= 0 && capacity >= 0) {
            if (executor.getRegistrationEventManager().newEvent(eventName, cost, capacity)) {
                executor.output(String.format("Successfully added event %s with cost %d and capacity %d.%n", eventName, cost, capacity));
            } else {
                executor.output(String.format("Could not add event %s. Event with that name already exists.%n", eventName));
            }
        } else {
            executor.output(String.format("Could not add event %s. Cost and capacity must be integers greater than 0.%n", eventName));
        }
    }

}
