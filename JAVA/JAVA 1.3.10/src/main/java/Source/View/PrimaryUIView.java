package Source.View;

import Parser.View.ParserView;
import Parser.View.ParserViewModel;
import Server.TCP.View.TCPView;
import Server.TCP.View.TCPViewModel;
import Server.UDP.View.UDPView;
import Server.UDP.View.UDPViewModel;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryUIView implements FxmlView<PrimaryUIViewModel>, Initializable {
    private static final Logger LOGGER = Logger.getLogger(PrimaryUIView.class);
    public Label SceneTitle;
    private ViewTuple<TCPView, TCPViewModel> tcpView;
    private ViewTuple<UDPView, UDPViewModel> udpView;
    private ViewTuple<ParserView, ParserViewModel> parserView;
    @FXML
    private Button TcpLoadBtn;
    @FXML
    private Button UdpLoadBtn;
    @FXML
    private Button ParserLoadBtn;
    @InjectViewModel
    private PrimaryUIViewModel viewModel;

    @FXML
    private BorderPane MainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getParserView();
        getTcpView();
        getUdpView();
    }

    /**
     Loads Udp stage
     */
    private void getUdpView() {
        try {
            udpView = FluentViewLoader.fxmlView(UDPView.class).load();
        } catch (Exception e) {

            LOGGER.error("UdpView : "+e.getCause());
        }
    }

    /**
     Loads Tcp stage
     */
    private void getTcpView() {
        try {
            tcpView = FluentViewLoader.fxmlView(TCPView.class).load();
        } catch (Exception e) {
            LOGGER.error("TcpView : "+e.getCause());
        }
    }

    /**
     Loads Parser stage
     */
    private void getParserView() {
        try {
            parserView = FluentViewLoader.fxmlView(ParserView.class).load();
        } catch (Exception e) {
            LOGGER.error("ParserView : "+e.getCause());
        }
    }


    /**
     Loads and shows Tcp stage
     */
    @FXML
    private void LoadTCPUI() {
        MainPane.setCenter(tcpView.getView());
        UdpLoadBtn.setDisable(false);
        ParserLoadBtn.setDisable(false);
        TcpLoadBtn.setDisable(true);
        SceneTitle.setText("TCP Listener");
    }

    /**
     Loads and shows Udp stage
     */
    @FXML
    private void LoadUDPUI() {
        MainPane.setCenter(udpView.getView());
        UdpLoadBtn.setDisable(true);
        ParserLoadBtn.setDisable(false);
        TcpLoadBtn.setDisable(false);
        SceneTitle.setText("UDP Listener");
    }

    /**
     Loads and shows Parser stage
     */
    @FXML
    private void LoadParserUI() {
        MainPane.setCenter(parserView.getView());
        UdpLoadBtn.setDisable(false);
        ParserLoadBtn.setDisable(true);
        TcpLoadBtn.setDisable(false);
        SceneTitle.setText("AVL Parser");
    }

}