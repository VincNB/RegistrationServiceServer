package nmbai.main;

import nmbai.accounts.AccountManager;
import nmbai.controls.CommandExecutor;
import nmbai.controls.ServerController;
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
        Thread serverThread = new Thread(server);
        serverThread.start();
        CommandExecutor controllerExecutor = CommandExecutor.getInstance(this.accountManager, this.registrationEventManager);
        Thread controllerThread = new Thread(new ServerController(controllerExecutor, this.actions));
        controllerThread.start();
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
