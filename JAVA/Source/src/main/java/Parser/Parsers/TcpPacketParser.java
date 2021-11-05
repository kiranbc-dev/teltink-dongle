package Parser.Parsers;

import Parser.Models.AVL.AVLRecord;
import Parser.Models.AVL.TcpAvlPacket.TcpAvlPacket;
import Parser.Models.AVL.TcpAvlPacket.TcpHead;
import Primary.ByteReader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TcpPacketParser {

    private static final Logger LOGGER = Logger.getLogger(Parser.class);

    /**
     * <h1>Create tcp packet</h1>
     * <p>Creates a tcp packet</p>
     * @param bytes for reading and creating packet from it
     * @return returns TcpAvlPacket
     */
    public TcpAvlPacket CreateTcpPacket(byte[] bytes) {
        TcpHead tcpHead = ReadTcpAvlPacketHeader(bytes);
        List<AVLRecord> avlRecords = new ArrayList<> ();
        if(tcpHead != null)
        {
            for (int i = 0; i < tcpHead.GetRecordCount(); i++) {
                avlRecords.add(new AVLRecord(bytes,tcpHead.GetCodec()));
            }
            return new TcpAvlPacket(tcpHead, avlRecords);
        }
        return null;
    }

   /* private void CreateCsv()
    {
        PrintWriter pw = new PrintWriter(new File("SomeImei.csv"));
        StringBuilder sb = new StringBuilder();
        for (AVLRecord record: tcpAvlPacket.GetCollection()) {
            sb.append(record.GetRecordGpsData().GetLatitude());
            sb.append(';');
            sb.append(record.GetRecordGpsData().GetLongitude());
            sb.append('\n');
            pw.write(sb.toString());
        }

        pw.close();
    }
*/
    /**
     * <h1>Read Tcp Avl Packet Header</h1>
     * <p>A function reads header of given bytes</p>
     * @param bytes requires for reading data
     * @return returns TcpHead object
     */
    private TcpHead ReadTcpAvlPacketHeader(byte[] bytes) {
        if (bytes.length >= 15) {
            return new TcpHead(ByteReader.ReadValue(bytes, 4),
                    ByteReader.ReadValue(bytes, 4),
                    ByteReader.ReadValueUnsigned(bytes, 1),
                    ByteReader.ReadValueUnsigned(bytes, 1));
        }
        else{
            ShowMessage();
            ByteReader.SetIndex(0);
            LOGGER.error("AVL too short " + bytes.length);
            return null;
        }
    }

    private void ShowMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Parse Error");
        alert.getDialogPane().setExpandableContent(new TextArea("Data is too short.."));
        alert.showAndWait();
    }
}
