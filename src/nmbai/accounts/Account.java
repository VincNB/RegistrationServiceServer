package nmbai.accounts;

import nmbai.registrationevents.RegistrationEventManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Account {
    private static final int ADDED_POINTS = 1_000;
    private static final int MAX_POINTS = 10_000;
    private final String username;
    private final String password; //hashed password
    private final Map<Integer, Integer> eventIdMap;
    private int points = 0;

    Account(String username, String password) {
        this.eventIdMap = new HashMap<>();
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
    }

    private Account(String username, String password, Map<Integer, Integer> eventIdMap) {
        this.username = username;
        this.password = password;
        this.eventIdMap = eventIdMap;
    }

    static Account changePassword(Account oldAccount, String newPassword) {
        Account newAccount = new Account(oldAccount.username, newPassword, oldAccount.eventIdMap);
        newAccount.points = oldAccount.points;
        return newAccount;
    }

    public static Account read(DataInputStream is) throws IOException {
        String username = is.readUTF();
        String password = is.readUTF();
        int eventsRegistered = is.readInt();
        Map<Integer, Integer> eventIdMap = new HashMap<>();
        for (int i = 0; i < eventsRegistered; i++) {
            int id = is.readInt();
            int count = is.readInt();
            eventIdMap.put(id, count);
        }
        int points = is.readInt();
        Account account = new Account(username, password, eventIdMap);
        account.points = points;
        return account;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return this.points;
    }

    public void addPoints() {
        this.points = Integer.min(this.points + ADDED_POINTS, MAX_POINTS);
    }

    public void removePoints(int amount) {
        this.points -= amount;
    }

    public void registerEvent(int id, int amount) {
        int prev = eventIdMap.getOrDefault(id, 0);
        eventIdMap.put(id, prev + amount);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeUTF(this.username);
        os.writeUTF(this.password);
        os.writeInt(eventIdMap.size());
        for (Map.Entry<Integer, Integer> entry : eventIdMap.entrySet()) {
            os.writeInt(entry.getKey());
            os.writeInt(entry.getValue());
        }
        os.writeInt(points);
    }

    public String getDescription(RegistrationEventManager eventManager) {
        StringBuilder builder = new StringBuilder(String.format("Username:%s, points:%d%n", username, points));
        eventIdMap.forEach((k, v) ->
                builder.append("\tEvent: ").append(eventManager.getEvent(k).getName()).append("; number registered: ").append(v).append('\n')
        );
        return builder.toString();
    }
}
