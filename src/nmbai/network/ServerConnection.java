package nmbai.network;


import nmbai.Logger;
import nmbai.commands.Command;
import nmbai.controls.CommandExecutor;
import nmbai.controls.Parser;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServerConnection implements Runnable {
    private final Parser parser = new Parser();
    private final CommandExecutor executor;
    private final BlockingQueue<ServerAction> actionQueue;
    private final Socket socket; //input(from outside) is saved, output(to outside) given to executor
    private BufferedReader input;
    private boolean running = false;

    public ServerConnection(CommandExecutor executor, Socket socket, BlockingQueue<ServerAction> actionQueue) {
        this.executor = executor;
        this.actionQueue = actionQueue;
        this.socket = socket;
        this.executor.setParserListener(parser.getListener());
    }

    public void run() {
        running = setup();
        executor.output("Welcome to the server! Input \"help\" for help.");
        while (executor.isRunning() && running) {
            Command command = parser.parseCommand(getInput());
            ServerAction action = new ServerAction(executor, command);
            actionQueue.add(action);
        }
        close(false);
    }

    private String getInput() {
        String nextLine = null;
        try {
            nextLine = input.readLine();
        } catch (IOException ex) {
            Logger.INSTANCE.log(ex.toString());
            close(true);
        }
        if (nextLine == null) {
            nextLine = "";
        }
        return nextLine;
    }

    private void close(boolean error) {
        try {
            socket.close();
            running = false;
        } catch (IOException ex) {
            if (error) {
                Logger.INSTANCE.log(ex.toString());
            }
        }
    }

    private boolean setup() {
        boolean success = true;
        try {
            executor.setOutput(new PrintStream(socket.getOutputStream()));
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }
}
