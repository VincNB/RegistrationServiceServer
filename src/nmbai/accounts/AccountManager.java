package nmbai.accounts;

import nmbai.Logger;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountManager {
    private final Map<String, Account> accounts = new HashMap<>();
    private final AccountAuthenticator authenticator = new AccountAuthenticator();
    private final AccountFile file = new AccountFile(); //file is rewritten every time an account's data changes, this is bad

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        AccountManager manager = new AccountManager();
        manager.readFile();
        return manager;
    }

    private void readFile() {
        Logger.INSTANCE.log("Reading Account file.");
        Deque<Account> queue = file.read();
        int count = 0;
        while (!queue.isEmpty()) {
            count++;
            Account account = queue.poll();
            accounts.put(account.getUsername(), account);
        }
        Logger.INSTANCE.log("Reading of Account file complete. Read " + count + " account(s).");
    }

    public void writeFile() {
        Logger.INSTANCE.log("Writing to Account file.");
        for (Account account : accounts.values()) {
            file.bufferWrite(account);
        }
        file.write();
        Logger.INSTANCE.log("Writing to Account file complete.");
    }

    public boolean newAccount(String username, String password) {
        boolean success = false;
        if (!accounts.containsKey(Objects.requireNonNull(username))) {
            Account account = new Account(username, authenticator.generatePassword(Objects.requireNonNull(password)));
            accounts.put(username, account);
            success = true;
            writeFile();
        }
        return success;
    }

    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        Account account = getAccount(Objects.requireNonNull(username));
        if (account != null) {
            authenticated = authenticator.authenticate(Objects.requireNonNull(password), account.getPassword());
        }
        return authenticated;
    }

    public Account getAccount(String username) {
        return accounts.get(Objects.requireNonNull(username));
    }

    public boolean changePassword(String username, String password, String newPassword) {
        boolean success = false;
        if (authenticate(username, password)) {
            Account account = getAccount(username);
            account = Account.changePassword(account, authenticator.generatePassword(Objects.requireNonNull(newPassword)));
            accounts.put(account.getUsername(), account); //need to replace account because immutable
            success = true;
            writeFile();
        }
        return success;
    }

}
