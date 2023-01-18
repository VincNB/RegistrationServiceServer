package nmbai.controls;


public class LoggedOutExecutor implements ExecutorStateManager {
    private final CommandExecutor executor;

    public LoggedOutExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public ExecutorState getState() {
        return ExecutorState.LOGGED_OUT;
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
        if (executor.getAccountManager().authenticate(username, password)) {
            executor.setActiveAccountUsername(username);
            executor.output("You are now logged in as " + username + ".");
            executor.setState(ExecutorState.LOGGED_IN);
        } else {
            executor.output("Credentials not recognized.");
        }
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
        executor.output("This account type can not execute that action.");
    }
}
