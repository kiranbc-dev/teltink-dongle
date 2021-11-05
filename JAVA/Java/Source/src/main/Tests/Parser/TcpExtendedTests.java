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

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TcpExtendedTests {


    @Test
    public void TcpExtendedCodecTest() throws ParseException {
        String data = "000000000000005F8E0200000161F54139A30100000000000000000000000000000000F70002000100F704000000000000000101010258FFDB00550030FFDC00530047FFD10031FFFAFFD40036001DFFDE00410033FFD4001DFFB6FFE10029FF73FFDF002DFFDEFFCD000DFFF7FFD6001EFFEEFFDB002D000BFFD60025000AFFD2001E000CFFCD0012FFFDFFD600260005FFD9002C0011FFDC0033001CFFDA0028FFE7FFDB002A0000FFDC0028FFF1FFDB00290001FFDC0029FFFEFFDC002A0002FFDC0029FFFBFFDC002AFFFEFFDB002A0001FFDC002A0000FFDB002AFFFDFFDC002B0002FFDB002A0000FFDC002A0004FFDC002AFFFEFFDB002A0007FFDC00290001FFDD0029FFFEFFDB002A000AFFDD00280000FFDC00280005FFDC0024FFE2FFDD00290007FFDE0027FFFCFFDE00270000FFDD00280009FFD4001A0000FFD1000FFFFDFFE40022FFF6FFE3002A0003FFDF00340006FFE9003B0000FFE0002B0003FFE2002B0007FFE000280004FFE000250002FFE20023FFECFFE10023FFF8FFE200250006FFE200240000FFE20023FFFAFFE20024FFFCFFE30024FFF4FFDB0024FFF1FFB2002BFFDDFFE8004FFFF3FFD7003EFFE6000C004300430000004CFFED0004003E0001FFE20018FFDCFFE00010FFC1FFE00025FFB3FFD80026FFDAFFD10020FFBEFFC10011FFCFFF9BFFD3FFC5FF95FFD4FFB7FF80FFB9FFBBFF77FFAEFF64FF98FFFBFF5BFFA90032FF77FF8E0024FFAEFF880030FF8CFF4EFFF5FFDCFF3FFFE4FF36FF590053000FFF7E00C2FFB0FF2F006BFFECFF0E006FFFC5FF10007BFF40FEC4008BFF2DFE29007DFEC7FE40014C0095FDA00070FDE8FD99012BFCCFFE7C013DFE43FD7D0009FDB4FE2E0014FF1E005F013A021C00880184043001E70238051002B1FF3207E400000161F54135BA0100000000000000000000000000000000F70001000100F7040000000000000000020000AE47";

        byte[] bytes = Converter.hexStringToByteArray(data);
        System.out.println(Arrays.toString(bytes));
        //byte[] bytes = data.getBytes();
        // Head
        int preamble = ByteReader.ReadValue(bytes,4);
        int avlLength = ByteReader.ReadValue(bytes,4);

        Assert.assertEquals(0,preamble);
        Assert.assertEquals(95,avlLength);

        int codec = ByteReader.ReadValueUnsigned(bytes,1);
        int dataCount = ByteReader.ReadValueUnsigned(bytes,1);

        Assert.assertEquals(142,codec);
        Assert.assertEquals(2,dataCount);

        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "05-03-2018 08:22:28";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(0,gpsModel.GetLongitude());
        Assert.assertEquals(0,gpsModel.GetLatitude());
        Assert.assertEquals(0,gpsModel.GetAltitude());
        Assert.assertEquals(0,gpsModel.GetAngle());
        Assert.assertEquals(0,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        ioParser.setTakeByte(2);
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(247,recordIoModel.getEventID());
        Assert.assertEquals(2,recordIoModel.getElementCount());

        // Assert 1 bytes
        Assert.assertEquals(247,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getID());
        Assert.assertEquals(4,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getValue());

        // Assert X bytes
        Assert.assertEquals(257,recordIoModel.getRecordIO_records().getByteList_XList().get(0).getID());
        Assert.assertEquals(600,recordIoModel.getRecordIO_records().getByteList_XList().get(0).getValue());

        // Header
        headerParser = new HeaderParser();
        headerModel =  headerParser.ReadRecordHeader(bytes);

        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        dateInString = "05-03-2018 08:22:27";
        date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        gpsParser = new GPSParser();
        gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(0,gpsModel.GetLongitude());
        Assert.assertEquals(0,gpsModel.GetLatitude());
        Assert.assertEquals(0,gpsModel.GetAltitude());
        Assert.assertEquals(0,gpsModel.GetAngle());
        Assert.assertEquals(0,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        ioParser = new IOParser();
        ioParser.setTakeByte(2);
        recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(247,recordIoModel.getEventID());
        Assert.assertEquals(1,recordIoModel.getElementCount());

        // Assert 1 bytes
        Assert.assertEquals(247,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getID());
        Assert.assertEquals(4,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getValue());

        ByteReader.SetIndex(0);
    }

    @Test
    public void UdpExtendedCodecTest() throws ParseException {
        String data = "000000000000005F8E0200000161F54139A30100000000000000000000000000000000F70002000100F704000000000000000101010258FFDB00550030FFDC00530047FFD10031FFFAFFD40036001DFFDE00410033FFD4001DFFB6FFE10029FF73FFDF002DFFDEFFCD000DFFF7FFD6001EFFEEFFDB002D000BFFD60025000AFFD2001E000CFFCD0012FFFDFFD600260005FFD9002C0011FFDC0033001CFFDA0028FFE7FFDB002A0000FFDC0028FFF1FFDB00290001FFDC0029FFFEFFDC002A0002FFDC0029FFFBFFDC002AFFFEFFDB002A0001FFDC002A0000FFDB002AFFFDFFDC002B0002FFDB002A0000FFDC002A0004FFDC002AFFFEFFDB002A0007FFDC00290001FFDD0029FFFEFFDB002A000AFFDD00280000FFDC00280005FFDC0024FFE2FFDD00290007FFDE0027FFFCFFDE00270000FFDD00280009FFD4001A0000FFD1000FFFFDFFE40022FFF6FFE3002A0003FFDF00340006FFE9003B0000FFE0002B0003FFE2002B0007FFE000280004FFE000250002FFE20023FFECFFE10023FFF8FFE200250006FFE200240000FFE20023FFFAFFE20024FFFCFFE30024FFF4FFDB0024FFF1FFB2002BFFDDFFE8004FFFF3FFD7003EFFE6000C004300430000004CFFED0004003E0001FFE20018FFDCFFE00010FFC1FFE00025FFB3FFD80026FFDAFFD10020FFBEFFC10011FFCFFF9BFFD3FFC5FF95FFD4FFB7FF80FFB9FFBBFF77FFAEFF64FF98FFFBFF5BFFA90032FF77FF8E0024FFAEFF880030FF8CFF4EFFF5FFDCFF3FFFE4FF36FF590053000FFF7E00C2FFB0FF2F006BFFECFF0E006FFFC5FF10007BFF40FEC4008BFF2DFE29007DFEC7FE40014C0095FDA00070FDE8FD99012BFCCFFE7C013DFE43FD7D0009FDB4FE2E0014FF1E005F013A021C00880184043001E70238051002B1FF3207E400000161F54135BA0100000000000000000000000000000000F70001000100F7040000000000000000020000AE47";

        byte[] bytes = Converter.hexStringToByteArray(data);
        //byte[] bytes = data.getBytes();
        // Head
        int preamble = ByteReader.ReadValue(bytes,4);
        int avlLength = ByteReader.ReadValue(bytes,4);

        Assert.assertEquals(0,preamble);
        Assert.assertEquals(95,avlLength);

        int codec = ByteReader.ReadValueUnsigned(bytes,1);
        int dataCount = ByteReader.ReadValueUnsigned(bytes,1);

        Assert.assertEquals(142,codec);
        Assert.assertEquals(2,dataCount);

        // Header
        HeaderParser headerParser = new HeaderParser();
        RecordHeaderModel headerModel =  headerParser.ReadRecordHeader(bytes);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss" );
        String dateInString = "05-03-2018 08:22:28";
        Date date = sdf.parse(dateInString);

        Assert.assertEquals(date.toString()   ,headerModel.GetTimestamp().toString());
        Assert.assertEquals(1   ,headerModel.getRecordPriority());

        //GPS
        GPSParser gpsParser = new GPSParser();
        RecordGpsModel gpsModel =  gpsParser.ReadRecordGPS(bytes);

        Assert.assertEquals(0,gpsModel.GetLongitude());
        Assert.assertEquals(0,gpsModel.GetLatitude());
        Assert.assertEquals(0,gpsModel.GetAltitude());
        Assert.assertEquals(0,gpsModel.GetAngle());
        Assert.assertEquals(0,gpsModel.GetSatellites());
        Assert.assertEquals(0,gpsModel.GetSpeed());


        // IO
        IOParser ioParser = new IOParser();
        ioParser.setTakeByte(2);
        RecordIoModel recordIoModel = ioParser.GetElement(bytes);

        Assert.assertEquals(247,recordIoModel.getEventID());
        Assert.assertEquals(2,recordIoModel.getElementCount());

        // Assert 1 bytes
        Assert.assertEquals(247,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getID());
        Assert.assertEquals(4,recordIoModel.getRecordIO_records().getByteList_1List().get(0).getValue());

        ByteReader.SetIndex(0);
    }

}
