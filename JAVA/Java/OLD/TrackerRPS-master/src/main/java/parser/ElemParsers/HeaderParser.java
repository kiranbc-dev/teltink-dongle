package Parser.ElemParsers;

import Parser.Models.AVL.RecordHeader;
import Primary.Converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>Data header parser</h1>
 * <p>Methods required to parse the header of data</p>
 * */
class HeaderParser {

    private String HEX;
    private List<Long> data = new ArrayList<>();
    private Converter converter;

    /**
     * <h1>Header parser construct</h1>
     * <p>The constructor only takes HEX parameter</p>
     * @param HEX HEX value
     * */
    HeaderParser(String HEX) {
        this.HEX = HEX;
        converter = new Converter();
    }

    String getHEX() {
        return HEX;
    }

    /**
     * <h1>Convert timestamp</h1>
     * <p>Converts from long dec to date</p>
     * @param dec the first parameter Long dec is required for conversion
     * @return returns date in Date format
     * */
    private Date ConvertTimestamp(Long dec) {
        Date date;
        Timestamp timestamp = new Timestamp(dec);
        date = new Date(timestamp.getTime());
        return date;
    }

    /**
     * <h1>Converts Record Header data </h1>
     * <p>Main function which parses and converts header data from given hex.</p>
     * @return returns record header element object.
     * */
    RecordHeader ConvertRecord_Data() {
        String text;
        int[] num = {16, 2};
        for (int i = 0; i < 2; i++) {
            text = HEX.substring(0, num[i]);
            data.add(converter.convertLong(text));
            HEX = HEX.substring(num[i], HEX.length());
        }
        return Fill_recordData(data);
    }

    /**
     * <h1>Fill record header element</h1>
     * <p>Fill header element object from parsed data</p>
     * @param data parameter used for filling the Record Header element object
     * @return returns header element object
     * */
    private RecordHeader Fill_recordData(List<Long> data) {
        RecordHeader recordHeader;
        recordHeader = new RecordHeader(ConvertTimestamp(data.get(0)), data.get(1));
        return recordHeader;
    }



}
