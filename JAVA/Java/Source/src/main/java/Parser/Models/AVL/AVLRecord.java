package Parser.Models.AVL;

import Parser.Models.GPS.RecordGpsModel;
import Parser.Models.IO.RecordIoModel;
import Parser.Parsers.GPSParser;
import Parser.Parsers.HeaderParser;
import Parser.Parsers.IOParser;

import java.io.Serializable;

/**
 * <h1>AVL Record Object</h1>
 * <p>AVL record object that holds Record Header, GPS, IO elements. The object implements Serializable</p>
 */
public class AVLRecord implements Serializable {
    private RecordHeaderModel RecordHeader;
    private RecordGpsModel RecordGps;
    private RecordIoModel RecordIo;
    private byte[] bytes;

    /**
     * <h1>AVL Record construct</h1>
     * <p>AVL record requires Record Header, GPS, IO objects.</p>
     *
     * @param recordHeader the first parameter is for data header
     * @param recordGps the second parameter is for GPS data
     * @param recordIo  the third parameter is for IO data
     */
    public AVLRecord(RecordHeaderModel recordHeader, RecordGpsModel recordGps, RecordIoModel recordIo) {
        RecordHeader = recordHeader;
        RecordGps = recordGps;
        RecordIo = recordIo;
    }

    public AVLRecord(byte[] bytes,int codec) {
        this.bytes = bytes;
        setRecordHeader(ReadRecord_Header());
        setRecordGps(ReadRecord_GPS());
        setRecordIo(ReadRecord_IO(codec));
    }

    private void setRecordHeader(RecordHeaderModel recordHeader) {
        RecordHeader = recordHeader;
    }

    private void setRecordGps(RecordGpsModel recordGps) {
        RecordGps = recordGps;
    }

    private void setRecordIo(RecordIoModel recordIo) {
        RecordIo = recordIo;
    }

    /**
     * <h1>Get Record Header</h1>
     * <p>gets header data</p>
     *
     * @return returns record header object
     */
    public RecordHeaderModel GetRecordHeader() {
        return RecordHeader;
    }

    /**
     * <h1>Get Record GPS</h1>
     * <p>gets GPS data</p>
     *
     * @return returns record GPS object
     */
    public RecordGpsModel GetRecordGpsData() {
        return RecordGps;
    }

    /**
     * <h1>Get Record IO</h1>
     * <p>gets IO data</p>
     *
     * @return returns record IO object
     */
    public RecordIoModel GetRecordIoData() {
        return RecordIo;
    }


    /**
     * <h1>Get Record Header</h1>
     * <p>A function collects head data from byte array</p>
     *
     * @return returns Record Header element object.
     */
    private RecordHeaderModel ReadRecord_Header() {
        HeaderParser headerParser = new HeaderParser();
        return headerParser.ReadRecordHeader(bytes);
    }

    /**
     * <h1>Get Record GPS</h1>
     * <p>A function collects gps data from byte array</p>
     *
     * @return returns Record GPS element object.
     */
    private RecordGpsModel ReadRecord_GPS() {
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel recordGpsModel;
        recordGpsModel = gpsParser.ReadRecordGPS(bytes);
        return recordGpsModel;
    }

    /**
     * <h1>Get Record IO</h1>
     * <p>A function collects IO data from byte array</p>
     *
     * @param codec codec is required for parser to know if it's a special data like E8, if not default value is used.
     * @return returns Record IO element object.
     */
    private RecordIoModel ReadRecord_IO(int codec) {
        IOParser ioParser = new IOParser();
        ioParser.setTakeByte(codec == 142 ? 2 : 1);
        RecordIoModel recordIoModel;
        recordIoModel = ioParser.GetElement(bytes);
        return recordIoModel;
    }

}
