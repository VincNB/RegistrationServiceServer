package nmbai.controls;

import nmbai.accounts.AccountManager;
import nmbai.registrationevents.RegistrationEventManager;

import java.io.PrintWriter;
import java.util.function.Consumer;

public class CommandExecutor {
    private final AccountManager accountManager;
    private final RegistrationEventManager registrationEventManager;

    private Consumer<ExecutorState> parserListener;
    private PrintWriter socketOutput;
    private String activeAccountUsername;
    private boolean running;
    private ExecutorState state;

    public CommandExecutor(AccountManager accountManager, RegistrationEventManager registrationEventManager) {
        this.accountManager = accountManager;
        this.registrationEventManager = registrationEventManager;
        this.running = true;
        this.state = ExecutorState.LOGGED_OUT;
        this.activeAccountUsername = "";
    }

    public void setSocketOutput(PrintWriter socketOutput) {
        this.socketOutput = socketOutput;
    }

    public void setParserListener(Consumer<ExecutorState> parserListener) {
        this.parserListener = parserListener;
    }

    public void newAccount(String username, String password) {
        if (state == ExecutorState.LOGGED_OUT) {
            if (accountManager.newAccount(username, password)) {
                output("Successfully added new account with username " + username + ".");
            } else {
                output("Could not add a new account with username " + username + ".");
            }
        } else {
            output("You must be logged out to create a new account.");
        }
    }

    public void login(String username, String password) {
        if (state == ExecutorState.LOGGED_OUT) {
            if (accountManager.authenticate(username, password)) {
                activeAccountUsername = username;
                output("You are now logged in as " + username + ".");
                parserListener.accept(ExecutorState.LOGGED_IN);
                state = ExecutorState.LOGGED_IN;
            } else {
                output("Credentials not recognized.");
            }
        } else {
            output("You are already logged in.");
        }

    }

    public void changePassword(String password, String newPassword) {
        if (state == ExecutorState.LOGGED_IN) {
            if (accountManager.changePassword(activeAccountUsername, password, newPassword)) {
                output("Password successfully changed.");
            } else {
                output("Password could not be changed.");
            }
        } else {
            output("You must log in before you change your password.");
        }

    }

    public void logout() {
        if (state == ExecutorState.LOGGED_IN) {
            activeAccountUsername = "";
            output("You are now logged out.");
            parserListener.accept(ExecutorState.LOGGED_OUT);
            state = ExecutorState.LOGGED_OUT;
        } else {
            output("You must be logged in to be logged out.");
        }
    }

    public void registerEvent(String eventName, String strAmount) {
        if (state == ExecutorState.LOGGED_IN) {
            int amount = 0;
            boolean parsed;
            try {
                amount = Integer.parseInt(strAmount);
                parsed = true;
            } catch (NumberFormatException nfe) {
                parsed = false;
            }
            if (parsed) {
                output(registrationEventManager.registerForEvent(accountManager.getAccount(activeAccountUsername), eventName, amount));
                accountManager.writeFile();
            } else {
                output(String.format("%s is not an integer amount.", strAmount));
            }
        } else {
            output("You must log in before you can register for events.");
        }

    }

    public void displayEvents() {
        output(registrationEventManager.getDescription());
    }

    public void displayAccount() {
        if (state == ExecutorState.LOGGED_IN) {
            output(accountManager.getAccount(activeAccountUsername).getDescription(registrationEventManager));
        } else {
            output("You must log in before you can view account information.");
        }

    }

    public void add() {
        if (state == ExecutorState.LOGGED_IN) {
            accountManager.getAccount(activeAccountUsername).addPoints();
            output("Added 1,000 points up to a maximum of 10,000.");
        } else {
            output("You must log in before you can add points to your account.");
        }
    }

    public void output(String message) {
        socketOutput.println(message);
    }

    public void quit() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
