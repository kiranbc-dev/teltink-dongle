package UDP.UDP_Model;

import Primary.Converter;
import Primary.Logger;
import UDP.UDP_View.UDPViewModel;
import javafx.application.Platform;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * <h1>UDP Listener Server</h1>
 * <p>A UDP listener listens to all incoming connections port specified port.
 * Receives packet and then sends back the ack. </p>
 * */
public class UDP_ListenerServer extends Thread {

    private UDPViewModel viewModel;
    private DatagramSocket ds;
    private Logger logger;
    private boolean flag = true;
    private int port;
    private Converter converter;

    /**
     * <h1>UDP listener construct</h1>
     * <p>The udp connection requires two parameters port and view model. </p>
     * @param port the parameter is required to start a server on specified port.
     * @param viewModel to display results for user.
     * */
    public UDP_ListenerServer(UDPViewModel viewModel,int port) {
        this.setViewModel(viewModel);
        this.port = port;
        converter = new Converter();
        String path = System.getProperty("user.home") + "/Desktop";
        logger = new Logger(path+"/Logs/UDPLogs");
    }

    public UDP_ListenerServer() {
    }

    /**
     * <h1>Run function to start listener</h1>
     * <p>Simply runs the runnable thread to listen everything from client</p>
     * */
    public void run() {
            try {
                setDs(new DatagramSocket(port));
            } catch (SocketException e) {
                e.printStackTrace();
            }
            System.out.println("Datagram Socket initialized");
            listen();
    }

    /**
     * <h1>Listen</h1>
     * <p>Function for listening connected client and communicates with it.
     * The while loop is running until we set flag to false.</p>
     * */
    private void listen()
    {
        while (flag) {
            if(!flag)
            {
                ds.close();
                break;
            }
            try {
                byte[] bb = new byte[4096];

                DatagramPacket dp = new DatagramPacket(bb, bb.length);
                System.out.println("Packet created");

                System.out.println("Waiting for data from client");

                getDs().receive(dp);
                String str = new String(dp.getData(), 0, dp.getLength());
                int i = dp.getLength();
                byte[] data;
                data = dp.getData();
                InetAddress IPAddress = dp.getAddress();

                int port = dp.getPort();
                System.out.println("ByteArray: " + str);
                System.out.println();
                String msg = converter.BytesArrayToHex(data);

                msg = msg.replaceAll(" ", "");
                msg = msg.substring(0, i * 2);
                System.out.println("RESULT : " + msg);
                UDPPacket packet = FillPacket(msg.substring(0,50));
                PrintPacket(packet);
                logger.PrintToLOG("received : " + msg);
                SendResponse(packet, IPAddress, port);

                String finalData = msg;
                Platform.runLater(() -> getViewModel().setClientMessage(viewModel.getClientMessage() + "\r\n"+IPAddress+" : " + finalData));

            } catch (IOException e) {
                System.out.println("Socket is closed");
            }
        }
    }


    /**
     * <h1>Print Packet</h1>
     * <p>Function for printing the results from client, prints to text area and logs</p>
     * @param packet the parameter is UDP packet object.
     * */
    private void PrintPacket(UDPPacket packet)
    {
        System.out.println("Packet : " +
                "\nData Length: "+packet.getDataLength()+
                "\nIdentification : "+packet.getPacketIdentification()+
                "\nType: "+packet.getPacketType()+
                "\nID: "+packet.getPacketID()+
                "\nImei Length : "+packet.getActualImei().length()/2+
                "\nImei : "+packet.getActualImei()+
                "\nNumber Of Records: "+packet.getNumberOfData());
        logger.PrintToLOG("Packet : " +
                "\nData Length: "+packet.getDataLength()+
                "\nIdentification : "+packet.getPacketIdentification()+
                "\nType: "+packet.getPacketType()+
                "\nID: "+packet.getPacketID()+
                "\nImei Length : "+packet.getActualImei().length()/2+
                "\nImei : "+converter.ReadImei(packet.getActualImei())+
                "\nNumber Of Records: "+packet.getNumberOfData());
    }

    /**
     * <h1>Fill Packet</h1>
     * <p>Fills data to packet object for further usage.</p>
     * @param ReceivedData the parameter is HEX String type.
     * @return returns UDP packet object
     * */
    private UDPPacket FillPacket(String ReceivedData) {
        UDPPacket packet = new UDPPacket();
        packet.setDataLength(ReceivedData.substring(0, 4));
        packet.setPacketIdentification(ReceivedData.substring(4, 8));
        packet.setPacketType(ReceivedData.substring(8, 10));
        packet.setPacketID(ReceivedData.substring(10, 12));
        packet.setActualImei(ReceivedData.substring(16,46));
        packet.setNumberOfData(ReceivedData.substring(ReceivedData.length()-2,50));
        return packet;
    }

    /**
     * <h1>Send Response</h1>
     * <p>Function for sending output back to the client</p>
     * @param packet UDPPacket object
     * @param IPAddress InetAddress for sending to specific address
     * @param port receiver port is required.
     * */
    private void SendResponse(UDPPacket packet, InetAddress IPAddress, int port) {
        byte[] concatBytes = ArrayUtils.addAll(converter.toHexBytes("0005"),converter.toHexBytes(packet.getPacketIdentification()));
        concatBytes = ArrayUtils.addAll(concatBytes,converter.toHexBytes("01"));
        concatBytes = ArrayUtils.addAll(concatBytes,converter.toHexBytes(packet.getPacketID()));
        concatBytes = ArrayUtils.addAll(concatBytes,converter.toHexBytes(packet.getNumberOfData()));
        DatagramPacket sendPacket = new DatagramPacket(concatBytes, concatBytes.length, IPAddress, port);
        try {
            getDs().send(sendPacket);
        } catch (IOException e) {
            System.out.println("IO exception sending response");
        }
        logger.PrintToLOG("Response send: ["+converter.BytesArrayToHex(concatBytes)+"]\n");
    }

    /**
     * <h1>Set Flag</h1>
     * <p>Function sets flag to true of false. This function is being called when listener is wanted to stop or start.</p>
     * @param flag the parameter declares flag to true or false
     * */
    public void setFlag(boolean flag)
    {
        this.flag = flag;
        if(!flag)
        {
            ds.close();
        }
    }

    private UDPViewModel getViewModel() {
        return viewModel;
    }

    private void setViewModel(UDPViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public DatagramSocket getDs() {
        return ds;
    }

    public void setDs(DatagramSocket ds) {
        this.ds = ds;
    }


}