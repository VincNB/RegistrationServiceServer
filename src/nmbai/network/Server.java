package nmbai.network;

import nmbai.Logger;
import nmbai.accounts.AccountManager;
import nmbai.controls.CommandExecutor;
import nmbai.registrationevents.RegistrationEventManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 45112;

    private final AccountManager accountManager;
    private final RegistrationEventManager registrationEventManager;
    private final BlockingQueue<ServerAction> actions;

    private boolean running;
    private ServerSocket serverSocket;

    public Server(AccountManager accountManager, RegistrationEventManager registrationEventManager, BlockingQueue<ServerAction> actions) {
        this.accountManager = accountManager;
        this.registrationEventManager = registrationEventManager;
        this.actions = actions;
    }

    public void run() {
        start();
        ExecutorService pool = Executors.newCachedThreadPool();
        while (running) {
            Socket socket = connect();
            if (socket != null)
            {
                CommandExecutor executor = new CommandExecutor(this.accountManager, this.registrationEventManager);
                ServerConnection connection = new ServerConnection(executor, socket, actions);
                pool.submit(connection);
            }
        }
        pool.shutdown();
    }

    private Socket connect() {
        Logger.INSTANCE.log("Server listening for connection.");
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            Logger.INSTANCE.log("Server accepted connection.");
        } catch (IOException ex) {
            ex.printStackTrace();
            running = false;
        }
        return socket;
    }

    private void start() {
        Logger.INSTANCE.log("Starting server.");
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            running = true;
            Logger.INSTANCE.log("Server started.");
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
            Logger.INSTANCE.log("Server could not start.");
        }
    }

    public void stop() {
        Logger.INSTANCE.log("Stopping server.");
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.INSTANCE.log("Server has stopped.");
    }

}
