package Parser.Models.AVL.TcpAvlPacket;

import Parser.Models.AVL.AVLRecord;

public class TcpAvlPacket  {

    private TcpHead tcpHead;
    private Iterable<AVLRecord> packet;

    public TcpAvlPacket(TcpHead tcpHead, Iterable<AVLRecord> packet) {
        this.tcpHead = tcpHead;
        this.packet = packet;
    }

    public TcpHead GetTcpHead() {
        return tcpHead;
    }

    public Iterable<AVLRecord> GetCollection() {
        return packet;
    }
}
