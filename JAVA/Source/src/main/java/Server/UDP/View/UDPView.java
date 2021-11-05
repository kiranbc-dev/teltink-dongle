package Server.UDP.View;

import Server.PortCheckUp;
import Server.UDP.UdpServer;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class UDPView implements FxmlView<UDPViewModel>, Initializable {
    public Button StopBtn;
    public Button OpenLogBtn;
    @FXML
    private ProgressBar LoadBar;

    @FXML
    private Button ListenBtn;
    @FXML
    private TextField PortField;
    @FXML
    private TextArea Console;

    @InjectViewModel
    private UDPViewModel viewModel;

    private UdpServer server;
    private PortCheckUp portCheckUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Console.textProperty().bind(viewModel.clientMessageProperty());
        portCheckUp = new PortCheckUp();
        Console.textProperty().addListener((observable, oldValue, newValue)
                -> Platform.runLater(() -> Console.setScrollTop(Console.getLength())));
        StopBtn.setDisable(true);
    }

    public void ListenStart() {

        if (portCheckUp.CheckPort((PortField.getText()))) {
            viewModel.setClientMessage("");
            StartServer();
            StopBtn.setDisable(false);
            PortField.setDisable(true);
            ListenBtn.setDisable(true);
        }
    }

    public void StopListening() {
        if (server != null) {
            LoadBar.setProgress(0);
            PortField.setDisable(false);
            ListenBtn.setDisable(false);
            server.StopServer();
            System.out.println("Shutting down UDP Server...");
            StopBtn.setDisable(true);
        }

    }

    public void OpenLog() {
        Desktop desktop = Desktop.getDesktop();
        String path = System.getProperty("user.home") + "/Desktop";
        File file = new File(path + "/Logs/UDPLogs.txt");
        try {
            if (file.exists()) {
                desktop.open(file);
            } else {
                ShowMessage("No logs was found, the logs should be created on desktop.");
            }
        } catch (IOException e) {
            ShowMessage("No folder or text file was found.");
        }
    }

    private void StartServer() {
        LoadBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        server = new UdpServer(viewModel, Integer.parseInt(PortField.getText()));
        server.run();
    }

    private void ShowMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("No logs found");
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }

}
