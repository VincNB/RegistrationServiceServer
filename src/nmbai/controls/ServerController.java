package nmbai.controls;

import nmbai.commands.Command;
import nmbai.network.ServerAction;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ServerController implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private final Parser parser = new Parser();
    private final CommandExecutor executor;
    private final BlockingQueue<ServerAction> actionQueue;

    public ServerController(CommandExecutor executor, BlockingQueue<ServerAction> actionQueue) {
        this.executor = executor;
        this.actionQueue = actionQueue;
        this.executor.setOutput(System.out);
        this.executor.setParserListener(parser.getListener());
        this.executor.setState(ExecutorState.SERVER_CONTROLLER);
    }

    @Override
    public void run() {
        while (executor.isRunning()) {
            executor.output("Server Input > ");
            String input = scanner.nextLine();
            if (executor.isRunning()) {
                Command command = parser.parseCommand(input);
                actionQueue.add(new ServerAction(executor, command));
            }
        }
    }
}
