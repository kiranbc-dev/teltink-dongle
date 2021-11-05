package Parser.Models.AVL;

import Parser.Models.GPS.RecordGPS_Element;
import Parser.Models.IO.RecordIO_Element;
import java.io.Serializable;

/**
 * <h1>AVL Record Object</h1>
 * <p>AVL record object that holds Record Header, GPS, IO elements. The object implements Serializable</p>
 * */
public class AVLRecord implements Serializable {
    private RecordHeader recordHeader;
    private RecordGPS_Element recordGPS_elements;
    private RecordIO_Element recordIO_elements ;

    /**
     * <h1>AVL Record construct</h1>
     * <p>AVL record requires Record Header, GPS, IO objects.</p>
     * @param recordHeader the first parameter is for data header
     * @param recordGPS_elements the second parameter is for GPS data
     * @param recordIO_elements the third parameter is for IO data
     * */
    public AVLRecord(RecordHeader recordHeader, RecordGPS_Element recordGPS_elements, RecordIO_Element recordIO_elements) {
        this.recordHeader = recordHeader;
        this.recordGPS_elements = recordGPS_elements;
        this.recordIO_elements = recordIO_elements;
    }

    /**
     * <h1>Get Record Header</h1>
     * <p>gets header data</p>
     * @return returns record header object
     * */
    public RecordHeader getRecordHeader() {
        return recordHeader;
    }

    /**
     * <h1>Get Record GPS</h1>
     * <p>gets GPS data</p>
     * @return returns record GPS object
     * */
    public RecordGPS_Element getRecordGPS_elements() {
        return recordGPS_elements;
    }

    /**
     * <h1>Get Record IO</h1>
     * <p>gets IO data</p>
     * @return returns record IO object
     * */
    public RecordIO_Element getRecordIO_elements() {
        return recordIO_elements;
    }

}
