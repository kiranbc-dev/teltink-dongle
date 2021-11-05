package Parser.ElemParsers;

import Parser.Models.IO.RecordIO_ElementsLists;
import Parser.Models.IO.RecordIO_Element;
import Parser.Models.IO.RecordIO_Property;
import Primary.Converter;

import java.util.ArrayList;
import java.util.List;


/**
 * <h1>Data IO parser</h1>
 * <p>Methods required to parse the IO of data</p>
 * */
class IOParser {

    private String HEX;
    private Converter converter;
    private int TakeByte;

    /**
     * <h1>IO parser construct</h1>
     * <p>The constructor only takes HEX parameter</p>
     * @param HEX HEX value
     * */
    IOParser(String HEX) {
        this.HEX = HEX;
        converter = new Converter();
    }

    String getHEX() {
        return HEX;
    }

    /**
     * <h1>Get IO element</h1>
     * <p>Method collects and returns IO data.</p>
     * @return returns Record IO element object.
     * */
    RecordIO_Element GetElement()
    {
        RecordIO_Element recordIO_element;
        int eventID = GetElement_EventID();
        int recordCount = GetElement_RecordCount();

        RecordIO_ElementsLists dataLists = new RecordIO_ElementsLists();
        if(TakeByte==4)
        {
            dataLists.setByteList_1List(ConvertRecord_IO_ByteData(6,0));
            dataLists.setByteList_2List(ConvertRecord_IO_ByteData(8,0));
            dataLists.setByteList_4List(ConvertRecord_IO_ByteData(12,0));
            dataLists.setByteList_8List(ConvertRecord_IO_ByteData(20,0));
            dataLists.setByteList_XList(ConvertRecord_IO_ByteData(8,1));
            recordIO_element = new RecordIO_Element(eventID,recordCount,dataLists);
        }
        else {
            dataLists.setByteList_1List(ConvertRecord_IO_ByteData(4,0));
            dataLists.setByteList_2List(ConvertRecord_IO_ByteData(6,0));
            dataLists.setByteList_4List(ConvertRecord_IO_ByteData(10,0));
            dataLists.setByteList_8List(ConvertRecord_IO_ByteData(18,0));
            recordIO_element = new RecordIO_Element(eventID,recordCount,dataLists);
        }


        return recordIO_element;
    }

    /**
     * <h1>Converts Record IO data </h1>
     * <p>Main function which parses and converts IO data from given hex.</p>
     * @param charIndex parameter used for selecting required byte length (4,6,10,18)
     * @return returns record IO element object.
     * */
    private List<RecordIO_Property> ConvertRecord_IO_ByteData(int charIndex, int n) {
        int currentRecordCount = GetCurrentRecordNumberOfElements();
        int value, id;
        String data = null;
        List<RecordIO_Property> recordIO_properties_1Byte = new ArrayList<>();
        RecordIO_Property record;
        for (int i = 0; i < currentRecordCount; i++) {
            String output = HEX.substring(0, charIndex);
            HEX = HEX.substring(charIndex, HEX.length());
            id = GetElementID(output);
            value = GetElementValue(output);
         if(n == 1)
            {
                data = HEX.substring(0,value*2);
                HEX = HEX.substring(value*2, HEX.length());
            }

             record = new RecordIO_Property(id, value,data);

             recordIO_properties_1Byte.add(record);

        }
        return recordIO_properties_1Byte;
    }

    /**
     * <h1>Get Current Record Count</h1>
     * <p>Retrieves the records number from given hex</p>
     * @return returns number of elements
     * */
    private int GetCurrentRecordNumberOfElements() {
        int n = Integer.parseInt(HEX.substring(0,TakeByte), 16);
        HEX = HEX.substring(TakeByte, HEX.length());
        return n;
    }

    /**
     * <h1>Get Current Record ID</h1>
     * <p>Retrieves the event ID from given hex</p>
     * @return returns record event ID
     * */
    private int GetElement_EventID() {
        int n = Integer.parseInt(HEX.substring(0, TakeByte), 16);
        HEX = HEX.substring(TakeByte, HEX.length());
        return n;
    }

    /**
     * <h1>Get Elements Count</h1>
     * <p>Retrieves the records number from given element</p>
     * @return returns number of records
     * */
    private int GetElement_RecordCount() {
        int n = Integer.parseInt(HEX.substring(0, TakeByte), 16);
        HEX = HEX.substring(TakeByte, HEX.length());
        return n;
    }

    /**
     * <h1>Get Element ID</h1>
     * <p>Retrieves the element ID from given hex</p>
     * @param output the parameter String output is required
     * @return returns ID of element
     * */
    private int GetElementID(String output) {
        return Integer.parseInt(output.substring(0, TakeByte), 16);
    }

    /**
     * <h1>Get Element value</h1>
     * <p>Retrieves Element value from given hex</p>
     * @param output the parameter String output is required
     * @return returns value of element
     * */
    private int GetElementValue(String output) {
        return converter.convertStringToIntHex(output.substring(2*2, output.length()));
    }

    public void setTakeByte(int takeByte) {
        TakeByte = takeByte;
    }
}
