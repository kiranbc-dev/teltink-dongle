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

    private ByteReader byteReader;

    public HeaderParser(ByteReader byteReader) {
        this.byteReader = byteReader;
    }

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
     * @return returns record header element object.
     */
    public RecordHeaderModel ReadRecordHeader() {

        try {
            long dateValue = byteReader.ReadValueLong( 8);
            int  priority = byteReader.ReadValue(1);
            return new RecordHeaderModel(ConvertTimestamp(dateValue), priority);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
