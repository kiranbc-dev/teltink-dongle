package Parser.Models.AVL.TcpAvlPacket;

import Parser.Models.Interfaces.ITcpHead;
import Primary.Converter;

public class TcpHead implements ITcpHead {

    private int Preamble;
    private int AvlDataLength;
    private int Codec;
    private int RecordCount;


    public TcpHead(int Preamble, int AvlDataLength,int Codec, int RecordCount) {
        this.Preamble = Preamble;
        this.AvlDataLength = AvlDataLength;
        this.Codec = Codec;
        this.RecordCount = RecordCount;
    }


    @Override
    public int GetCodec() {
        return Codec;
    }

    @Override
    public int GetRecordCount() {
        return RecordCount;
    }

    @Override
    public int GetPreamble() {
        return Preamble;
    }

    @Override
    public int GetAvlDataLength() {
        return AvlDataLength;
    }

    @Override
    public String toString() {
        return "Preamble: "+Preamble+"\nAvl Data Length : "+AvlDataLength+"\n"
                +"Codec : "+ Converter.IntToHex(Codec)+" \nRecord count : "+RecordCount+"\n";
    }
}
