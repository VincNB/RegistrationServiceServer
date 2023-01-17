package nmbai.main;

import nmbai.accounts.AccountManager;
import nmbai.registrationevents.RegistrationEventManager;
import nmbai.network.Server;
import nmbai.network.ServerAction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Driver {
    private final RegistrationEventManager registrationEventManager;
    private final AccountManager accountManager;
    private final Server server;
    private final BlockingQueue<ServerAction> actions = new ArrayBlockingQueue<>(256);
    private boolean running;

    public Driver() {
        this.registrationEventManager = RegistrationEventManager.getInstance();
        this.accountManager = AccountManager.getInstance();
        this.server = new Server(this.accountManager, this.registrationEventManager, this.actions);
    }

    public void start() {
        running = true;
      //  registrationEventManager.newEvent("test_event", 10, 10);
      //  registrationEventManager.newEvent("test_event2", 100, 5);
        for (int i = 0; i < 0; i++) {
            accountManager.newAccount("username" + i, "password");
        }
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    public void run() {
        while (running) {
            ServerAction action = null;
            try {
                action = actions.take();
            } catch (InterruptedException e) {
                running = false;
                e.printStackTrace();
            }
            if (action != null) {
                action.execute();
            }
        }
    }

    public void stop() {
        running = false;
        server.stop();
    }

}
