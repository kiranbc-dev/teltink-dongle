package Parser;

import Parser.Models.AVL.RecordHeaderModel;
import Parser.Models.GPS.RecordGpsModel;
import Parser.Models.IO.RecordIoModel;
import Parser.Parsers.GPSParser;
import Parser.Parsers.HeaderParser;
import Parser.Parsers.IOParser;
import Primary.ByteReader;
import Primary.Converter;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserTest extends Converter {

    @Test
    public void ParsingNegativeValueTest()
    {
        int negativeValue = ConvertHexToInt("DF6A7122");
        int negativeValue1 = ConvertHexToInt("FFFE7960");
        int negativeValue2 = ConvertHexToInt("F0EB09B0");
        int negativeValue3 = ConvertHexToInt("FF14F650");


        Assert.assertEquals(-546672350,negativeValue);
        Assert.assertEquals(-100000,negativeValue1);
        Assert.assertEquals(-253032016,negativeValue2);
        Assert.assertNotEquals(253032016,negativeValue3);
    }

    @Test
    public void ParsingHeaderValuesTest() throws ParseException {
        HeaderParser parser = new HeaderParser();
        byte[] bytes = hexStringToByteArray("0000013feb55ff7400");
        //byte[] bytes = "0000013feb55ff7400".getBytes();
        RecordHeaderModel headerModel =  parser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "17-07-2013 06:34:09";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(0   ,headerModel.getRecordPriority());
        ByteReader.SetIndex(0);
    }

    @Test
    public void ParsingGpsValuesTest()
    {
        GPSParser parser = new GPSParser();
        byte[] bytes = hexStringToByteArray("0f14f650209cca80006f00d6040004");
        //byte[] bytes = "0f14f650209cca80006f00d6040004".getBytes();
        RecordGpsModel gpsModel =  parser.ReadRecordGPS(bytes);

        Assert.assertEquals(253032016   ,gpsModel.GetLongitude());
        Assert.assertEquals(547146368   ,gpsModel.GetLatitude());
        Assert.assertEquals(111     ,gpsModel.GetAltitude());
        Assert.assertEquals(214     ,gpsModel.GetAngle());
        Assert.assertEquals(4       ,gpsModel.GetSatellites());
        Assert.assertEquals(4       ,gpsModel.GetSpeed());

        ByteReader.SetIndex(0);
    }

    @Test
    public void ParsingIo1BytesTest()
    {
        IOParser ioParser = new IOParser();
        byte[] bytes = hexStringToByteArray("000A04EF01C8004501B30006B50009B6000842376C1800004300004400000000");
        //byte[] bytes = "000A04EF01C8004501B30006B50009B6000842376C1800004300004400000000".getBytes();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        // Assert 1 bytes
        Assert.assertEquals(239,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getID());
        Assert.assertEquals(1,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getValue());

        Assert.assertEquals(200,recordIoModel.getRecordIO_records().getByteList_1List().get(1).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(1).getValue());

        Assert.assertEquals(69,recordIoModel.getRecordIO_records().getByteList_1List().get(2).getID());
        Assert.assertEquals(1,recordIoModel.getRecordIO_records().getByteList_1List().get(2).getValue());

        Assert.assertEquals(179,recordIoModel.getRecordIO_records().getByteList_1List().get(3).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_1List().get(3).getValue());
        ByteReader.SetIndex(0);
    }

    @Test
    public void ParsingIo2BytesTest()
    {
        IOParser ioParser = new IOParser();
        byte[] bytes = hexStringToByteArray("000A04EF01C8004501B30006B50009B6000842376C1800004300004400000000");
        //byte[] bytes = "000A04EF01C8004501B30006B50009B6000842376C1800004300004400000000".getBytes();
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        // Assert 2 bytes
        Assert.assertEquals(181,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getID());
        Assert.assertEquals(9,recordIoModel.getRecordIO_records().getByteList_2List().get(0).getValue());

        Assert.assertEquals(182,recordIoModel.getRecordIO_records().getByteList_2List().get(1).getID());
        Assert.assertEquals(8,recordIoModel.getRecordIO_records().getByteList_2List().get(1).getValue());

        Assert.assertEquals(66,recordIoModel.getRecordIO_records().getByteList_2List().get(2).getID());
        Assert.assertEquals(14188,recordIoModel.getRecordIO_records().getByteList_2List().get(2).getValue());

        Assert.assertEquals(24,recordIoModel.getRecordIO_records().getByteList_2List().get(3).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_2List().get(3).getValue());

        Assert.assertEquals(67,recordIoModel.getRecordIO_records().getByteList_2List().get(4).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_2List().get(4).getValue());

        Assert.assertEquals(68,recordIoModel.getRecordIO_records().getByteList_2List().get(5).getID());
        Assert.assertEquals(0,recordIoModel.getRecordIO_records().getByteList_2List().get(5).getValue());
        ByteReader.SetIndex(0);
    }

    @Test
    public void ParsingUdpHeaderTest()
    {
        String hex = "040ccafe0175000f3335373435343037303131383432350816";

        byte[] bytes = hexStringToByteArray(hex);
       // byte[] bytes = hex.getBytes();
        int PacketLength = ByteReader.ReadValue(bytes , 2);
        int PacketIdentification = ByteReader.ReadValue(bytes,2);
        int PacketType = ByteReader.ReadValue(bytes,1);
        int PacketId = ByteReader.ReadValue(bytes,1);
        int ImeiLength = ByteReader.ReadValue(bytes,2);
        int Imei = ByteReader.ReadValue(bytes,ImeiLength);
        if (ImeiLength < 20) {
            System.out.println("PacketLength       : " + PacketLength);
            System.out.println("Packet Identification       : " + PacketIdentification);
            System.out.println("Packet Type       : " + PacketType);
            System.out.println("PacketId       : " + PacketId);
            System.out.println("Imei Length       : " + ImeiLength);
            System.out.println("Imei : " + Imei);

            int codec = ByteReader.ReadValue(bytes,1);
            int recordC = ByteReader.ReadValue(bytes,1);

            System.out.println(codec+" : "+recordC);
        }
        ByteReader.SetIndex(0);
    }

    @Test
    public void TestMethod()
    {
        long startTime = System.currentTimeMillis();

        byte[] byteArray = {48, 48, 48, 48, 48, 51, 51, 99};
        int v = 0;
        for (byte b : byteArray) {
            v = 16 * v + Character.getNumericValue(b);
        }
        System.out.println(v);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time! :"+elapsedTime);
    }

    @Test
    public void ReadImei()
    {
        String imeiString = "333537343534303730313138343235";
        byte[] bytes = Converter.toHexBytes(imeiString);
        String b = new String(bytes);
        Assert.assertEquals("357454070118425",b);
    }


}
