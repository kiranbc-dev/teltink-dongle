package Parser.Parsers;

import Parser.Models.IO.*;
import Primary.ByteReader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <h1>Data IO parser</h1>
 * <p>Methods required to parse the IO of data</p>
 */
public class IOParser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    private int ioIdSize;
    private ByteReader byteReader;

    public IOParser(int ioIdSize, ByteReader byteReader){
        this.ioIdSize = ioIdSize;
        this.byteReader = byteReader;
    }

    /**
     * <h1>Get IO element</h1>
     * <p>Method collects and returns IO data.</p>
     *
     * @return returns Record IO element object.
     */
    public RecordIoModel GetElement() {
        RecordIoModel recordIO_element;
        int eventID = byteReader.ReadValueUnsigned(ioIdSize);
        int recordCount = byteReader.ReadValueUnsigned(ioIdSize);

        RecordIoModelList dataLists = ParseIoLists();
        recordIO_element = new RecordIoModel(eventID, recordCount, dataLists);
        return recordIO_element;
    }

    private RecordIoModelList ParseIoLists() {
        RecordIoModelList record = new RecordIoModelList();
        record.setByteList_1List(ParseFixedSizeData(1));
        record.setByteList_2List(ParseFixedSizeData(2));
        record.setByteList_4List(ParseFixedSizeData(4));
        record.setByteList_8List(ParseFixedSizeData(8));
        if(ioIdSize == 2){
            record.setByteList_XList(ParseVariableSizeData());
        }

        return record;
    }

    /**
     * <h1>Parse Record IO data </h1>
     * <p>Main function which parses and converts IO data from given byte array.</p>
     *
     * @param valueSize parameter used for selecting required byte length (1,2,4,8)
     * @return returns record IO element object.
     */
    private List<RecordIoPropertyModel> ParseFixedSizeData(int valueSize) {
        int currentRecordCount = byteReader.ReadValueUnsigned(ioIdSize);
        int value, id;
        List<RecordIoPropertyModel> recordIoByteList = new ArrayList<>();
        RecordIoPropertyModel record;
        for (int i = 0; i < currentRecordCount; i++) {
            try {
                id = byteReader.ReadValueUnsigned(ioIdSize);
                value = byteReader.ReadValue(valueSize);
                record = new RecordIoPropertyModel(id, value);
                recordIoByteList.add(record);
            } catch (Exception e) {
                e.getCause();
                LOGGER.error("Error parsing Io element - "+valueSize,e.getCause());
                break;
            }
        }
        return recordIoByteList;
    }

    private List<RecordIoPropertyModel> ParseVariableSizeData() {
        int currentRecordCount = byteReader.ReadValueUnsigned(ioIdSize);
        List<RecordIoPropertyModel> recordIoByteList = new ArrayList<>();
        RecordIoPropertyModel record;
        for (int i = 0; i < currentRecordCount; i++) {
            try {
                int id = byteReader.ReadValueUnsigned(ioIdSize);
                int length = byteReader.ReadValueUnsigned( 2);
                byte[] value = byteReader.ReadBytes(length);

                switch (id) {
                    case 10358:
                        record = new McanIoProperty(id, value);
                        break;

                    default:
                        record = new RecordIoPropertyModel(id, value.length);
                        byteReader.SkipIndex(value.length);
                }
                recordIoByteList.add(record);
            } catch (Exception e) {
                e.getCause();
                break;
            }
        }
        return recordIoByteList;
    }
}
