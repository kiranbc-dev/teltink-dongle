package Source;

import Parser.View.ParserView;
import Parser.View.ParserViewModel;
import TCP.TCP_View.TCPView;
import TCP.TCP_View.TCPViewModel;
import UDP.UDP_View.UDPView;
import UDP.UDP_View.UDPViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryUIView implements FxmlView<PrimaryUIViewModel>, Initializable {

    public ImageView bonus;
    public Label SceneTitle;
    private ViewTuple<TCPView, TCPViewModel> tcpView;
    private ViewTuple<UDPView, UDPViewModel> udpView;
    private ViewTuple<ParserView, ParserViewModel> parserView;
    private short n =0;
    @FXML
    private Button TCPLOAD_btn;
    @FXML
    private Button LOADUDP_UI;
    @FXML
    private Button PARSELOAD_UI;

    @InjectViewModel
    private PrimaryUIViewModel viewModel;

    @FXML
    private Label MemoryUsageLabel;

    @FXML
    private BorderPane MainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parserView = FluentViewLoader.fxmlView(ParserView.class).load();
        tcpView = FluentViewLoader.fxmlView(TCPView.class).load();
        udpView = FluentViewLoader.fxmlView(UDPView.class).load();
        MemoryUsageLabel.textProperty().bind(viewModel.memoryDataProperty());
        //RunMemoryCheckUp();
    }

    @FXML
    private void LoadTCPUI() {
        MainPane.setCenter(tcpView.getView());
        LOADUDP_UI.setDisable(false);
        PARSELOAD_UI.setDisable(false);
        TCPLOAD_btn.setDisable(true);
        SceneTitle.setText("TCP Listener");
    }

    @FXML
    private void LoadUDPUI() {
        MainPane.setCenter(udpView.getView());
        LOADUDP_UI.setDisable(true);
        PARSELOAD_UI.setDisable(false);
        TCPLOAD_btn.setDisable(false);
        SceneTitle.setText("UDP Listener");
    }

    @FXML
    private void LoadParserUI() {
        MainPane.setCenter(parserView.getView());
        LOADUDP_UI.setDisable(false);
        PARSELOAD_UI.setDisable(true);
        TCPLOAD_btn.setDisable(false);
        SceneTitle.setText("Data Parser");
    }

    @FXML
    private void ActivateFrog() {

    }

    private void RunMemoryCheckUp() {
        Thread t = new Thread(() -> {
            Runtime runtime;
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runtime = Runtime.getRuntime();
                long total_mem = runtime.totalMemory();
                long free_mem = runtime.freeMemory();
                long used_mem = total_mem - free_mem;
                Platform.runLater(() -> viewModel.setMemoryData("" + used_mem / (1024 * 1024)));
            }
        });
        t.start();
    }

    @FXML
    private void Click() {
        n++;
        if(n==5)
            RunMemoryCheckUp();
        if(n==10)
            bonus.setVisible(true);

    }

}