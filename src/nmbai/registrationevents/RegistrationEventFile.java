package nmbai.registrationevents;

import nmbai.Logger;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class RegistrationEventFile {
    //this probably should've been a database
    private final String fileName = "Registration_Events";
    private final Deque<RegistrationEvent> writeQueue = new ArrayDeque<>();


    public RegistrationEventFile() {
        //empty constructor
    }

    public void bufferWrite(RegistrationEvent event) {
        writeQueue.offer(event);
    }

    public void write() {
        try (DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName))) {
            os.writeInt(writeQueue.size()); //write size of queue first, will need in future for read
            while (!writeQueue.isEmpty()) {
                writeQueue.poll().write(os);
            }
        } catch (FileNotFoundException ex) {
            Logger.INSTANCE.log(String.format("Could not write EventFile. File %s not found.", fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Deque<RegistrationEvent> read() {
        //read should ideally only be called once per run
        Deque<RegistrationEvent> readQueue = new ArrayDeque<>();
        try (DataInputStream is = new DataInputStream(new FileInputStream(fileName))) {
            int count = is.readInt();
            for (int i = 0; i < count; i++) {
                RegistrationEvent event = RegistrationEvent.read(is);
                readQueue.offer(event);
            }
        } catch (FileNotFoundException ex) {
            Logger.INSTANCE.log(String.format("Could not read EventFile. File %s not found.", fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return readQueue;
    }

}
