package Source;

import Source.View.PrimaryUIView;
import Source.View.PrimaryUIViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileNotFoundException;


public class Main extends Application {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    /**
     * <h1>Automatic vehicle location representer v1.3.8</h1>
     * This program is Teltonika academy project
     * It listens TCP/UDP to receive packages from trackers,
     * logs them and you can parse data from them.
     * <p>
     * <b>Note:</b> The program is written with Java 8.0
     *
     * @param args no arguments require
     * @version 1.3.9
     * @since 2018-11-27
     */
    public static void main(String[] args) {
            PropertyConfigurator.configure("Properties/log4j.properties");
            Application.launch(args);
    }

    /**
     * <h1>Method that initialize the program</h1>
     * <p>Loads the view</p>
     *
     * @param primaryStage this is the stage that initializes first setup
     */
    @Override
    public void start(Stage primaryStage) {
        String JVersion = System.getProperty("java.version");
        LOGGER.info("\nAutomatic vehicle location representor v1.3.9 started...");
        LOGGER.info("Installed JAVA version: "+JVersion);
        if(JVersion.startsWith("10")) ShowMessage();

        Image icon = new Image(getClass().getResourceAsStream("/Images/teltonika_ico.png"));
        primaryStage.getIcons().add(icon);
        final ViewTuple<PrimaryUIView, PrimaryUIViewModel> viewTuple = FluentViewLoader.fxmlView(PrimaryUIView.class).load();
        Parent root = viewTuple.getView();

        primaryStage.setTitle("Automatic vehicle location representer v1.3.9");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
    /**
     * <h1>Show Message</h1>
     * <p>Shows error pop up message</p>
     */
    private void ShowMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Warning. Incompatible Java version.");
        alert.getDialogPane().setExpandableContent(new TextArea("AVL Representer is not incompatible with java 10. Please use Java 8 version instead."));
        alert.showAndWait();
    }

    private static void ShowMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }

}
