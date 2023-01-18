package nmbai.registrationevents;

import nmbai.Logger;
import nmbai.accounts.Account;

import java.util.*;

public class RegistrationEventManager {
    //registrationevents publicly accessible by name but internally categorized by id
    private final Map<String, RegistrationEvent> stringEventMap = new HashMap<>();
    private final Map<Integer, RegistrationEvent> intEventMap = new HashMap<>();
    private final RegistrationEventFile file = new RegistrationEventFile();
    private int nextId = 1_000; //initial value is completely arbitrary
    //every event should have a completely unique id forever, used in saving and loading file

    private RegistrationEventManager() {
    }

    public static RegistrationEventManager getInstance() {
        RegistrationEventManager manager = new RegistrationEventManager();
        manager.readFile();
        return manager;
    }

    public boolean newEvent(String eventName, int cost, int capacity) {
        boolean success = false;
        if (cost >= 0 && capacity >= 0 && !stringEventMap.containsKey(eventName)) {
            RegistrationEvent event = new RegistrationEvent(nextId++, eventName, cost, capacity);
            stringEventMap.put(eventName, event);
            intEventMap.put(event.getId(), event);
            writeFile();
            success = true;
        }
        return success;
    }

    public RegistrationEvent getEvent(int eventId) {
        return intEventMap.get(eventId);
    }

    public RegistrationEvent getEvent(String eventName) {
        return stringEventMap.get(eventName);
    }

    public void removeEvent(String eventName) {
        RegistrationEvent event = stringEventMap.remove(eventName);
        if (event != null) {
            intEventMap.remove(event.getId());
            writeFile();
        }
    }

    public String registerForEvent(Account account, String eventName, int amount) {
        StringBuilder builder = new StringBuilder();
        RegistrationEvent event = getEvent(eventName);
        if (event != null) {
            if (amount > 0) {
                if (event.getCapacity() >= amount) {
                    int cost = amount * event.getCost();
                    if (cost <= account.getPoints()) {
                        account.removePoints(cost);
                        account.registerEvent(event.getId(), amount);
                        event.removeCapacity(amount);
                        builder.append(String.format("Successfully registered for %d spots for event %s; deducted %d points from your account.", amount, eventName, cost));
                        writeFile();
                    } else {
                        builder.append(String.format("Not enough points to cover registration fee; found %d, requires %d. ", account.getPoints(), cost));
                    }
                } else {
                    builder.append(String.format("Event %s does not have enough remaining capacity; found %d, requires %d. ", eventName, event.getCapacity(), amount));
                }
            } else {
                builder.append(String.format("Amount must be an integer greater than 0; found %d. ", amount));
            }

        } else {
            builder.append(String.format("Event %s not found. ", eventName));
        }
        return builder.toString();
    }

    private void writeFile() {
        Logger.INSTANCE.log("Writing to event file.");
        for (RegistrationEvent event : stringEventMap.values()) {
            file.bufferWrite(event);
        }
        file.write();
        Logger.INSTANCE.log("Writing to event file complete.");
    }

    private void readFile() {
        Set<Integer> set = new HashSet<>();
        Logger.INSTANCE.log("Reading event file.");
        Deque<RegistrationEvent> queue = file.read();
        int count = 0;
        while (!queue.isEmpty()) {
            count++;
            RegistrationEvent event = queue.poll();
            stringEventMap.put(event.getName(), event);
            intEventMap.put(event.getId(), event);
            set.add(event.getId());
        }
        for (Integer i : set) {
            if (i > nextId) {
                nextId = i;
            }
        }
        nextId++;
        Logger.INSTANCE.log("Reading of event file complete. Read " + count + " event(s). Next event ID will be " + nextId + ".");
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder("Events:\n");
        for (RegistrationEvent event : stringEventMap.values()) {
            builder.append(String.format("[%s] - cost:%d - capacity:%d%n", event.getName(), event.getCost(), event.getCapacity()));
        }
        return builder.toString();
    }
}
