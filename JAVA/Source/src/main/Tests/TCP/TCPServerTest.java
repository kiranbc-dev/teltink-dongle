package TCP;

import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;


public class TCPServerTest {

    @Test
    public void ServerSetupTest() throws IOException {
        //Arrange
        int port = 1;
        ServerSocket ss = null;

        //Act
            ss = new ServerSocket(port);

        //Assert
            assertEquals(1, ss.getLocalPort());

        }


    @Test
    public void fragmentedStringTest() {
        EmbeddedChannel channel = new EmbeddedChannel(new StringDecoder(StandardCharsets.UTF_8));
        channel.writeInbound(Unpooled.wrappedBuffer(new byte[]{(byte)0xE2,(byte)0x98,(byte)0xA2}));
        String myObject = channel.readInbound();
        // Perform checks on your object
        assertEquals("☢", myObject);
    }

}
