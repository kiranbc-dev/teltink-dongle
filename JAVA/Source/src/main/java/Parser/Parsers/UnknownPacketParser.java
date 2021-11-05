package Parser.Parsers;

import Parser.Models.AVL.AVLRecord;
import Parser.Models.AVL.AvlPacket;
import Primary.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class UnknownPacketParser {

    /**
     * <h1>Create Unknown packet</h1>
     * <p>Function will simply <i>TRY</i> to read and parse the given byte array, it will read if codec 08 or E8 is found.</p>
     * @param bytes for reading and creating packet from it
     * @return returns UnknownAvlPacket
     */
    public AvlPacket CreateUnknownPacket(byte[] bytes) {
        if (Contains(bytes, (byte) 8) || Contains(bytes, (byte) -114)) {
            List<AVLRecord> avlRecords = new ArrayList<>();
            int ccc = -1;
            try {
                if (GetIndexOfCodec(bytes,-114) == 23 || GetIndexOfCodec(bytes,-114) == 8)
                {
                    ccc = GetIndexOfCodec(bytes,-114);
                }
                else if (GetIndexOfCodec(bytes,8) == 23 || GetIndexOfCodec(bytes,8) == 8)
                {
                    ccc = GetIndexOfCodec(bytes,8);
                }


                ByteReader.SetIndex(ccc);
                int codec = ByteReader.ReadValueUnsigned(bytes, 1);
                int recordC = ByteReader.ReadValueUnsigned(bytes, 1);

                for (int i = 0; i < recordC; i++)
                    avlRecords.add(new AVLRecord(bytes,codec));

               return new AvlPacket(codec , recordC,avlRecords);


            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("The inserted value is corrupted or incorrect.");
            }
        }
        return null;
    }

    private boolean Contains(byte[] bytes, byte match)
    {
        for (int i = 0;i<bytes.length;i++)
        {
            if(bytes[i] == match)
                return true;
        }
        return false;
    }

    private int GetIndexOfCodec(byte[] bytes, int value) {
        for (int i = 0; i < 24; i++) {
            if (value == bytes[i]) {
                return i;
            }
        }
        return -1;
    }
}
