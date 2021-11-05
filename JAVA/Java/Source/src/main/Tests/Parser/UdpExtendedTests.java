package Parser;

import Parser.Models.AVL.RecordHeaderModel;
import Parser.Models.GPS.RecordGpsModel;
import Parser.Models.IO.RecordIoModel;
import Parser.Parsers.GPSParser;
import Parser.Parsers.HeaderParser;
import Parser.Parsers.IOParser;
import Primary.ByteReader;
import Primary.Converter;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpExtendedTests {

    private String data;
    byte[] bytes;

    public UdpExtendedTests()
    {
        data = "00a1cafe011b000f3335363330373034323434313031338e010000013febdd19c8000f0e9ff0209a718000690000120000001e09010002000300040016014703f0001504c8000c0900910a00440b004d130044431555440000b5000bb60005422e9b180000cd0386ce000107c700000000f10000601a460000013c4800000bb84900000bb84a00000bb84c00000000024e0000000000000000cf000000000000000001";
        bytes = Converter.hexStringToByteArray(data);
    }

    @Test
    public void UdpAvlHeadParseTest() throws ParseException {
        // Head
        int PacketLength = ByteReader.ReadValue(bytes , 2);
        int PacketIdentification = ByteReader.ReadValue(bytes,2);
        int PacketType = ByteReader.ReadValue(bytes,1);
        int PacketId = ByteReader.ReadValue(bytes,1);
        int ImeiLength = ByteReader.ReadValue(bytes,2);
        int Imei = ByteReader.ReadValue(bytes,ImeiLength);

        int codec = ByteReader.ReadValueUnsigned(bytes,1);
        int dataCount = ByteReader.ReadValueUnsigned(bytes,1);

        Assert.assertEquals(161,PacketLength);
        Assert.assertEquals(-13570,PacketIdentification);
        Assert.assertEquals(1,PacketType);
        Assert.assertEquals(27,PacketId);
        Assert.assertEquals(15,ImeiLength);
        Assert.assertEquals(0,Imei);

        Assert.assertEquals(142,codec);
        Assert.assertEquals(1,dataCount);
    }

    @Test
    public void IoRecordsTest1() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "17-07-2013 09:01:43";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(0   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252616688,gpsModel.GetLongitude());
        Assert.assertEquals(546992512,gpsModel.GetLatitude());
        Assert.assertEquals(105,gpsModel.GetAltitude());
        Assert.assertEquals(0,gpsModel.GetAngle());
        Assert.assertEquals(18,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(0,recordIoModel.getEventID());
        Assert.assertEquals(30,recordIoModel.getElementCount());

        // Assert 1 bytes
        Assert.assertEquals(1,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getValue());

        Assert.assertEquals(2,recordIoModel.getRecordIO_records().getByteList_1List().get(1).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(1).getValue());

        Assert.assertEquals(3,recordIoModel.getRecordIO_records().getByteList_1List().get(2).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(2).getValue());

        Assert.assertEquals(4,recordIoModel.getRecordIO_records().getByteList_1List().get(3).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(3).getValue());

        Assert.assertEquals(22,recordIoModel.getRecordIO_records().getByteList_1List().get(4).getID());
        Assert.assertEquals(1,recordIoModel.getRecordIO_records().getByteList_1List().get(4).getValue());

        Assert.assertEquals(71,recordIoModel.getRecordIO_records().getByteList_1List().get(5).getID());
        Assert.assertEquals(3,recordIoModel.getRecordIO_records().getByteList_1List().get(5).getValue());

        Assert.assertEquals(240,recordIoModel.getRecordIO_records().getByteList_1List().get(6).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(6).getValue());

        Assert.assertEquals(21,recordIoModel.getRecordIO_records().getByteList_1List().get(7).getID());
        Assert.assertEquals(4,recordIoModel.getRecordIO_records().getByteList_1List().get(7).getValue());

        Assert.assertEquals(200,recordIoModel.getRecordIO_records().getByteList_1List().get(8).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(8).getValue());

        // Assert 2 bytes
        Assert.assertEquals(9,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(145,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());

        Assert.assertEquals(10,recordIoModel.getRecordIO_records().getByteList_2List().get(1).getID());
        Assert.assertEquals(68,recordIoModel.getRecordIO_records().getByteList_2List().get(1).getValue());

        Assert.assertEquals(11,recordIoModel.getRecordIO_records().getByteList_2List().get(2).getID());
        Assert.assertEquals(77,recordIoModel.getRecordIO_records().getByteList_2List().get(2).getValue());

        Assert.assertEquals(19,recordIoModel.getRecordIO_records().getByteList_2List().get(3).getID());
        Assert.assertEquals(68,recordIoModel.getRecordIO_records().getByteList_2List().get(3).getValue());

        Assert.assertEquals(67,recordIoModel.getRecordIO_records().getByteList_2List().get(4).getID());
        Assert.assertEquals(5461,recordIoModel.getRecordIO_records().getByteList_2List().get(4).getValue());

        Assert.assertEquals(68,recordIoModel.getRecordIO_records().getByteList_2List().get(5).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_2List().get(5).getValue());

        Assert.assertEquals(181,recordIoModel.getRecordIO_records().getByteList_2List().get(6).getID());
        Assert.assertEquals(11,recordIoModel.getRecordIO_records().getByteList_2List().get(6).getValue());

        Assert.assertEquals(182,recordIoModel.getRecordIO_records().getByteList_2List().get(7).getID());
        Assert.assertEquals(5,recordIoModel.getRecordIO_records().getByteList_2List().get(7).getValue());

        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(8).getID());
        Assert.assertEquals(11931,recordIoModel.getRecordIO_records().getByteList_2List().get(8).getValue());

        Assert.assertEquals(24,recordIoModel.getRecordIO_records().getByteList_2List().get(9).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_2List().get(9).getValue());

        Assert.assertEquals(205,recordIoModel.getRecordIO_records().getByteList_2List().get(10).getID());
        Assert.assertEquals(902,recordIoModel.getRecordIO_records().getByteList_2List().get(10).getValue());

        Assert.assertEquals(206,recordIoModel.getRecordIO_records().getByteList_2List().get(11).getID());
        Assert.assertEquals(1,recordIoModel.getRecordIO_records().getByteList_2List().get(11).getValue());

        // Assert 4 bytes
        Assert.assertEquals(199,recordIoModel.getRecordIO_records().getByteList_4List().get(0).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_4List().get(0).getValue());

        Assert.assertEquals(241,recordIoModel.getRecordIO_records().getByteList_4List().get(1).getID());
        Assert.assertEquals(24602,recordIoModel.getRecordIO_records().getByteList_4List().get(1).getValue());

        Assert.assertEquals(70,recordIoModel.getRecordIO_records().getByteList_4List().get(2).getID());
        Assert.assertEquals(316,recordIoModel.getRecordIO_records().getByteList_4List().get(2).getValue());

        Assert.assertEquals(72,recordIoModel.getRecordIO_records().getByteList_4List().get(3).getID());
        Assert.assertEquals(3000,recordIoModel.getRecordIO_records().getByteList_4List().get(3).getValue());

        Assert.assertEquals(73,recordIoModel.getRecordIO_records().getByteList_4List().get(4).getID());
        Assert.assertEquals(3000,recordIoModel.getRecordIO_records().getByteList_4List().get(4).getValue());

        Assert.assertEquals(74,recordIoModel.getRecordIO_records().getByteList_4List().get(5).getID());
        Assert.assertEquals(3000,recordIoModel.getRecordIO_records().getByteList_4List().get(5).getValue());

        Assert.assertEquals(76,recordIoModel.getRecordIO_records().getByteList_4List().get(6).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_4List().get(6).getValue());

        // Assert 8 bytes
        Assert.assertEquals(78,recordIoModel.getRecordIO_records().getByteList_8List().get(0).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_8List().get(0).getValue());

        Assert.assertEquals(207,recordIoModel.getRecordIO_records().getByteList_8List().get(1).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_8List().get(1).getValue());

        ByteReader.SetIndex(0);
    }

}
