package Parser.Parsers;

import Parser.Models.AVL.AVLRecord;
import Parser.Models.AVL.AvlPacket;
import Parser.Models.AVL.TcpAvlPacket.TcpAvlPacket;
import Parser.Models.AVL.UdpAvlPacket.UdpAvlPacket;
import Primary.ByteReader;
import Primary.Converter;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

/**
 * <h1>Parser class for parsing data</h1>
 * <p>Parser class is responsible for all parsing of data from received TCP/UDP hex data to readable format.</p>
 */
public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    private String ProtocolType;
    private UdpPacketParser udpPacketParser = new UdpPacketParser();
    private TcpPacketParser tcpPacketParser = new TcpPacketParser();
    private UnknownPacketParser unknownPacketParser = new UnknownPacketParser();
    private TextArea Console;

    /**
     * <h1>Parser constructor requires only two parameters</h1>
     *
     * @param ProtocolType a protocol is required to know which parsing functions to use.
     * @param Console      the output area for results
     */
    public Parser(String ProtocolType, TextArea Console) {
        this.ProtocolType = ProtocolType;
        this.Console = Console;
    }

    /**
     * <h1>Parse Data</h1>
     * <p>Function uses switch to check what protocol type is selected to parse and print.</p>
     *
     * @param hex parameter used for converting to byte array
     */
    public void ParseData(String hex) {
            byte[] bytes = Converter.hexStringToByteArray(hex);

            LOGGER.info(ProtocolType + "- Parsing AVL : \n" + hex);
            switch (ProtocolType) {
                case "TCP":
                    PrintTcpPacket(tcpPacketParser.CreateTcpPacket(bytes));
                    break;
                case "UDP":
                    PrintUdpPacket(udpPacketParser.CreateUdpPacket(bytes));
                    break;
                case "UNKNOWN":
                    PrintUnknownPacket(unknownPacketParser.CreateUnknownPacket(bytes));
                    break;
                default:
                    break;
            }

    }

    /**
     * <h1>Print Unknown Packet</h1>
     *
     * <p>Prints the given packet to console.</p>
     *
     * @param packet packet to print to console
     */
    private void PrintUnknownPacket(AvlPacket packet)
    {
        if(packet != null)
        {
            printToConsole("Codec: "+Converter.IntToHex(packet.GetCodec()) + "\n");
            printToConsole("Record Count : "+packet.GetRecordC() + "\n");
            int i = 0;
            for (AVLRecord record : packet.GetCollection()) {
                i++;
                printToConsole("\n" + i + " Record======================================================");
                printToConsole(record.GetRecordHeader().toString());
                printToConsole(record.GetRecordGpsData().toString());
                printToConsole(record.GetRecordIoData().toString());
            }
        }

    }

    /**
     * <h1>Print Tcp Packet</h1>
     *
     * <p>Prints the given packet to console.</p>
     *
     * @param packet packet to print to console
     */
    private void PrintTcpPacket(TcpAvlPacket packet)
    {
        if(packet != null)
        {
            printToConsole(packet.GetTcpHead().toString() + "\n");
            int i = 0;
            for (AVLRecord record : packet.GetCollection()) {
                i++;
                printToConsole("\n" + i + " Record======================================================");
                printToConsole(record.GetRecordHeader().toString());
                printToConsole(record.GetRecordGpsData().toString());
                printToConsole(record.GetRecordIoData().toString());
            }
        }

    }

    /**
     * <h1>Print Udp Packet</h1>
     *
     * <p>Prints the given packet to console.</p>
     *
     * @param packet packet to print to console
     */
    private void PrintUdpPacket(UdpAvlPacket packet)
    {
        if(packet != null)
        {
            printToConsole(packet.GetUdpHead().toString() + "\n");
            int i = 0;
            for (AVLRecord record : packet.GetCollection()) {
                i++;
                printToConsole("\n" + i + " Record======================================================");
                printToConsole(record.GetRecordHeader().toString());
                printToConsole(record.GetRecordGpsData().toString());
                printToConsole(record.GetRecordIoData().toString());
            }
        }

    }

    private void printToConsole(String text) {
        Console.appendText(text);
    }



}
