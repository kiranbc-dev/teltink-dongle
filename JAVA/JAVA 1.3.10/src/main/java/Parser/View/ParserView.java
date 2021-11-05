package Parser.View;

import Parser.Parsers.Parser;
import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ParserView implements FxmlView<ParserViewModel>, Initializable {
    @FXML
    private TextArea InputArea;

    @FXML
    private TreeTableView<String> ResultTreeTableView;

    @FXML
    private TextArea Console;

    @FXML
    private ComboBox<String> ProtocolTypeCb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProtocolTypeCb.getItems().addAll("TCP", "UDP", "UNKNOWN");

    }

    /**
     * <h1>Function to start parsing process</h1>
     * <p>It checks protocol which one is selected and starts parsing according to the selected one.</p>
     */
    public void ParseStart() {
        if (ProtocolTypeCb.getValue() != null) {
            String text = InputArea.getText().trim();
            Parser parser = new Parser("" + ProtocolTypeCb.getValue(), Console);
            Console.setText("");
            parser.ParseData(text);
        }
        Fill();
    }

    /**
     * <h1>Function to clear input and result fields</h1>
     */
    public void ParserClear() {
        Console.setText("");
        InputArea.setText("");
    }

    public void Fill() {
        TreeItem<String> root, avl, record;

        root = new TreeItem<>();
        root.setExpanded(true);

        avl = makeBranch("AVL data", root);
        makeBranch("avl 1", avl);
        makeBranch("avl 2", avl);
        makeBranch("avl 3", avl);

        record = makeBranch("Record data", root);
        makeBranch("record 1", record);
        makeBranch("record 2", record);
        makeBranch("record 3", record);
        makeBranch("record 4", record);
        makeBranch("record 5", record);
        makeBranch("record 6", record);

        ResultTreeTableView = new TreeTableView<>(root);
        ResultTreeTableView.setShowRoot(false);

    }

    public TreeItem<String> makeBranch(String title, TreeItem<String> root)
    {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        root.getChildren().add(item);
        return item;
    }

}
