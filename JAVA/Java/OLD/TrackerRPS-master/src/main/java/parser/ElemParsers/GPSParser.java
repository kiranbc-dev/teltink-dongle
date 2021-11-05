package Parser.ElemParsers;

import Parser.Models.GPS.RecordGPS_Element;
import Primary.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * <H1>GPS parser for parsing gps element from data</H1>
 * <p>Parsing gps from given HEX of data</p>
 * */
public class GPSParser {

    private String HEX;
    private Converter converter;
    /**
     * <h1>GPS parser construct</h1>
     * <p>The constructor only takes HEX parameter</p>
     * @param HEX HEX value
     * */
    public GPSParser(String HEX) {
        this.HEX = HEX;
        converter = new Converter();
    }

    /**
     * <h1>Get HEX</h1>
     * @return returns String hex;
     * */
    public String getHEX() {
        return HEX;
    }

    /**
     * <h1>Converts Record GPS </h1>
     * <p>Main function which parses and converts gps data from given hex.</p>
     * @return returns record gps element object.
     * */
    public RecordGPS_Element ConvertRecord_GPS() {
        RecordGPS_Element recordGPS_element;
        String text;
        int[] num = {8, 8, 4, 4, 2, 4};
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            text = HEX.substring(0, num[i]);
            data.add(converter.convertStringToIntHex(text));
            HEX = HEX.substring(num[i], HEX.length());
        }
        recordGPS_element = Fill_recordGPS_element(data);
        return recordGPS_element;
    }

    /**
     * <h1>Fill record gps element</h1>
     * <p>Fill gps element object from parsed data</p>
     * @param list parameter used for filling the Record GPS element object
     * @return returns gps element object
     * */
    public RecordGPS_Element Fill_recordGPS_element(List<Integer> list) {
        RecordGPS_Element recordGPS_element;
        recordGPS_element = new RecordGPS_Element(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));

        return recordGPS_element;
    }

}
