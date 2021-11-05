package Parser.Models.AVL.UdpAvlPacket;

import Primary.Converter;

public class UdpAvlHead {

    private int Length;
    private int PacketId;
    private int PacketType;
    private int AvlPacketId;
    private int ImeiLength;
    private String Imei;
    private int Codec;
    private int RecordC;

    public UdpAvlHead(int length, int packetId, int packetType, int avlPacketId, int imeiLength, String imei, int codec, int recordC) {
        Length = length;
        PacketId = packetId;
        PacketType = packetType;
        AvlPacketId = avlPacketId;
        ImeiLength = imeiLength;
        Imei = imei;
        RecordC = recordC;
        Codec = codec;
    }

    public int getLength() {
        return Length;
    }

    public int getPacketId() {
        return PacketId;
    }

    public int getPacketType() {
        return PacketType;
    }

    public int getAvlPacketId() {
        return AvlPacketId;
    }

    public int getImeiLength() {
        return ImeiLength;
    }

    public String getImei() {
        return Imei;
    }

    public int getCodec() {
        return Codec;
    }

    public int getRecordC() {
        return RecordC;
    }

    @Override
    public String toString() {
        return ("\nPacket Length         : " + Length)
        +("\nPacket Identification       : " + PacketId)
        +("\nPacket Type                 : " + PacketType)
        +("\nPacketId                    : " + AvlPacketId)
        +("\nImei Length                 : " + ImeiLength)
        +("\nImei                        : " + Imei)
        +("\nCodec                       : " + Converter.IntToHex(Codec))
        +("\nRecord count                : " + RecordC);

    }
}
