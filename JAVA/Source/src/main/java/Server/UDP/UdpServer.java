package Server.UDP;


import Server.UDP.View.UDPViewModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * <h1>UDP Server</h1>
 * <p>The Netty Udp Server-listener for listening all incoming packets.</p>
 */
public class UdpServer {

    private int port;
    private UDPViewModel viewModel;
    private final EventLoopGroup nioEventLoopGroup;
    private ChannelFuture channelFuture;

    /**
     * <h1>UDP server construct</h1>
     * <p>The udp server requires two parameters port and view model. </p>
     * @param port the parameter is required to start a server on specified port.
     * @param viewModel to display results for the user.
     * */
    public UdpServer(UDPViewModel viewModel, int port) {
       this.port = port;
       this.viewModel = viewModel;
        nioEventLoopGroup = new NioEventLoopGroup();
    }

    /**
     * <h1>Run server</h1>
     * <p>The main method that initializes and runs the udp server, we attach to Bootstrap group, channel, handler.
     * Latter on we start the server by binding with a port and sync.</p>
     */
    public void run() {
        System.out.println("UDP Server is starting.");

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast("UDP LOGS", new LoggingHandler(LogLevel.INFO));
                            channel.pipeline().addLast(new StringEncoder(), new StringDecoder());
                            channel.pipeline().addLast(
                                    new UdpServerHandler(viewModel));
                        }
                    });
            channelFuture = bootstrap.bind(port).sync();

        }
        catch (InterruptedException e) {
            System.err.println("UDP listener was interrupted and shutted down");
            e.getCause();
        }
    }

    /**
     * <h1>Stop Server</h1>
     * <h1>Stops the server for listening any incoming packets, the shut down process has delay to shut down gracefully.</h1>
     */
    public void StopServer()
    {
        try {
            nioEventLoopGroup.shutdownGracefully().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
