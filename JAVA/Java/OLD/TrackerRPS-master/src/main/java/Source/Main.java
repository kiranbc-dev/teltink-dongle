package Source;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    /**
     * <h1>Tracker Receive Parse Send v1.0</h1>
     * This program is Teltonika academy project
     * It listens TCP.TCP_View/UDP.UDP_View to receive packages from trackers,
     * logs them and you can parse data from them.
     * <p>
     * <b>Note:</b> The program is written with Java 8.0
     *
     * @version 1.2
     * @since   2018-05-03
     * @param args no arguments require
     */
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("user.dir"));
        Application.launch(args);
    }
    /**
     * <h1>Method that initialize the program</h1>
     * <p>Loads the view</p>
     * @param primaryStage this is the stage that initializes first setup
     *
     * */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TrackerRPS v1.2");
        final ViewTuple<PrimaryUIView, PrimaryUIViewModel> viewTuple = FluentViewLoader.fxmlView(PrimaryUIView.class).load();
        Parent root = viewTuple.getView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

    }
}
