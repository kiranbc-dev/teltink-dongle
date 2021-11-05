package Parser.ElemParsers;

import Parser.Models.AVL.AVLRecord;
import Parser.Models.AVL.AVLRecordCollection;
import Parser.Models.AVL.RecordHeader;
import Parser.Models.GPS.RecordGPS_Element;
import Parser.Models.IO.RecordIO_Element;
import Parser.Models.IO.RecordIO_Property;
import Primary.Converter;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Parser class for parsing data</h1>
 * <p>Parser class is responsible for all parsing of data from received TCP/UDP hex data to readable format.</p>
 */
public class Parser {
    private String HEX;
    private int codec;
    private int recordC;
    private String ProtocolType;
    private TextArea Console;
    private Converter converter;
    private AVLRecordCollection avlRecordCollection;
    private short n;
    /**
     * <h1>Parser constructor requires only two parameters</h1>
     *
     * @param ProtocolType a protocol is required to know which parsing functions to use.
     * @param Console      the output area for results
     */
    public Parser(String ProtocolType, TextArea Console) {
        this.ProtocolType = ProtocolType;
        this.Console = Console;
        converter = new Converter();
    }

    private List<AVLRecord> avlRecords = new ArrayList<>();

    private AVLRecordCollection CreateCollection() {
        return new AVLRecordCollection(codec, recordC, avlRecords);
    }

    private void CreateRecord() {
        AVLRecord AVLRecord;
        RecordHeader recordHeader = GetRecord_Data();
        RecordGPS_Element recordGPS_element = GetRecord_GPS();
        RecordIO_Element recordIOElement = GetRecord_IO();
        AVLRecord = new AVLRecord(recordHeader, recordGPS_element, recordIOElement);
        avlRecords.add(AVLRecord);
    }

    private RecordHeader GetRecord_Data() {
        HeaderParser headerParser = new HeaderParser(HEX);
        RecordHeader recordHeader = headerParser.ConvertRecord_Data();
        HEX = headerParser.getHEX();
        return recordHeader;
    }

    /**
     * <h1>Print Record to result Text Area</h1>
     * <p>A function that prints all results from Avl Record Collection list.</p>
     */
    private void PrintRecordToResultTA() {
        int i = 0;
        Console.appendText("\nCodec: " + avlRecordCollection.getCodecID());
        Console.appendText("\nRecord Count: " + avlRecordCollection.getRecordCount());
        for (AVLRecord record : avlRecordCollection.getRecordList()) {
            i++;
            Console.appendText("\n" + i + " Record======================================================");
            Console.appendText("\nRecord Timestamp:      " + record.getRecordHeader().getTimestamp());
            Console.appendText("\nRecord Priority:       " + record.getRecordHeader().getRecordPriority());
            Console.appendText("\nRecord GPS longitude: " + record.getRecordGPS_elements().getLongitude());
            Console.appendText("\nRecord GPS latitude  : " + record.getRecordGPS_elements().getLatitude());
            Console.appendText("\nRecord GPS altitude  : " + record.getRecordGPS_elements().getAltitude());
            Console.appendText("\nRecord GPS angle     : " + record.getRecordGPS_elements().getAngle());
            Console.appendText("\nRecord GPS satellites: " + record.getRecordGPS_elements().getSatellites());
            Console.appendText("\nRecord GPS Kmh       : " + record.getRecordGPS_elements().getKmh());

            Console.appendText("\nEventID : " + record.getRecordIO_elements().getEventID());
            Console.appendText("\nElement count : " + record.getRecordIO_elements().getElementCount());

            Console.appendText("\n\n1 byte elements ");
            for (RecordIO_Property data : record.getRecordIO_elements().getRecordIO_records().getByteList_1List()) {
                Console.appendText("\nID      : " + data.getID());
                Console.appendText("\n\tValue   : " + data.getValue());
            }

            Console.appendText("\n\n2 byte elements ");
            for (RecordIO_Property data : record.getRecordIO_elements().getRecordIO_records().getByteList_2List()) {
                Console.appendText("\nID      : " + data.getID());
                Console.appendText("\n\tValue   : " + data.getValue());
            }

            Console.appendText("\n\n4 byte elements ");
            for (RecordIO_Property data : record.getRecordIO_elements().getRecordIO_records().getByteList_4List()) {
                Console.appendText("\nID      : " + data.getID());
                Console.appendText("\n\tValue   : " + data.getValue());
            }

            Console.appendText("\n\n8 byte elements ");
            for (RecordIO_Property data : record.getRecordIO_elements().getRecordIO_records().getByteList_8List()) {
                Console.appendText("\nID      : " + data.getID());
                Console.appendText("\n\tValue   : " + data.getValue());
            }

            if(record.getRecordIO_elements().getRecordIO_records().getByteList_XList()!=null)
            {
                Console.appendText("\n\nX byte elements ");
                for (RecordIO_Property data : record.getRecordIO_elements().getRecordIO_records().getByteList_XList()) {
                    Console.appendText("\nID      : " + data.getID());
                    Console.appendText("\n\tLength   : " + data.getValue());
                    Console.appendText("\n\tData   : " + data.getData());
                }
            }

            Console.appendText("\n");
        }
    }

    /**
     * <h1>Get Record GPS</h1>
     * <p>A function collects gps data from HEX</p>
     *
     * @return returns Record GPS element object.
     */
    private RecordGPS_Element GetRecord_GPS() {
        GPSParser gpsParser = new GPSParser(HEX);
        RecordGPS_Element recordGPS_element;
        recordGPS_element = gpsParser.ConvertRecord_GPS();
        HEX = gpsParser.getHEX();
        return recordGPS_element;
    }

    /**
     * <h1>Get Record IO</h1>
     * <p>A function collects IO data from HEX</p>
     *
     * @return returns Record IO element object.
     */
    private RecordIO_Element GetRecord_IO() {
        IOParser ioParser = new IOParser(HEX);
        ioParser.setTakeByte(n);
        RecordIO_Element recordIOElement;
        recordIOElement = ioParser.GetElement();
        HEX = ioParser.getHEX();
        return recordIOElement;
    }

    /**
     * <h1>TCP Calculate Records</h1>
     * <p>A function reads and header of given HEX to get number of records</p>
     */
    private void TCPCalcRecords() {
        if (HEX.length() >= 90) {
            String preamble = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 8)));
            HEX = HEX.substring(8, HEX.length());
            String avlDataLength = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 8)));
            HEX = HEX.substring(8, HEX.length());

            Console.appendText("\nPreamble       : " + preamble);
            Console.appendText("\navl Data Length       : " + avlDataLength);
            codec = converter.convertStringToIntHex(HEX.substring(0, 2));
            if (codec <= 142) {
                if (codec == 142){
                    n = 4;
                }
                else{
                    n = 2;
                }
                System.out.println("Codec: " + codec);
                recordC = converter.convertStringToIntHex(HEX.substring(2, 4));
                HEX = HEX.substring(4, HEX.length());
            }
            else {
                ShowMessage("Parse Error", "Data is corrupted or codec is wrong");
            }
        } else {
            ShowMessage("Parse Error", "Data is corrupted or codec is wrong");
        }
    }

    /**
     * <h1>UDP Calculate Records</h1>
     * <p>A function reads and header of given HEX to get number of records</p>
     */
    private void UDPCalcRecords() {
        if (HEX.length() >= 100) {
            String PacketLength = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 4)));
            HEX = HEX.substring(4, HEX.length());
            String PacketIdentification = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 4)));
            HEX = HEX.substring(4, HEX.length());
            String PacketType = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 2)));
            HEX = HEX.substring(2, HEX.length());
            String PacketId = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 2)));
            HEX = HEX.substring(2, HEX.length());
            String ImeiLength = String.format("%d", converter.convertStringToIntHex(HEX.substring(0, 4)));
            HEX = HEX.substring(4, HEX.length());
            if (Integer.parseInt(ImeiLength) < 20) {
                Console.appendText("\nPacketLength       : " + PacketLength);
                Console.appendText("\nPacket Identification       : " + PacketIdentification);
                Console.appendText("\nPacket Type       : " + PacketType);
                Console.appendText("\nPacketId       : " + PacketId);
                Console.appendText("\nImei Length       : " + ImeiLength);

                String Imei = HEX.substring(0, 30);
                System.out.println("IMEI: " + converter.hexToAscii(Imei));
                HEX = HEX.substring(30, HEX.length());

                codec = converter.convertStringToIntHex(HEX.substring(0, 2));
                recordC = converter.convertStringToIntHex(HEX.substring(2, 4));
                HEX = HEX.substring(4, HEX.length());
                if (codec == 142){
                    n = 4;
                }
                else{
                    n = 2;
                }
            }
            else{
                ShowMessage("Something is wrong", "Imei length is too long.. imei length : " + Integer.parseInt(ImeiLength));
            }

        } else {
            ShowMessage("Parse Error", "Data is corrupted or codec is wrong");
        }
    }

    /**
     * <h1>TCP type</h1>
     * <p>Function calls to get number of records to create records and finally puts to Collections</p>
     */
    private void TCPType() {
        TCPCalcRecords();
        for (int i = 0; i < recordC; i++) {
            CreateRecord();
        }
        System.out.println(" Records :" + recordC);
        AssignCollection();
    }

    /**
     * <h1>UDP type</h1>
     * <p>Function calls to get number of records to create records and finally puts to Collections</p>
     */
    private void UDPType() {
        UDPCalcRecords();
        for (int i = 0; i < recordC; i++) {
            CreateRecord();
        }
        System.out.println(" Records :" + recordC);
        AssignCollection();
    }

    /**
     * <h1>Unknown type</h1>
     * <p>Function will simply <i>TRY</i> to read and parse the given HEX value, will read if codec 08 is found.
     * Function calls to get number of records to create records and finally puts to Collections
     * @param hex parameter used for reading and parsing data
     * </p>
     */
    private void UnknownType(String hex) {
        if(hex.contains("08") || hex.contains("8E")){
            try{
                HEX = hex.substring(hex.indexOf("08"), hex.length());
                codec = converter.convertStringToIntHex(HEX.substring(0, 2));
                recordC = converter.convertStringToIntHex(HEX.substring(2, 4));
                HEX = HEX.substring(4, HEX.length());
                if (codec == 142){
                    n = 4;
                }
                else{
                    n = 2;
                }
                for (int i = 0; i < recordC; i++) {
                    CreateRecord();
                }
                System.out.println(" Records :" + recordC);
                AssignCollection();
            }catch (StringIndexOutOfBoundsException e)
            {
                System.out.println("The inserted value is corrupted or incorrect.");
            }
        }
    }

    /**
     * <h1>Clear Avl record Collection</h1>
     * <p>nullify avl record collection </p>
     */
    public void ClearAVLRecordCollection() {
        avlRecordCollection = null;
    }

    /**
     * <h1>Parse Data</h1>
     * <p>Function uses switch to check what protocol type is selected to start parsing</p>
     * @param hex parameter used for reading and parsing data
     */
    public void ParseData(String hex) {
        if (!empty(hex)) {
            HEX = hex;
            switch (ProtocolType) {
                case "TCP":
                    TCPType();
                    break;
                case "UDP":
                    UDPType();
                    break;
                case "UNKNOWN":
                    UnknownType(hex);
                    break;
                default:
                    System.out.println("Protocol is invalid");
                    break;
            }
            if(avlRecordCollection != null)
            PrintRecordToResultTA();
        } else {
            System.err.println("HEX value is incorrect or null!");
        }
    }

    /**
     * <h1>Empty</h1>
     * <p>Checks if String is empty</p>
     * @param s parameter used for checking if String is null
     * @return returns true of false
     */
    private boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * <h1>Assign Collection</h1>
     * <p>Assigns created collection to AVL record collection object</p>
     */
    private void AssignCollection() {
        avlRecordCollection = CreateCollection();
    }

    /**
     * <h1>Show Message</h1>
     * <p>Shows error pop up message</p>
     *
     * @param header parameter for declaring message header
     * @param text   parameter for declaring message text
     */
    private void ShowMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.getDialogPane().setExpandableContent(new TextArea(text));
        alert.showAndWait();
    }
}
