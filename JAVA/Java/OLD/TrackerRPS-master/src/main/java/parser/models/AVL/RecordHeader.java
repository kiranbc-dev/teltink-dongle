package Parser.Models.AVL;

import java.io.Serializable;
import java.util.Date;

/**
 * <h1>Record Header Object</h1>
 * <p>Record Header that holds Date format timestamp, long format record priority. The object implements Serializable</p>
 * */
public class RecordHeader implements Serializable {

    private Date Timestamp;
    private long RecordPriority;

    /**
     * <h1>Record header construct</h1>
     * <p>record header requires Date format timestamp, long format record priority.</p>
     * @param timestamp the first parameter is Date format timestamp
     * @param recordPriority the second parameter is long type priority
     * */
    public RecordHeader(Date timestamp, long recordPriority) {
        Timestamp = timestamp;
        RecordPriority = recordPriority;
    }

    /**
     * <h1>Get Record timestamp</h1>
     * <p>gets timestamp data</p>
     * @return returns record timestamp
     * */
    public Date getTimestamp() {
        return Timestamp;
    }

    /**
     * <h1>Get Record Priority</h1>
     * <p>gets Priority data</p>
     * @return returns record Priority
     * */
    public long getRecordPriority() {
        return RecordPriority;
    }

}
