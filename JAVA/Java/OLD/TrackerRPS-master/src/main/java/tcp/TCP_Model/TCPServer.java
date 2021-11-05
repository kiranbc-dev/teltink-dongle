package TCP.TCP_Model;

import TCP.TCP_View.TCPViewModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>TCP server</h1>
 * <p>The TCP server, uses single thread to listen all incoming connections and puts them into the list of connections.
 * The thread is running until is being stopped or interrupted.
 * When Server is stopped all connections are stopped.</p>
 * */
public class TCPServer implements Runnable {
    private TCPViewModel viewModel;
    private int port;
    private Socket socket;
    private ServerSocket ss;
    private boolean running = true;
    private ArrayList<TCPConnection> tcpConnections;

    /**
     * <h1>TCP server construct</h1>
     * <p>The tcp server takes two parameters view model and port </p>
     * @param viewModel requires to display and TCP listener gui
     * @param port is required for server to listen all incoming connections
     * */
    public TCPServer(TCPViewModel viewModel, int port) {
        this.viewModel = viewModel;
        this.port = port;
    }

    /**
     * <h1>Run</h1>
     * <p>Runs the runnable thread to listen connections, it accepts a connection, if accept was successful,
     * the connection is added to tcpConnections list and runs the TCPConnection for further listening.
     * The server is running in while loop and stops when Running is set to false,
     * then break is called and shutdowns every connected client.</p>
     * */
    public void run() {
        tcpConnections = new ArrayList<>();
        try {
            ss = new ServerSocket(port);
            System.out.println("Listening on port : " + ss.getLocalPort());
            ExecutorService executorService;
            while (true) {
                executorService = Executors.newSingleThreadExecutor();
                socket = ss.accept();
                TCPConnection connection = new TCPConnection(socket, viewModel);
                executorService.submit(connection);
                tcpConnections.add(connection);
                if (!running) {
                    StopConnections();
                    break;
                }
            }
            executorService.shutdownNow();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.out.println("socket is closed");
        }
    }

    /**
     * <h1>Set Flag</h1>
     * <p>Function is being called when we want to interrupt server thread and stop it.</p>
     * @param flag the parameter sets whenever to true(run server) or false(stop server)
     * */
    public void setFlag(boolean flag) {
        running = flag;
        if (!running) {
            try {
                ss.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                System.out.println("Socket is " + socket.isClosed());
            }
        }
    }

    /**
     * <h1>Stop Connections</h1>
     * <p>Function is being called when we are stopping server,
     * this function iterates through every connection and stops it.</p>
     * */
    private void StopConnections() {
        if (!tcpConnections.isEmpty()) {
            for (TCPConnection connections : tcpConnections) {
                connections.setRunning();
            }
            tcpConnections.clear();
        }
    }
}
