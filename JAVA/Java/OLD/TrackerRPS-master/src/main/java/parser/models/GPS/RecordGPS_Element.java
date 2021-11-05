package Parser.Models.GPS;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * <h1>Record GPS Element</h1>
 * <p>Record GPS Element that holds essential data. The object implements Serializable</p>
 * */
public class RecordGPS_Element implements Serializable {

    private int Longitude;
    private int Latitude;
    private int Altitude;
    private int Angle;
    private int satellites;
    private int Kmh;

    /**
     * <h1>Record GPS Element construct</h1>
     * <p>record GPS Element requires longitude, latitude, altitude, angle, satellites, kmh</p>
     * @param longitude int type
     * @param latitude int type
     * @param altitude int type
     * @param angle int type
     * @param satellites int type
     * @param kmh int type
     * */
    public RecordGPS_Element(int longitude, int latitude, int altitude, int angle, int satellites, int kmh) {
        Longitude = longitude;
        Latitude = latitude;
        Altitude = altitude;
        Angle = angle;
        this.satellites = satellites;
        Kmh = kmh;
    }


    public int getLongitude() {
        return Longitude;
    }

    public int getLatitude() {
        return Latitude;
    }

    public int getAltitude() {
        return Altitude;
    }

    public int getAngle() {
        return Angle;
    }

    public int getSatellites() {
        return satellites;
    }

    public int getKmh() {
        return Kmh;
    }
}
