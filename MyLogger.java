package courseProjects;

import java.io.IOException;
import java.util.logging.*; // needed for logging

public class MyLogger {

    // this is our one shared logger object
    private static Logger log = Logger.getLogger(MyLogger.class.getName());

    static {
        try {
            // reset any old settings
            LogManager.getLogManager().reset();

            // set the logger to log everything
            log.setLevel(Level.ALL);

            // create a file to write the logs to (append mode = true)
            FileHandler file = new FileHandler("ordersystem_log.txt", true);
            file.setLevel(Level.ALL); // log all levels
            file.setFormatter(new SimpleFormatter()); // simple text format

            // add the file handler to our logger
            log.addHandler(file);

        } catch (IOException e) {
            // if logging setup fails, log it
            log.log(Level.SEVERE, "couldn't set up logger", e);
        }
    }

    // this lets other classes get the logger to use it
    public static Logger getLogger() {
        return log;
    }
}
