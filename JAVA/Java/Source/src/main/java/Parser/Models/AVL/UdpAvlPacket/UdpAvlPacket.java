package Parser.Models.AVL.UdpAvlPacket;

import Parser.Models.AVL.AVLRecord;

public class UdpAvlPacket {

    private UdpAvlHead UdpHead;
    private Iterable<AVLRecord> Packet;

    public UdpAvlPacket(UdpAvlHead udpHead, Iterable<AVLRecord> packet) {
        UdpHead = udpHead;
        Packet = packet;
    }

    public UdpAvlHead GetUdpHead() {
        return UdpHead;
    }

    public Iterable<AVLRecord> GetCollection() {
        return Packet;
    }

}
