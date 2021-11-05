package Parser.Models.IO;

import java.io.Serializable;

/**
 * <h1>Record IO Element</h1>
 * <p>Record IO Element object that holds lists of records.
 * The object implements Serializable.</p>
 * */
public class RecordIO_Element implements Serializable {

    private int EventID;
    private int ElementCount;
    private RecordIO_ElementsLists recordIO_records;

    /**
     * <h1>Record IO element construct</h1>
     * <p>record IO element requires eventID, element Count, lists of elements </p>
     * @param eventID parameter declares event ID
     * @param elementCount parameter declares element Count
     * @param recordIO_records parameter declares record IO objects list
     * */
    public RecordIO_Element(int eventID, int elementCount, RecordIO_ElementsLists recordIO_records) {
        EventID = eventID;
        ElementCount = elementCount;
        this.recordIO_records = recordIO_records;
    }

    public int getEventID() {
        return EventID;
    }

    public int getElementCount() {
        return ElementCount;
    }

    public RecordIO_ElementsLists getRecordIO_records() {
        return recordIO_records;
    }
}
