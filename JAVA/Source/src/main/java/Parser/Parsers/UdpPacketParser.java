package Parser.Parsers;

import Parser.Models.AVL.AVLRecord;
import Parser.Models.AVL.UdpAvlPacket.UdpAvlHead;
import Parser.Models.AVL.UdpAvlPacket.UdpAvlPacket;
import Primary.ByteReader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

class UdpPacketParser {

    /**
     * <h1>Create udp packet</h1>
     * <p>Creates a udp packet</p>
     * @param bytes for reading and creating packet from it
     * @return returns UdpAvlPacket
     */
    UdpAvlPacket CreateUdpPacket(byte[] bytes) {
        UdpAvlHead udpAvlHead = ReadUdpAvlPacketHeader(bytes);
        List<AVLRecord> avlRecords = new ArrayList<> ();
        if(udpAvlHead != null) {
            for (int i = 0; i < udpAvlHead.getRecordC(); i++)
                avlRecords.add(new AVLRecord(bytes,udpAvlHead.getCodec()));
            return new UdpAvlPacket(udpAvlHead, avlRecords);
        }
       else {
           return null;
        }
    }

    /**
     * <h1>Read Udp Avl Packet Header</h1>
     * <p>A function reads header of given bytes</p>
     * @param bytes requires for reading data
     * @return returns UdpHead object
     */
    private UdpAvlHead ReadUdpAvlPacketHeader(byte[] bytes) {
        if (bytes.length >= 33) {
            try {
                int PacketLength = ByteReader.ReadValue(bytes, 2);
                int PacketIdentification = ByteReader.ReadValueUnsigned(bytes, 2);
                int PacketType = ByteReader.ReadValue(bytes, 1);
                int PacketId = ByteReader.ReadValueUnsigned(bytes, 1);
                int ImeiLength = ByteReader.ReadValue(bytes, 2);
                String Imei = ByteReader.ReadImei(bytes, ImeiLength);
                int codec = ByteReader.ReadValueUnsigned(bytes, 1);
                int recordC = ByteReader.ReadValueUnsigned(bytes, 1);

                if (ImeiLength < 20) {

                    return new UdpAvlHead(PacketLength, PacketIdentification, PacketType, PacketId, ImeiLength , Imei,codec,recordC);
                } else {
                    ShowMessage("Something is wrong", "Imei length is too long.. imei length : " + ImeiLength);
                    return null;
                }
            } catch (Exception e) {
                e.getCause();
                ShowMessage("Parse Error", "Data is corrupted or codec is wrong, try UNKNOWN");
                return null;
            }

        } else ShowMessage("Parse Error", "Data is corrupted or codec is wrong");
        return null;
    }

    private void ShowMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }
}
