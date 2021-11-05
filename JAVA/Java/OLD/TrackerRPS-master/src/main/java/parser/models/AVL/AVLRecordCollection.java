package Parser.Models.AVL;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>AVL Record Collection Object</h1>
 * <p>AVL record Collection that holds codec, record count, list of records. The object implements Serializable</p>
 * */
public class AVLRecordCollection implements Serializable {

    private int CodecID;
    private int RecordCount;
    private List<AVLRecord> recordList;

    /**
     * <h1>AVL Record Collection construct</h1>
     * <p>AVL record requires codec ID, record count, list of records.</p>
     * @param codecID codec number
     * @param recordCount number of records
     * @param recordList list of records
     * */
    public AVLRecordCollection(int codecID, int recordCount, List<AVLRecord> recordList) {
        CodecID = codecID;
        RecordCount = recordCount;
        this.recordList = recordList;
    }

    /**
     * <h1>Get Record Codec</h1>
     * <p>gets Codec data</p>
     * @return returns record Codec object
     * */
    public int getCodecID() {
        return CodecID;
    }

    /**
     * <h1>Get Record Count</h1>
     * <p>gets Count data</p>
     * @return returns record Count
     * */
    public int getRecordCount() {
        return RecordCount;
    }

    /**
     * <h1>Get Record List</h1>
     * <p>gets List data</p>
     * @return returns record List object
     * */
    public List<AVLRecord> getRecordList() {
        return recordList;
    }

}
