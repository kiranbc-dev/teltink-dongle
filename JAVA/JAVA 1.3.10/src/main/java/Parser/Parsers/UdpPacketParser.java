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
        ByteReader byteReader = new ByteReader(bytes);
        UdpAvlHead udpAvlHead = ReadUdpAvlPacketHeader(byteReader,bytes.length);
        List<AVLRecord> avlRecords = new ArrayList<> ();
        if(udpAvlHead != null) {
            for (int i = 0; i < udpAvlHead.getRecordC(); i++)
                avlRecords.add(new AVLRecord(byteReader,udpAvlHead.getCodec()));
            return new UdpAvlPacket(udpAvlHead, avlRecords);
        }
       else {
           return null;
        }
    }

    /**
     * <h1>Read Udp Avl Packet Header</h1>
     * <p>A function reads header of given bytes</p>
     * @return returns UdpHead object
     */
    private UdpAvlHead ReadUdpAvlPacketHeader(ByteReader byteReader,int length) {
        if (length >= 33) {
            try {
                int PacketLength = byteReader.ReadValue( 2);
                int PacketIdentification = byteReader.ReadValueUnsigned( 2);
                int PacketType = byteReader.ReadValue( 1);
                int PacketId = byteReader.ReadValueUnsigned( 1);
                int ImeiLength = byteReader.ReadValue( 2);
                String Imei = byteReader.ReadImei( ImeiLength);
                int codec = byteReader.ReadValueUnsigned( 1);
                int recordC = byteReader.ReadValueUnsigned( 1);

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
