package Primary;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CanYouSeeMyPort{

    private int code = 0;

    int CheckPortIfAvailable(String port) {

       /* while (code != 200) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        return 200;
    }

    void HeyListen(String port) {
        Thread thread = new Thread(() -> {
            URL u;
            try {
                u = new URL("http://canyouseeme.org");
                HttpURLConnection con = (HttpURLConnection) u.openConnection();
                con.setRequestMethod("GET");
                Map<String, String> parameters = new HashMap<>();
                parameters.put("port", port);
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();
                con.connect();
                code = con.getResponseCode();
                System.out.println(code);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        thread.start();
    }

    static class ParameterStringBuilder {
        static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException{
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }

    public static boolean pingHost(int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("212.59.13.226", port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}