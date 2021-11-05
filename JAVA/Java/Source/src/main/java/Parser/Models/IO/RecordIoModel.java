package Parser.Models.IO;

import Parser.Models.Interfaces.IRecordIoModel;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>Record IO Element</h1>
 * <p>Record IO Element object that holds lists of records.
 * The object implements Serializable.</p>
 */
public class RecordIoModel implements Serializable, IRecordIoModel {

    private int EventID;
    private int ElementCount;
    private RecordIoModelList RecordIO_records;

    /**
     * <h1>Record IO element construct</h1>
     * <p>record IO element requires eventID, element Count, lists of elements </p>
     *
     * @param eventID          parameter declares event ID
     * @param elementCount     parameter declares element Count
     * @param recordIO_records parameter declares record IO objects list
     */
    public RecordIoModel(int eventID, int elementCount, RecordIoModelList recordIO_records) {
        EventID = eventID;
        ElementCount = elementCount;
        RecordIO_records = recordIO_records;
    }

    @Override
    public int getEventID() {
        return EventID;
    }

    @Override
    public int getElementCount() {
        return ElementCount;
    }

    @Override
    public RecordIoModelList getRecordIO_records() {
        return RecordIO_records;
    }

    @Override
    public String toString()
    {
        String result = "\nEventID : " + getEventID()+
                "\nElement count : " + getElementCount()+
                "\n\n1 byte elements :"+getRecordIO_records().getByteList_1List().size()+
                ListToString(getRecordIO_records().getByteList_1List())+
                "\n\n2 byte elements "+getRecordIO_records().getByteList_2List().size()+
                ListToString(getRecordIO_records().getByteList_2List())+
                "\n\n4 byte elements "+getRecordIO_records().getByteList_4List().size()+
                ListToString(getRecordIO_records().getByteList_4List())+
                "\n\n8 byte elements "+getRecordIO_records().getByteList_8List().size()+
                ListToString(getRecordIO_records().getByteList_8List())+"\n";
        if(getRecordIO_records().getByteList_XList()!=null)
            return result+(ListToString(getRecordIO_records().getByteList_XList()))+"\n";

        return result;
    }

    private String ListToString(List<RecordIoPropertyModel> record)
    {
        StringBuilder sb = new StringBuilder();
        for (RecordIoPropertyModel data : record) {
            sb.append(data.toString());
        }
        return sb.toString();
    }

}
