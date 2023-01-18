package nmbai.controls;

public class LoggedInExecutor implements ExecutorStateManager {
    private final CommandExecutor executor;

    public LoggedInExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public ExecutorState getState() {
        return ExecutorState.LOGGED_IN;
    }

    @Override
    public void newAccount(String username, String password) {
        executor.output("You can't create new accounts while logged in.");
    }

    @Override
    public void login(String username, String password) {
        executor.output("You are already logged in.");
    }

    @Override
    public void changePassword(String password, String newPassword) {
        if (executor.getAccountManager().changePassword(executor.getActiveAccountUsername(), password, newPassword)) {
            executor.output("Password successfully changed.");
        } else {
            executor.output("Password could not be changed.");
        }
    }

    @Override
    public void logout() {
        executor.setActiveAccountUsername("");
        executor.output("You are now logged out.");
        executor.setState(ExecutorState.LOGGED_OUT);
    }

    @Override
    public void registerEvent(String eventName, String strAmount) {
        int amount = 0;
        boolean parsed;
        try {
            amount = Integer.parseInt(strAmount);
            parsed = true;
        } catch (NumberFormatException nfe) {
            parsed = false;
        }
        if (parsed) {
            executor.output(executor.getRegistrationEventManager().registerForEvent(
                    executor.getAccountManager().getAccount(executor.getActiveAccountUsername()),
                    eventName, amount));
            executor.getAccountManager().writeFile();
        } else {
            executor.output(String.format("%s is not an integer amount.", strAmount));
        }
    }

    @Override
    public void displayEvents() {
        executor.output(executor.getRegistrationEventManager().getDescription());
    }

    @Override
    public void displayAccount() {
        executor.output(executor.getAccountManager().getAccount(executor.getActiveAccountUsername())
                .getDescription(executor.getRegistrationEventManager())
        );
    }

    @Override
    public void add() {
        executor.getAccountManager().getAccount(executor.getActiveAccountUsername()).addPoints();
        executor.output("Added 1,000 points up to a maximum of 10,000.");
    }

    @Override
    public void addEvent(String eventName, String eventCost, String eventCapacity) {
        executor.output("This account type can not execute that action.");
    }

}
