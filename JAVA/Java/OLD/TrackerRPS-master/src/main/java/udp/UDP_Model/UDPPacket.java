package UDP.UDP_Model;

/**
 * <h1>UDP Packet</h1>
 * <p>UDP packet object is a set of data that can be used later on.</p>
 * */
public class UDPPacket {

    private String DataLength;
    private String PacketIdentification;
    private String PacketType;
    private String PacketID;
    private String ActualImei;
    private String NumberOfData;
    private String Data;

    public UDPPacket(String dataLength, String packetIdentification, String packetType, String packetID, String actualImei, String numberOfData, String data) {
        DataLength = dataLength;
        PacketIdentification = packetIdentification;
        PacketType = packetType;
        PacketID = packetID;
        ActualImei = actualImei;
        NumberOfData = numberOfData;
        Data = data;
    }

    UDPPacket() { }

    String getDataLength() {
        return DataLength;
    }

    void setDataLength(String dataLength) {
        DataLength = dataLength;
    }

    String getPacketIdentification() {
        return PacketIdentification;
    }

    void setPacketIdentification(String packetIdentification) {
        PacketIdentification = packetIdentification;
    }

    String getPacketType() {
        return PacketType;
    }

    void setPacketType(String packetType) {
        PacketType = packetType;
    }

    String getPacketID() {
        return PacketID;
    }

    void setPacketID(String packetID) {
        PacketID = packetID;
    }

    String getActualImei() {
        return ActualImei;
    }

    void setActualImei(String actualImei) {
        ActualImei = actualImei;
    }

    String getNumberOfData() {
        return NumberOfData;
    }

    void setNumberOfData(String numberOfData) {
        NumberOfData = numberOfData;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
