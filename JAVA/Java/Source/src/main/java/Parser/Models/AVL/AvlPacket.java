package Parser.Models.AVL;

import java.io.Serializable;

public  class AvlPacket implements Serializable {

    private Iterable<AVLRecord> Packet;
    private int Codec;
    private int RecordC;

    public AvlPacket(byte[] bytes)
    {

    }

    public AvlPacket(int codec, int recordC ,Iterable<AVLRecord> packet) {
        Packet = packet;
        Codec = codec;
        RecordC = recordC;

    }

    public int GetCodec() {
        return Codec;
    }

    public int GetRecordC() {
        return RecordC;
    }

    public Iterable<AVLRecord> GetCollection() {
        return Packet;
    }

}
