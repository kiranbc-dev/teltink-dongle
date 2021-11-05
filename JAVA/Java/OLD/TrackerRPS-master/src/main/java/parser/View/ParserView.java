package Parser.View;

import Parser.ElemParsers.Parser;
import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


import java.net.URL;
import java.util.ResourceBundle;

public class ParserView implements FxmlView<ParserViewModel>, Initializable {
    private Parser parser;
    @FXML
    private TextArea InputArea;

    @FXML
    private TextArea Console;

    @FXML
    private ComboBox<String> ProtocolTypeCb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProtocolTypeCb.getItems().addAll("TCP", "UDP","UNKNOWN");
    }

    /**
     * <h1>Function to start parsing process</h1>
     * <p>It checks protocol which one is selected and starts parsing according to the selected one.</p>
     * */
    public void ParseStart()
    {

        System.out.println("selected: "+ ProtocolTypeCb.getValue());
        if(ProtocolTypeCb.getValue()!=null)
        {
            String text = InputArea.getText().trim();
            parser = new Parser(""+ ProtocolTypeCb.getValue(),Console);
            parser.ClearAVLRecordCollection();
            Console.setText("");
            parser.ParseData(text);

        }
    }
    /**
     * <h1>Function to clear input and result fields</h1>
     * */
    public void ParserClear()
    {
       Clear();
    }

    private void Clear()
    {
        if(parser!=null)
        {
            parser.ClearAVLRecordCollection();
        }
        Console.setText("");
        InputArea.setText("");
    }


}
