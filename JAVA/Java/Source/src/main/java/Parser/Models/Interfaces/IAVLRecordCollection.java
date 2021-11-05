package Parser.Models.Interfaces;

import Parser.Models.AVL.AVLRecord;

public interface IAVLRecordCollection {

    int GetCodecID();
    int GetRecordCount();
    Iterable<AVLRecord> GetRecordList();
    String toString();
}
