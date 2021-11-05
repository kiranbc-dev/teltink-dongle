package Server;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;


/**
 * <h1>Port Check Up class</h1>
 * <p>Holds validations for checking availability of given port</p>
 */
public class PortCheckUp {
    /**
     * <h1>Check port</h1>
     * <p>Checks if given port number, is reachable and is not in use</p>
     *
     * @param StringPort port is String but it converts to int later on
     * @return true of false
     */
    public boolean CheckPort(String StringPort) {
        if (IsInteger(StringPort)) {
            if (isPortInUse(Integer.parseInt(StringPort))) {
                //CanYouSeeMyPort canYouSeeMyPort = new CanYouSeeMyPort();
                if (/*canYouSeeMyPort.CheckPortIfAvailable(StringPort) == 200*/ true) { //Can you see my port is currently not available
                    return true;
                }
            }
        } else {
            ShowMessage("Port is invalid", "Port is not available.");
            return false;
        }
        return false;
    }

    /**
     * <h1>Is Integer</h1>
     * <p>Checks if given String is contains numbers and no characters</p>
     *
     * @param s String parameter to check
     * @return true of false
     */
    private boolean IsInteger(String s) {
        return IsInteger(s, 10);
    }

    private boolean IsInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    private boolean isPortInUse(int port) {
        if (port <= 65535 && port > 0) {
            return TCP_InUse(port) && UDP_InUse(port);
        } else {
            ShowMessage("Port is invalid", "Given port is too long. Maximum length of port can be 65535");
            return false;
        }
    }

    /**
     * <h1>TCP In use</h1>
     * <p>Checks if port is in use</p>
     *
     * @param port parameter is int value
     * @return returns true of false
     */
    private boolean TCP_InUse(int port) {
        boolean result = false;
        try {
            new ServerSocket(port).close();
            result = true;

        } catch (SocketException e) {
            // Could not connect.
            System.out.println("Could not establish server");
            ShowMessage("Start server error", "Port is not avaiable.");

        } catch (IOException e) {
            // IO exception
            System.out.println("IO error");
        }

        return result;
    }

    /**
     * <h1>UDP In use</h1>
     * <p>Checks if port is in use</p>
     *
     * @param port parameter is int value
     * @return returns true of false
     */
    private boolean UDP_InUse(int port) {
        try {
            new DatagramSocket(port).close();
            return true;
        } catch (SocketException e) {
            // Could not connect.
            System.out.println("Could not establish server");
            ShowMessage("Start server error", "Port is not available.");

        }
        return false;
    }


    /**
     * <h1>Show Message</h1>
     * <p>Shows error pop up message</p>
     *
     * @param header parameter for declaring message header
     * @param text   parameter for declaring message text
     */
    private void ShowMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }

}
