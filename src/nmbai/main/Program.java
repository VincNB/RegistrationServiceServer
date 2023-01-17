package nmbai.main;


import nmbai.Logger;

public class Program {
    public static void main(String[] args) {
        if (args.length != 0) {
            if (args[0].equals("-v")) {
                Logger.INSTANCE.start();
                Logger.INSTANCE.log("Logging enabled.");
            }
        } else {
            System.out.println("Run with parameter -v to enable logging.");
        }
        Driver driver = new Driver();
        driver.start();
        driver.run();
        driver.stop();

        Logger.INSTANCE.stop();
    }
}
