package Parser.Parsers;

import Parser.Models.GPS.RecordGpsModel;
import Primary.ByteReader;

/**
 * <H1>GPS parser for parsing gps element from data</H1>
 * <p>Parsing gps from given byte array of data</p>
 */
public class GPSParser {
    /**
     * <h1>Converts Record GPS </h1>
     * <p>Main function which parses and converts gps data from given byte array.</p>
     *
     * @param bytes requires for reading data
     * @return returns record gps element object.
     */
    public RecordGpsModel ReadRecordGPS(byte[] bytes) {
        try {
            RecordGpsModel recordGpsModel;
            recordGpsModel = new RecordGpsModel(ByteReader.ReadValue(bytes,4),
               ByteReader.ReadValue(bytes,4),
               ByteReader.ReadValue(bytes,2),
               ByteReader.ReadValue(bytes,2),
               ByteReader.ReadValue(bytes,1),
               ByteReader.ReadValue(bytes,2)
               );

            return recordGpsModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
