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

public class TcpAvlTests {

    private String data;
    byte[] bytes;

    public TcpAvlTests()
    {
        data = "000000000000033c081900000163d4059630010f0d9c16DF6A7122009c00320a0000420100014262b4000000000163d4059248010f0d9c16DF6A7122009c00320a0000420100014262b5000000000163d4058e60010f0d9c16DF6A7122009c00320a0000420100014262b4000000000163d4058a78010f0d9c16DF6A7122009a00320a0000420100014262bc000000000163d40582a8010f0d9c16DF6A7122009a00320a0000420100014262be000000000163d4057ec0010f0d9c16DF6A7122009a00320a0000420100014262bd000000000163d4057ad8010f0d9c16DF6A7122009a00320a0000420100014262bc000000000163d4056f20010f0d9c16DF6A7122009a00320a0000420100014262be000000000163d4056b38010f0d9c16DF6A7122009a00320a0000420100014262b6000000000163d4056750010f0d9c16DF6A7122009a0032090000420100014262b4000000000163d4056368010f0d9c16DF6A7122009a0032090000420100014262b6000000000163d40557b0010f0d9c16DF6A7122009a0032090000420100014262b4000000000163d40553c8010f0d9c16DF6A7122009a0032090000420100014262bc000000000163d4054428010f0d9c16DF6A712200990032090000420100014262be000000000163d4054040010f0d9c16DF6A712200990032090000420100014262b6000000000163d4052cb8010f0d9c16DF6A7122009a0032090000420100014262b4000000000163d40528d0010f0d9c16DF6A7122009a0032090000420100014262bd000000000163d40524e8010f0d9c16DF6A7122009a0032090000420100014262ac000000000163d4052100010f0d9c16DF6A7122009c00320a0000420100014262b0000000000163d4051d18010f0d9c16DF6A7122009c00320a0000420100014262b1000000000163d4051930010f0d9c16DF6A7122009c00320a0000420100014262b0000000000163d4051548010f0d9c16DF6A7122009c00320a0000420100014262ac000000000163d4051160010f0d9c16DF6A7122009c00320a0000420100014262ad000000000163d4050990010f0d9c16DF6A7122009c00320a0000420100014262ae000000000163d40505a8010f0d9c16DF6A7122009c00320a0000420100014262ac0000190000956c";
        bytes = Converter.hexStringToByteArray(data);
    }

    @Test
    public void TcpAvlHeadParseTest() throws ParseException {
        // Head
        int preamble = ByteReader.ReadValue(bytes,4);
        int avlLength = ByteReader.ReadValue(bytes,4);

        Assert.assertEquals(0,preamble);
        Assert.assertEquals(828,avlLength);

        int codec = ByteReader.ReadValueUnsigned(bytes,1);
        int dataCount = ByteReader.ReadValueUnsigned(bytes,1);

        Assert.assertEquals(8,codec);
        Assert.assertEquals(25,dataCount);

    }

    @Test
    public void IoRecordsTest1() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "06-06-2018 07:35:26";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252550166,gpsModel.GetLongitude());
        Assert.assertEquals(-546672350,gpsModel.GetLatitude());
        Assert.assertEquals(156,gpsModel.GetAltitude());
        Assert.assertEquals(50,gpsModel.GetAngle());
        Assert.assertEquals(10,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(66,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 2 bytes
        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(25268,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());
    }

    @Test
    public void IoRecordsTest2() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "06-06-2018 07:35:25";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252550166,gpsModel.GetLongitude());
        Assert.assertEquals(-546672350,gpsModel.GetLatitude());
        Assert.assertEquals(156,gpsModel.GetAltitude());
        Assert.assertEquals(50,gpsModel.GetAngle());
        Assert.assertEquals(10,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(66,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 2 bytes
        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(25269,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());
    }

    @Test
    public void IoRecordsTest3() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "06-06-2018 07:35:24";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252550166,gpsModel.GetLongitude());
        Assert.assertEquals(-546672350,gpsModel.GetLatitude());
        Assert.assertEquals(156,gpsModel.GetAltitude());
        Assert.assertEquals(50,gpsModel.GetAngle());
        Assert.assertEquals(10,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(66,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 2 bytes
        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(25268,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());
    }

    @Test
    public void IoRecordsTest4() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "06-06-2018 07:35:23";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252550166,gpsModel.GetLongitude());
        Assert.assertEquals(-546672350,gpsModel.GetLatitude());
        Assert.assertEquals(154,gpsModel.GetAltitude());
        Assert.assertEquals(50,gpsModel.GetAngle());
        Assert.assertEquals(10,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(66,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 2 bytes
        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(25276,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());
    }

    @Test
    public void IoRecordsTest5() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "06-06-2018 07:35:21";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252550166,gpsModel.GetLongitude());
        Assert.assertEquals(-546672350,gpsModel.GetLatitude());
        Assert.assertEquals(154,gpsModel.GetAltitude());
        Assert.assertEquals(50,gpsModel.GetAngle());
        Assert.assertEquals(10,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(66,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 2 bytes
        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(25278,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());
    }

    @Test
    public void IoRecordsTest6() throws ParseException {
        // 1 record
        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "06-06-2018 07:35:20";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(252550166,gpsModel.GetLongitude());
        Assert.assertEquals(-546672350,gpsModel.GetLatitude());
        Assert.assertEquals(154,gpsModel.GetAltitude());
        Assert.assertEquals(50,gpsModel.GetAngle());
        Assert.assertEquals(10,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(66,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 2 bytes
        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(25277,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());

        ByteReader.SetIndex(0);
    }



}

