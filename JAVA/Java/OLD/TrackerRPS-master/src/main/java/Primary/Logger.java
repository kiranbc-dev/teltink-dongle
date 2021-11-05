package Primary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

/**
 * <h1>Logger class responsible for logging</h1>
 * <p>Holds few functions Print To Log and Write log.</p>
 * */
public class Logger {

    private String log;
    private File logFile;

    /**
     * <h1>Logger constructor</h1>
     * <p>The constructor is required for declaring a path to file </p>
     * @param path declare full path to file to write
     * */
    public Logger(String path) {
        logFile = new File(path);
    }

    /**
     * <h1>Print to log</h1>
     * <p>Prints given String to file </p>
     * @param text prints value to specified file
     * */
    public void PrintToLOG(String text) {
        log = text;
        WriteLog();
    }

    /**
     * <h1>Write Log</h1>
     * <p>Prints given String to file </p>
     * */
    private void WriteLog() {
        BufferedWriter writer = null;
        try {
           // String path = System.getProperty("user.home") + "/Desktop";
            File yourFile = new File(""+logFile+".txt");
            yourFile.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(yourFile, true));
            writer.write(log + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (Exception e) {
            }
        }
    }
}
