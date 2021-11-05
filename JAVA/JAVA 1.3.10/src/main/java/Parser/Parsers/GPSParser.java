package Parser.Parsers;

import Parser.Models.GPS.RecordGpsModel;
import Primary.ByteReader;

/**
 * <H1>GPS parser for parsing gps element from data</H1>
 * <p>Parsing gps from given byte array of data</p>
 */
public class GPSParser {

    private ByteReader byteReader ;

    public GPSParser(ByteReader byteReader) {
        this.byteReader = byteReader;
    }

    /**
     * <h1>Converts Record GPS </h1>
     * <p>Main function which parses and converts gps data from given byte array.</p>
     *
     * @return returns record gps element object.
     */
    public RecordGpsModel ReadRecordGPS() {
        try {
            RecordGpsModel recordGpsModel;
            recordGpsModel = new RecordGpsModel(byteReader.ReadValue(4),
                    byteReader.ReadValue(4),
                    byteReader.ReadValue(2),
                    byteReader.ReadValue(2),
                    byteReader.ReadValue(1),
                    byteReader.ReadValue(2)
               );

            return recordGpsModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
