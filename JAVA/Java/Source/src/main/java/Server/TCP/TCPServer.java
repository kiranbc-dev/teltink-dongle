package Server.TCP;

import Server.TCP.View.TCPViewModel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

import java.util.List;

import static Primary.ANSICOLORS.ANSI_RED;
import static Primary.ANSICOLORS.ANSI_RESET;

/**
 * <h1>TCP server</h1>
 * <p>The TCP server, uses single thread to listen all incoming connections and puts them into the list of connections.
 * The thread is running until is being stopped or interrupted.
 * When Server is stopped all connections are stopped.</p>
 * */
public class TCPServer implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(TCPServer.class);

    private static final boolean EPOLL = Epoll.isAvailable();
    private TCPViewModel viewModel;
    private int port;
    private EventLoopGroup eventLoopGroup;
    private String startServerMsg;

    /**
     * <h1>TCP server construct</h1>
     * <p>The tcp server takes two parameters view model and port </p>
     * @param viewModel requires to display and TCP listener gui
     * @param port is required for server to listen all incoming connections
     * */
    public TCPServer(TCPViewModel viewModel, int port) {
       this.viewModel = viewModel;
       this.port = port;
        startServerMsg = "TCP server listener successfully started on port ["+port+"]... listening for incoming connections.";
    }

    /**
     * <h1>Run server</h1>
     * <p>The main method that initializes and runs the tcp server, we attach to Bootstrap group, channel, ChildHandler.
     * Latter on we start the server by binding with a port and sync.</p>
     */
    @Override
    public void run() {
        eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            System.out.println(startServerMsg);
            LOGGER.info(startServerMsg);
            new ServerBootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(1024,16*1024,1024*1024))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new Decoder());
                            pipeline.addLast("handler",new TcpServerHandler(viewModel));
                        }
                    })
                    .bind(port).sync().channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to start server : ",e);
            e.printStackTrace();
        } finally {
            System.out.println(ANSI_RED+"Server shutting down."+ANSI_RESET);
            LOGGER.info("Server shutting down.");
            eventLoopGroup.shutdownGracefully();

        }
    }

    /**
     * <h1>Shutdown</h1>
     * <p>Method to invoke the server shutdown.</p>
     */
    public void Shutdown()
    {
        eventLoopGroup.shutdownGracefully();
    }

}
