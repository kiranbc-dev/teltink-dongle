package Server.TCP.View;

import Server.PortCheckUp;
import Server.TCP.TCPServer;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPView implements FxmlView<TCPViewModel>, Initializable {
    public Button ListenBtn;
    public Button StopBtn;
    public Button OpenLogBtn;
    private static final Logger LOGGER = Logger.getLogger(TCPView.class);
    @InjectViewModel
    private TCPViewModel viewModel;

    @FXML
    private TextArea Console;

    @FXML
    private ProgressBar LoadBar;

    @FXML
    private TextField PortField;

    private ExecutorService executorService;
    private TCPServer TCPServer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Console.textProperty().bind(viewModel.clientMessageProperty());
        StopBtn.setDisable(true);
        Console.textProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> Console.positionCaret(Console.getLength())));

        Console.textProperty().addListener((observable, oldValue, newValue) -> {
           if(Console.textProperty().get().length() > 1000000)
           {
               viewModel.setClientMessage("");
           }
        });
    }

    public void ListenStart() {
        PortCheckUp checkPortUp = new PortCheckUp();
        if (checkPortUp.CheckPort(PortField.getText())) {
            Platform.runLater(() -> viewModel.setClientMessage(""));
            StartServer();
            StopBtn.setDisable(false);
            ListenBtn.setDisable(true);
            PortField.setDisable(true);
            LOGGER.info("Start server initializing..");
        }
    }

    public void StopListening() {
        if (TCPServer != null) {
            LoadBar.setProgress(0);
            ListenBtn.setDisable(false);
            TCPServer.Shutdown();
            PortField.setDisable(false);
            executorService.shutdownNow();
            StopBtn.setDisable(true);
            LOGGER.info("Stopping server..");
        }
    }

    public void OpenLog() {
        Desktop desktop = Desktop.getDesktop();
        String path = System.getProperty("user.home") + "/Desktop";
        File file = new File(path + "/Logs/TCPLogs");
        try {
            if (file.exists()) {
                desktop.open(file);
            } else {
                ShowMessage("No logs found", "No logs was found, the logs should be created on desktop.");
            }
        } catch (IOException e) {
            ShowMessage("No logs found", "No logs was found, the logs should be created on desktop.");
        }
    }

    private void StartServer() {
        try {
            TCPServer = new TCPServer(viewModel,Integer.parseInt(PortField.getText()));
            LoadBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            executorService = Executors.newFixedThreadPool(1);
            executorService.submit(TCPServer);
            LOGGER.info("Server is starting at port ["+PortField.getText()+"]");
        } catch (Exception e) {
            LOGGER.fatal("Class not found ! read the following : "+e.getMessage());
            ShowMessage("Exception",e.getCause()+"");
        }
    }

    private void ShowMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }
}
