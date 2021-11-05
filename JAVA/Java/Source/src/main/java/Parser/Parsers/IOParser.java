package Parser.Parsers;

import Parser.Models.IO.RecordIoModel;
import Parser.Models.IO.RecordIoModelList;
import Parser.Models.IO.RecordIoPropertyModel;
import Primary.ByteReader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * <h1>Data IO parser</h1>
 * <p>Methods required to parse the IO of data</p>
 */
public class IOParser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    private int TakeByte = 1;

    /**
     * <h1>Get IO element</h1>
     * <p>Method collects and returns IO data.</p>
     *
     * @param bytes requires for reading data
     * @return returns Record IO element object.
     */
    public RecordIoModel GetElement(byte[] bytes) {
        RecordIoModel recordIO_element;
        int eventID = GetElement_EventID(bytes);
        int recordCount = GetElement_RecordCount(bytes);

        RecordIoModelList dataLists = new RecordIoModelList();
        if (TakeByte == 2) {
            ParseIoLists(bytes, dataLists);
            dataLists.setByteList_XList(ParseIoData(bytes,2, true));
            recordIO_element = new RecordIoModel(eventID, recordCount, dataLists);
            return recordIO_element;
        } else
            ParseIoLists(bytes, dataLists);
            recordIO_element = new RecordIoModel(eventID, recordCount, dataLists);
        return recordIO_element;
    }

    private void ParseIoLists(byte[] bytes, RecordIoModelList dataLists) {
        dataLists.setByteList_1List(ParseIoData(bytes,1, false));
        dataLists.setByteList_2List(ParseIoData(bytes,2, false));
        dataLists.setByteList_4List(ParseIoData(bytes,4, false));
        dataLists.setByteList_8List(ParseIoData(bytes,8, false));
    }

    /**
     * <h1>Parse Record IO data </h1>
     * <p>Main function which parses and converts IO data from given byte array.</p>
     *
     * @param bytes requires for reading data
     * @param bytesToTake parameter used for selecting required byte length (1,2,4,8)
     * @param extended parameter used for parsing special data such as codec 8 extended.
     * @return returns record IO element object.
     */
    private List<RecordIoPropertyModel> ParseIoData(byte[] bytes,int bytesToTake, boolean extended) {
        int currentRecordCount = GetCurrentRecordNumberOfElements(bytes);
        int value, id;
        List<RecordIoPropertyModel> recordIoByteList = new ArrayList<>();
        RecordIoPropertyModel record;
        for (int i = 0; i < currentRecordCount; i++) {
            try {
                id = ByteReader.ReadValueUnsigned(bytes,TakeByte);
                value = ByteReader.ReadValue(bytes,bytesToTake);
                record = new RecordIoPropertyModel(id, value);
                if(extended){
                    record.setData(String.valueOf(value));
                    ByteReader.SetIndexSkipBytes(value);
                }
                recordIoByteList.add(record);
            } catch (Exception e) {
                e.getCause();
                LOGGER.error("Error parsing Io element - "+bytesToTake,e.getCause());
                break;
            }
        }
        return recordIoByteList;
    }

    /**
     * <h1>Get Current Record Count</h1>
     * <p>Retrieves the records number from given hex</p>
     *
     * @param bytes takes byte array to read from
     * @return returns number of elements
     */
    private int GetCurrentRecordNumberOfElements(byte[] bytes) {
        return ByteReader.ReadValueUnsigned(bytes,TakeByte);
    }

    /**
     * <h1>Get Current Record ID</h1>
     * <p>Retrieves the event ID from given hex</p>
     *
     * @param bytes takes byte array to read from
     * @return returns record event ID
     */
    private int GetElement_EventID(byte[] bytes) {
        return ByteReader.ReadValueUnsigned(bytes,TakeByte);
    }

    /**
     * <h1>Get Elements Count</h1>
     * <p>Retrieves the records number from given element</p>
     *
     * @param bytes takes byte array to read from
     * @return returns number of records
     */
    private int GetElement_RecordCount(byte[] bytes) {
        return ByteReader.ReadValueUnsigned(bytes, TakeByte);
    }

    /**
     * <h1>Take byte</h1>
     * <p>Sets variable takeByte to 2 or 4, depends which codec is used.
     * codec 8 uses 2 bytes, while extended codec 8 uses 4 bytes. </p>
     * @param takeByte takes int for selecting bytes.
     */
    public void setTakeByte(int takeByte) {
        TakeByte = takeByte;
    }
}
