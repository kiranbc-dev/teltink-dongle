package Server.TCP;

import Primary.ByteReader;
import Primary.Converter;
import Server.ImeiValidation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static Primary.Converter.BytesArrayToHex;

/**
 * <h1>Decoder</h1>
 * <p>The decoder class is first to read the incoming data packets from devices.
 * It's responsibility is to make sure that no data packet comes in separate parts.
 * Decoder class does not communicate with the sender,
 * it only checks whatever the data packet or imei is valid and good enough to forward to TCPServerHandler class.</p>
 */
public class Decoder extends ByteArrayDecoder {

    private static final Logger LOGGER = Logger.getLogger(TCPServer.class);
    private boolean receivedImei = false;
    private byte[] unfinishedBytes = new byte[0];

    /**
     * <h1>Decode</h1>
     * <p>Main function that gets the incoming message from the sender.
     * The function checks if imei is valid and passes to handler.
     * Later on, when handler starts sending ack back to the sender, the decode function does the check ups in between,
     * if data packet length is really as it says in the packet header.</p>
     * @param ctx Channel Handler Context
     * @param msg ByteBuf, all incoming messages comes in ByteBuf variable
     * @param out a list of objects that is passed to handler.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int readableBytes = msg.readableBytes();
        byte[] bytes = new byte[readableBytes];
        msg.getBytes(0, bytes);

        try{
            Thread.sleep(100);
            if(isImei(bytes))
            {
                out.add(bytes);
            }
            else{
                System.out.println("FMB SEND :: "+BytesArrayToHex(bytes,bytes.length)+"\n");
                int supposePacketLength = ByteReader.ReadValue(Arrays.copyOfRange(bytes,4,8));
                LOGGER.debug("FMB SEND :: "+BytesArrayToHex(bytes,bytes.length)+"\n");

                byte[] realPacketLength = Arrays.copyOfRange(bytes,8,bytes.length-4);
                System.out.println(supposePacketLength +" == "+ realPacketLength.length);

                if(supposePacketLength == realPacketLength.length)
                {
                    System.out.println("Passing bytes to handler ");
                    out.add(bytes);
                }
                else {
                    unfinishedBytes = ArrayUtils.addAll(unfinishedBytes, bytes);
                    System.out.println("The unfinishedBytes : "+Converter.BytesArrayToHex(unfinishedBytes));
                    supposePacketLength = ByteReader.ReadValue(Arrays.copyOfRange(unfinishedBytes,4,8));

                    realPacketLength = Arrays.copyOfRange(unfinishedBytes,8,unfinishedBytes.length-4);

                    System.out.println(supposePacketLength +" =UNFINISHED BYTES= "+ realPacketLength.length);
                    LOGGER.debug("Data packet length is not valid: expected = "+supposePacketLength+
                            " real length: "+ Arrays.toString(realPacketLength)+
                            "\nWaiting for more data..");

                    if(supposePacketLength == realPacketLength.length)
                    {
                        out.add(unfinishedBytes);
                        unfinishedBytes = null;
                    }
                }
            }

        }catch (Exception e)
        {
            System.out.println("Failed to read a packet. Corrupted or not enough bytes !");
            LOGGER.error("Failed to read a packet. Corrupted or not enough bytes ! Exception caught : "+e.getMessage());
        }

    }

    /**
     * <h1>Is Imei</h1>
     * <p>Checks if imei is valid.</p>
     * @param bytes gets imei in bytes
     * @return returns true of false
     */
    private boolean isImei(byte[] bytes) {
        int imeiGivenLength;
        if(!receivedImei)
        {
            imeiGivenLength = ByteReader.getAnInt(bytes);

            String imei = null;
            if(bytes.length<20)
                imei = readImei(bytes);

            if(ImeiValidation.ImeiIsValid(imei) && imei != null && imeiGivenLength == imei.length())
            {
                receivedImei = true;
                return true;
            }
            else{
                return false;
            }
        }
        return false;
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

}
