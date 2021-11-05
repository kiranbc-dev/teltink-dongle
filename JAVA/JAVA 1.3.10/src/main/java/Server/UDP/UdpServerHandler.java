package Server.UDP;

import Primary.Converter;
import Primary.MyLogger;
import Server.ImeiValidation;
import Server.UDP.View.UDPViewModel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import javafx.application.Platform;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

import static Primary.ANSICOLORS.*;
import static Primary.Converter.BytesArrayToHex;


/**
 * <h1>Netty UDP listener handler</h1>
 *
 * <p>This handler is responsible for all incoming and outcoming information for all devices,
 * since udp have no <i>connection</i> and only receives packets, there is no reason to create one.
 * As soon packet is received the <b>channelRead0</b> reads the input and sends the response back to the sender.</p>
 *
 * <p>Note : The netty handlers don't need any initialization or threads
 * because all functions works synchronize and listens only for events, the constructor is optional.</p>
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private UDPViewModel viewModel;
    private String imei;
    private MyLogger myLogger;

    /**
     * <h1>UdpServerHandler constructor</h1>
     *
     * <p>Initializes ViewModel and logger for outputs, this constructor not mandatory.</p>
     *
     * @param viewModel Requires to write the output back to the console.
     */

    UdpServerHandler(UDPViewModel viewModel)
    {
        this.viewModel = viewModel;
        myLogger = new MyLogger(System.getProperty("user.home") + "/Desktop" + "/Logs/UDPLogs");
    }


    /**
     * <h1>Exception Caught</h1>
     *
     * <p>Listens for any exception while listening, sending, connecting etc.
     * If any exception is being thrown in Netty Override functions, they will be pass through here.
     * If any exception thrown the server will simply close.</p>
     *
     * @param ctx Requires to communicate with server
     * @param e exception object
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
    {
        ctx.channel().close();
    }

    /**
     <h1>Channel Read 0</h1>
     *
     * <p>this method is responsible for reading all incoming data, since this is Udp listener the channel reader reads
     * incoming data as Datagram packets, later on we convert the packet into the bytes for further reading. </p>
     *
     * @param ctx Requires to communicate with server
     * @param datagramPacket packet that we receive from the sender
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket)
    {
            ByteBuf message = datagramPacket.content();

            byte[] bytes = getBytes(message);
            imei = readImei(Arrays.copyOfRange(bytes,8,23));

            if(ImeiValidation.ImeiIsValid(imei))
            {
                message = Unpooled.copiedBuffer(convertToMessage(bytes));
                ctx.channel().writeAndFlush(new DatagramPacket(message, datagramPacket.sender()));
                printToDisplay(bytes);
            }

    }

    /**
     * <h1>Print To Display</h1>
     * <p>Method uses bytes and global ViewModel to print the data for the user.</p>
     * @param bytes the parameter needed to convert bytes to String for printing the data
     */
    private void printToDisplay(byte[] bytes) {
        String finalData = BytesArrayToHex(bytes,bytes.length);
        Platform.runLater(() -> viewModel.setClientMessage(viewModel.getClientMessage() + "\r\n" + imei + " : " + finalData+"\r\n"));
    }

    /**
     * <h1>Convert To Message</h1>
     * <p>Method turns received bytes to respond message, the udp server must responde ack with
     * packet : length, Id, packet type, avl packet id, number of records.</p>
     *
     * @param bytes requires for reading received data to get the needed info to create ack
     *
     * @return returns bytes for sending the response
     */
    private byte[] convertToMessage(byte[] bytes) {
        byte[] responseBytes = new byte[7];
        System.arraycopy(bytes,0,responseBytes,0,6);
        responseBytes[6] = bytes[bytes.length-1];
        System.out.println(ANSI_CYAN+"RESPONSE : "+ Arrays.toString(responseBytes)+ANSI_RESET+"\n");
        myLogger.PrintToLog("RESPONSE SEND : [" + Arrays.toString(responseBytes) + "]\n");
        return responseBytes;
    }

    /**
     * <h1>Get Bytes</h1>
     * <p>Reads the received packet and converting to bytes</p>
     *
     * @param msg ByteBuf is for converting packet to bytes, since datagram packet has no direct cast to bytes array
     *
     * @return returns packet in bytes
     */
    private byte[] getBytes(ByteBuf msg) {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String printBytes = BytesArrayToHex(bytes,bytes.length);
        System.out.println(ANSI_PURPLE+"RECEIVED DATA : "+printBytes+ANSI_RESET);
        myLogger.PrintToLog("RECEIVED DATA : " + printBytes);
        return bytes;
    }

    /**
     * <h1>Read Imei</h1>
     * <p>Reads the imei</p>
     * @param bytes the parameter needed for converting bytes to string
     * @return returns String type imei
     */
    private String readImei(byte[] bytes)
    {
        System.out.println(ArrayUtils.toString(bytes));
        return Converter.ReadImei(BytesArrayToHex(bytes,bytes.length));
    }


}