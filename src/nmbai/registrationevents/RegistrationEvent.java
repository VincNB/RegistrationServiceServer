package nmbai.registrationevents;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistrationEvent  {
    private static final String DEFAULT_NAME = "EVENT_NAME_UNSET";
    private static final int DEFAULT_COST = 1;
    private static final int DEFAULT_CAPACITY = 0;
    private final String name;
    private final int cost;
    private final int id; //id used for file saving
    private int capacity;

    public RegistrationEvent(int id) {
        this(id, DEFAULT_NAME, DEFAULT_COST, DEFAULT_CAPACITY);
    }

    public RegistrationEvent(int id, String name, int cost, int capacity) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.capacity = capacity;
    }

    public static RegistrationEvent read(DataInputStream is) throws IOException {
        String name = is.readUTF();
        int cost = is.readInt();
        int id = is.readInt();
        int capacity = is.readInt();
        return new RegistrationEvent(id, name, cost, capacity);
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void removeCapacity(int amount) {
        capacity -= amount;
    }

    public int getId() {
        return id;
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeUTF(name);
        os.writeInt(cost);
        os.writeInt(id);
        os.writeInt(capacity);
    }
}
