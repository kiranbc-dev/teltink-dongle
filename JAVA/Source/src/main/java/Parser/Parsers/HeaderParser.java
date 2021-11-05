package Parser.Parsers;

import Parser.Models.AVL.RecordHeaderModel;
import Primary.ByteReader;

import java.util.Date;
import java.util.TimeZone;

/**
 * <h1>Data header parser</h1>
 * <p>Methods required to parse the header of data</p>
 */
public class HeaderParser {
     /**
     * <h1>Convert timestamp</h1>
     * <p>Converts from long dec to date</p>
     *
     * @param timestamp the first parameter Long dec is required for conversion
     * @return returns date in Date format
     */
    private Date ConvertTimestamp(Long timestamp) {
        Date date = new Date(timestamp);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return date;
    }

    /**
     * <h1>Converts Record Header data </h1>
     * <p>Main function which parses and converts header data from given byte array.</p>
     *
     * @param bytes requires for reading data
     * @return returns record header element object.
     */
    public RecordHeaderModel ReadRecordHeader(byte[] bytes) {

        RecordHeaderModel recordHeader;
        try {
            long dateValue = ByteReader.ReadValueLong(bytes, 8);
            int  priority = ByteReader.ReadValue(bytes,1);
            recordHeader = new RecordHeaderModel(ConvertTimestamp(dateValue), priority);
            return recordHeader;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
