package Server.TCP;

import Primary.Converter;
import Primary.Crc16;
import Primary.MyLogger;
import Server.ImeiValidation;
import Server.TCP.View.TCPViewModel;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import javafx.application.Platform;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

import java.time.Instant;
import java.util.Arrays;

import static Primary.Converter.BytesArrayToHex;
import static Primary.ANSICOLORS.*;

/**
 * <h1>Netty TCP listener handler</h1>
 *
 * <p>This handler is responsible for all incoming and outcoming information for all devices,
 * The each connection has it's own channel, when new connection arrives the Channel Active will be invoked.
 * All Netty Override functions listens to events.</p>
 *
 * <p>The netty handlers don't need any initialization or threads
 * because all functions works synchronize and listens only for events, the constructor is optional.</p>
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<byte[]>{
    private MyLogger myLogger;
    private Logger LOGGER = Logger.getLogger(TcpServerHandler.class);
    private TCPViewModel viewModel;
    private Channel channel;
    private String imei;
    private boolean receivedImei = false;
    private Crc16 crc16;

    /**
     * <h1>TCP server handler construct</h1>
     *
     * <p>The tcp handler takes one parameter view model.</p>
     *
     * @param viewModel requires to display and TCP listener gui
     * */
    public TcpServerHandler(TCPViewModel viewModel)
    {
        super(true);
        crc16 = new Crc16();
        this.viewModel = viewModel;

    }

    /**
     * <h1>Channel Active</h1>
     *
     * <p>As soon the new connection arrives this method will be invoked. Telling us that the specific ip has connected.
     * We can use the channel which is the connected user/device for later purposes.</p>
     *
     * @param ctx ctx Requires to communicate with server
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.channel = ctx.channel();
        System.out.println(ANSI_GREEN+"Channel connected -> "+ctx.channel()+ANSI_RESET);
        LOGGER.info("Channel connected -> "+ctx.channel());
    }

    /**
     * <h1>Channel Inactive</h1>
     *
     * <p>As soon the connection disconnects this method will be invoked. Telling us that the specific ip has disconnected.</p>
     *
     * @param ctx ctx Requires to communicate with server
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ANSI_RED+channel.localAddress()+" has disconnected."+ANSI_RESET);
        LOGGER.info(channel.localAddress()+" has disconnected.");
    }

    /**
     * <h1>Get An Int</h1>
     *
     * <p>For converting bytes to int</p>
     *
     * @param receivedBytes bytes we want to convert to int
     *
     * @return returns an int value
     */
    private int getAnInt(byte[] receivedBytes) {
        return receivedBytes[1] & 0xFF | (receivedBytes[0] & 0xFF) << 8;
    }

    /**
     <h1>Channel Read 0</h1>
     *
     * <p>this method is responsible for reading all incoming data, since this is tcp listener the channel reader reads
     * incoming data as plain objects, later on we convert the object into the bytes for further reading.
     * This method receives the new imei and checks if its valid imei, if true is returned it continues, otherwise it does nothing.
     * If imei is valid then the reading begins, once the reading process is finished the handler sends the ack back to device.</p>
     *
     * @param ctx Requires to communicate with server
     * @param msg object that we receive from the sender
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
        try{
        System.out.println("Message length ; "+ msg.length);

        int imeiGivenLength;


            byte[] bytes;
            if(!receivedImei)
        {
            imeiGivenLength = getAnInt(msg);

            if(msg.length<20)
                imei = readImei(msg);

            if(ImeiValidation.ImeiIsValid(imei) && imei != null && imeiGivenLength==imei.length() && msg.length < 20)
            {
                receivedImei = true;
                PrintToDisplay("New imei",imei);
                bytes = new byte[]{0x01};
                InitializeLogFile();
                SendResponse(ctx, bytes);
                myLogger.PrintToLog("Time: "+Instant.now()+"\nIMEI: "+imei);
            }
            else{
                PrintLine("Not imei\n",ANSI_RED);

                SendResponse(ctx, msg);
            }
        }

        else if(msg.length>20){
            String receivedDataString =  BytesArrayToHex(msg, msg.length);
            PrintReceivedDataAndResponse(receivedDataString);

            myLogger.PrintToLog("\tAVL Packet sent ↓");
            myLogger.PrintToLog("\tcodec             : " + Converter.BytesArrayToHex(new byte[]{msg[8]}));
            myLogger.PrintToLog("\tNumber of Records : " + msg[9]);
            myLogger.PrintToLog("\tAVL data          : " + receivedDataString + "\n");

            bytes = new byte[]{0,0,0, msg[9]};
            SendResponse(ctx, bytes);
        }}
        catch (Exception e)
        {
            System.err.println("Error occurred in read function : "+e.getMessage());
        }
        finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
    }

    /**
     * <h1>Print Response</h1>
     * <p>Method used to print out the response</p>
     * @param receivedDataString for printing to console
     */
    private void PrintReceivedDataAndResponse(String receivedDataString) {
        int crc = CRC(receivedDataString);

        PrintLine(" <- "+channel.localAddress()+" "+imei+" | ",ANSI_BLUE);
        PrintLine(""+ Instant.now(),ANSI_YELLOW);
        PrintLine("\n\tReceived :"+receivedDataString,ANSI_WHITE);
        PrintLine(" Crc16 = "+Integer.toHexString(crc)+"\n",ANSI_PURPLE);

        PrintToDisplay(imei+" Sends",receivedDataString);
    }

    /**
     * <h1>Exception Caught</h1>
     *
     * <p>Listens for any exception while listening, sending, connecting etc.
     * If any exception is being thrown in Netty Override functions, they will be pass through here.
     * If any exception thrown the server will simply close.</p>
     *
     * @param ctx Requires to communicate with server
     * @param cause exception object
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
        LOGGER.error("Exception occurred ! message : ",cause);
    }

    /**
     * <h1>Print To Display</h1>
     * <p>Method uses bytes and global ViewModel to print the data for the user.</p>
     * @param title to print imei
     * @param body to print received message
     */
    public void PrintToDisplay(String title, String body)
    {
        String message = "Too many bytes to display in console. Check logs to see the packets.";
        try {
            message = viewModel.getClientMessage() + "\r\n"+title+" : " + body + "\n" + repeatChar();
        }catch (OutOfMemoryError e)
        {
            System.err.println("Out of memory in console display"+e.getMessage());
        }finally {
            String finalMessage = message;
            Platform.runLater(() -> viewModel.setClientMessage(finalMessage));
        }
    }

    /**
     * <h1>Send Response</h1>
     * <p>Sends response back to the sender with ack </p>
     * @param ctx Requires to communicate with server
     * @param bytes for sending bytes back to the imei
     */
    private void SendResponse(ChannelHandlerContext ctx, byte[] bytes) {
        ChannelFuture cf = ctx.write(Unpooled.copiedBuffer(bytes));
        ctx.flush();

        if (cf.isSuccess()) {
            try {
                ArrayUtils.reverse(bytes);
                String respond = Arrays.toString(bytes);
                PrintLine(" -> "+channel.localAddress()+" "+imei+" | ", ANSI_GREEN);
                PrintLine(""+Instant.now(),ANSI_YELLOW);
                PrintLine("\n\tSending response : " + respond+"\n",ANSI_CYAN);
                myLogger.PrintToLog("Response: "+ respond+"\n"+repeatChar());
            } catch (Exception e) {
                LOGGER.warn("The message was sent successfully, but error occurred : ",e.getCause());
            }
        } else {
            System.err.println("Send failed: " + cf.cause());
            LOGGER.error("Send failed: " + cf.cause());
        }
    }

    private String repeatChar() {
        char[] data = new char[50];
        Arrays.fill(data, '=');
        return new String(data);
    }

    private void PrintLine(String text, String color)
    {
        System.out.print(color+text+ANSI_RESET);
    }


    /**
     * <h1>Read Imei</h1>
     * <p>Reads imei from given bytes.</p>
     * @param bytes the bytes that contains only imei
     * @return string of imei
     */
    private String readImei(byte[] bytes)
    {
        byte[] imeiBytes =  Arrays.copyOfRange(bytes, 2, bytes.length);
        return Converter.ReadImei(BytesArrayToHex(imeiBytes,imeiBytes.length));
    }

    /**
     * <h1>CRC</h1>
     * <p>Calculates CRC of received data</p>
     *
     * @param str the parameter is a received hex data
     * @return int of crc16
     */
    private int CRC(String str) {
        str = str.substring(16, str.length() - 8);
        byte[] bytes = Converter.StringToByteArray(str);
        return crc16.getCRC(bytes);
    }

    private void InitializeLogFile()
    {
        myLogger = new MyLogger( System.getProperty("user.home") + "/Desktop" + "/Logs/TCPLogs/" + imei);
    }

}
