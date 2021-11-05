package Parser.Models.Interfaces;

public interface IRecordGpsModel {
    String toString();
    int GetLongitude();
    int GetLatitude();
    int GetAltitude();
    int GetAngle();
    int GetSatellites();
    int GetSpeed();

    }
