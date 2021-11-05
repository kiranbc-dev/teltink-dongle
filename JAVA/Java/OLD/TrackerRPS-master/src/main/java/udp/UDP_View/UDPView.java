package UDP.UDP_View;

import Primary.PortCheckUp;
import UDP.UDP_Model.UDP_ListenerServer;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private ExecutorService executorService;
    private UDP_ListenerServer server;
    private PortCheckUp portCheckUp;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Console.textProperty().bind(viewModel.clientMessageProperty());
        portCheckUp = new PortCheckUp();
        Console.textProperty().addListener((observable, oldValue, newValue)
                -> Platform.runLater(() ->  Console.setScrollTop(Console.getLength())));
        StopBtn.setDisable(true);
    }

    public void ListenStart() {

            if(portCheckUp.CheckPort((PortField.getText())))
            {
                viewModel.setClientMessage("");
                StartServer();
                StopBtn.setDisable(false);
                PortField.setDisable(true);
                ListenBtn.setDisable(true);
            }
    }

    public void StopListening()
    {
        if(server != null)
        {
            LoadBar.setProgress(0);
            server.setFlag(false);
            PortField.setDisable(false);
            ListenBtn.setDisable(false);
            executorService.shutdownNow();
            System.out.println("Shutting down UDPServer...");
            StopBtn.setDisable(true);
        }

    }

    public void OpenLog()
    {
        Desktop desktop = Desktop.getDesktop();
        String path = System.getProperty("user.home") + "/Desktop";
        File file = new File(path+"/Logs/UDPLogs.txt");
        try {
            if(file.exists())
            {
                desktop.open(file);
            }
            else{
                ShowMessage("No logs found","No logs was found, the logs should be created on desktop.");
            }
        } catch (IOException e) {
            ShowMessage("No logs found","No folder or text file was found.");
        }
    }

    private void StartServer()
    {
        LoadBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        server = new UDP_ListenerServer(viewModel,Integer.parseInt(PortField.getText()));
        server.setFlag(true);
        executorService = Executors.newFixedThreadPool(1);
        executorService.submit(server);
    }

    private void ShowMessage(String header, String text)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }

}
