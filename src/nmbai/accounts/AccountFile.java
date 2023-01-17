package nmbai.accounts;


import nmbai.Logger;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class AccountFile {
    //this REALLY should be a database

    private final String fileName = "Accounts";
    private final Deque<Account> writeQueue = new ArrayDeque<>();

    public AccountFile() {
        //empty constructor
    }

    public void bufferWrite(Account account) {
        writeQueue.offer(account);
    }

    public void write() {
        try (DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName))) {
            os.writeInt(writeQueue.size());
            while (!writeQueue.isEmpty()) {
                writeQueue.poll().write(os);
            }
        } catch (FileNotFoundException ex) {
            Logger.INSTANCE.log(String.format("Could not write AccountFile. File %s not found.", fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Deque<Account> read() {
        //read should ideally only be called once per run
        Deque<Account> readQueue = new ArrayDeque<>();
        try (DataInputStream is = new DataInputStream(new FileInputStream(fileName))) {
            int count = is.readInt();
            for (int i = 0; i < count; i++) {
                Account account = Account.read(is);
                readQueue.offer(account);
            }
        } catch (FileNotFoundException ex) {
            Logger.INSTANCE.log(String.format("Could not read AccountFile. File %s not found.", fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return readQueue;
    }
}
