package Parser.Models.GPS;

import Parser.Models.Interfaces.IRecordGpsModel;

import java.io.Serializable;

/**
 * <h1>Record GPS Element</h1>
 * <p>Record GPS Element that holds essential data. The object implements Serializable</p>
 */
public class RecordGpsModel implements Serializable, IRecordGpsModel {

    private int Longitude;
    private int Latitude;
    private int Altitude;
    private int Angle;
    private int Satellites;
    private int Speed;

    /**
     * <h1>Record GPS Element construct</h1>
     * <p>record GPS Element requires longitude, latitude, altitude, angle, satellites, speed</p>
     *
     * @param longitude  int type
     * @param latitude   int type
     * @param altitude   int type
     * @param angle      int type
     * @param satellites int type
     * @param speed      int type
     */
    public RecordGpsModel(int longitude, int latitude, int altitude, int angle, int satellites, int speed) {
        Longitude = longitude;
        Latitude = latitude;
        Altitude = altitude;
        Angle = angle;
        Satellites = satellites;
        Speed = speed;
    }

    @Override
    public int GetLongitude() {
        return Longitude;
    }

    @Override
    public int GetLatitude() {
        return Latitude;
    }

    @Override
    public int GetAltitude() {
        return Altitude;
    }

    @Override
    public int GetAngle() {
        return Angle;
    }

    @Override
    public int GetSatellites() {
        return Satellites;
    }

    @Override
    public int GetSpeed() {
        return Speed;
    }

    @Override
    public String toString() {
        return "\nRecord GPS longitude : " + this.Longitude +
                "\nRecord GPS latitude  : " + this.Latitude +
                "\nRecord GPS altitude  : " + this.Altitude +
                "\nRecord GPS angle     : " + this.Angle +
                "\nRecord GPS satellites: " + this.Satellites +
                "\nRecord GPS Kmh       : " + this.Speed+"\n";
    }
}