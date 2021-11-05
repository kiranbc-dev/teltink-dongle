package Parser.Models.AVL;

import Parser.Models.Interfaces.IAVLRecordCollection;

import java.io.Serializable;

/**
 * <h1>AVL Record Collection Object</h1>
 * <p>AVL record Collection that holds codec, record count, list of records. The object implements Serializable</p>
 */
public class AVLRecordCollection implements Serializable, IAVLRecordCollection {

    private int CodecId;
    private int RecordCount;
    private Iterable<AVLRecord> RecordList;

    /**
     * <h1>AVL Record Collection construct</h1>
     * <p>AVL record requires codec ID, record count, list of records.</p>
     *
     * @param codecId     codec number
     * @param recordCount number of records
     * @param recordList  list of records
     */
    public AVLRecordCollection(int codecId, int recordCount, Iterable<AVLRecord> recordList) {
        CodecId = codecId;
        RecordCount = recordCount;
        RecordList = recordList;
    }

    /**
     * <h1>Get Record Codec</h1>
     * <p>gets Codec data</p>
     *
     * @return returns record Codec object
     */
    @Override
    public int GetCodecID() {
        return CodecId;
    }

    /**
     * <h1>Get Record Count</h1>
     * <p>gets Count data</p>
     *
     * @return returns record Count
     */
    @Override
    public int GetRecordCount() {
        return RecordCount;
    }


    /**
     * <h1>Get Record List</h1>
     * <p>gets List data</p>
     *
     * @return returns record List object
     */
    @Override
    public Iterable<AVLRecord> GetRecordList() {
        return RecordList;
    }

}
