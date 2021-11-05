package TCP.TCP_Model;

import Primary.Converter;
import Primary.Crc16;
import Primary.Logger;
import TCP.TCP_View.TCPViewModel;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
/**
 * <h1>TCP Connection</h1>
 * <p>A tcp connection is a connected client to tcp server that listens for incoming imei and rest of data.
 * First received input reads as IMEI, second reads as data and from there it sends record length
 * until the tracker disconnects or sends nothing </p>
 * */
public class TCPConnection implements Runnable {
    private volatile boolean flag = true;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Logger logger;
    private TCPViewModel viewModel;
    private Converter converter;
    private Crc16 crc16;
    private String imei;

    /**
     * <h1>TCP Connection construct</h1>
     * <p>The tcp connection requires two parameters socket and view model. </p>
     * @param socket to establish connection.
     * @param viewModel to display results for user.
     * */
    TCPConnection(Socket socket, TCPViewModel viewModel) {
        super();
        this.socket = socket;
        this.viewModel = viewModel;
        converter = new Converter();
        crc16 = new Crc16();
    }

    /**
     * <h1>Run function to start listener</h1>
     * <p>Simply runs the runnable thread to listen everything from client</p>
     * */
    public void run() {
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            Listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h1>Listen</h1>
     * <p>Function for listening connected client</p>
     * @throws IOException throws exception if input stream is interrupted
     * */
    private void Listen() throws IOException {
        while (flag) {
            System.out.println("listening...");
            while (!socket.isClosed() && inputStream.available() == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            Communicate();
        }
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    /**
     * <h1>Get Number Of Records</h1>
     * <p>Reads the number of records to send back to the sender</p>
     * @param data the parameter is a received hex data
     * @return String format number of records
     * */
    private String GetNumberOfRecords(String data) {
        return data.substring(18, 20);
    }

    /**
     * <h1>Communicate</h1>
     * <p>A reader and sender with client, first it reads imei, then sends back 01.
     * It receives data, as soon it receives it sends back number of records.
     * The while loop initializes and runs until it get interrupted or client disconnects.</p>
     * */
    private void Communicate()  {

        imei =  Objects.requireNonNull(ReadInput()).substring(4);
        imei = converter.ReadImei(imei);
        String path = System.getProperty("user.home") + "/Desktop";
        logger = new Logger(path+"/Logs/TCPLogs/"+imei);
        logger.PrintToLOG(GetTime()+" IMEI: " +imei);
        if(imei.length() < 15){
            SendOutput("00");
        }
        else{
            SendOutput("01");
            logger.PrintToLOG("\tResponse: [0" + 1 + "]");
            String input = ReadInput();
            Log(Objects.requireNonNull(input));
            while(flag){
                String recordsCount = GetNumberOfRecords(input);
                SendOutput("000000" + recordsCount);
                logger.PrintToLOG("\tCrc: " + Integer.toHexString(CRC(input)));
                logger.PrintToLOG("\tResponse: [000000" + recordsCount + "]\n");
                input = ReadInput();
                Log(Objects.requireNonNull(input));
            }
        }
    }

    /**
     * <h1>Send Output</h1>
     * <p>Sends output to the client</p>
     * @param message the parameter is a received hex data
     * */
    private void SendOutput(String message)  {
        try {
            outputStream.write(converter.StringToByteArray(message));
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Output stream was interrupted");
        }
    }

    /**
     * <h1>CRC</h1>
     * <p>Calculates CRC of received data</p>
     * @param str the parameter is a received hex data
     * @return int of crc16
     * */
    private int CRC(String str) {
        str = str.substring(16, str.length() - 8);
        byte[] bytes = converter.StringToByteArray(str);
        return crc16.getCRC(bytes);
    }

    /**
     * <h1>Read Input</h1>
     * <p>Reads the input from client. Currently maximum message byte is set up to 8192,
     * if message is bigger then message will not be properly readable and displayed.</p>
     * @return String of received data
     * */
    private String ReadInput() {
        byte[] messageByte = new byte[8192];
        int dataSize;
        try {
            dataSize = inputStream.read(messageByte);
            String finalInput = converter.BytesArrayToHex(messageByte, dataSize);
            SendToConsole(finalInput);
            return finalInput;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <h1>Send To Console</h1>
     * <p>Simply prints out the results to the text area for user</p>
     * @param input the parameter is String format to print in text area
     * */
    private void SendToConsole(String input) {
        if(imei!=null)
        {
            String message = viewModel.getClientMessage() + "\r\nFrom imei - "+imei+" : " + input + "\n" + repeatChar();
            Platform.runLater(() -> viewModel.setClientMessage(message));
        }
        else {
            String message = viewModel.getClientMessage() + "\r\nReceived imei - : " + input + "\n" + repeatChar();
            Platform.runLater(() -> viewModel.setClientMessage(message));
        }
    }

    /**
     * <h1>Log</h1>
     * <p>Given String is being written to log file.</p>
     * @param data the parameter is a received data
     * */
    private void Log(String data) {
        logger.PrintToLOG("\tcodec             : " + data.substring(16, 18));
        logger.PrintToLOG("\tNumber of Records : " + GetNumberOfRecords(data));
        logger.PrintToLOG("\tAVL data          : " + data + "\n");
    }

    /**
     * <h1>Set Running</h1>
     * <p>Sets flag to run or stop while loop in order to interrupt the thread.</p>
     * */
    void setRunning() {
        this.flag = false;
    }

    /**
     * <h1>Repeat Char</h1>
     * <p>Repeats the '=' character multiple times.</p>
     * @return String is being returned.
     * */
    private String repeatChar() {
        char[] data = new char[50];
        Arrays.fill(data, '=');
        return new String(data);
    }

    /**
     * <h1>Get Time</h1>
     * <p>Gets time when method is being called</p>
     * @return Time in String format
     * */
    private String GetTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime.toString();
    }

}
