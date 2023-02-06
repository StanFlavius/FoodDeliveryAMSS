package com.example.fooddelivery.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private static Logger logger = null;

    private final String logFile = "D:\\FoodDelivery\\src\\main\\resources\\log.txt";
    private PrintWriter writer;

    private Logger() {
        try {
            FileWriter fw = new FileWriter(logFile, true);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {}
    }

    public static synchronized Logger getInstance(){
        if(logger == null)
            logger = new Logger();
        return logger;
    }

    public void logMsg (Integer id, String controller, String function, String time) {

        writer.println("CurrentUserId " + id.toString() + "|" + controller + "|" + function + "|" + time);
    }
}
