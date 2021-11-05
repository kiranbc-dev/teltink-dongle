package Parser;

import Primary.ByteConverter;
import Primary.ByteReader;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestRuns {

    @Test
    public void WithShiftParseTest1b()
    {
        String hex = "08";
        byte[] bytes = hexStringToByteArray(hex);
        int  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValueFromBytesShift(bytes,1);
            ByteReader.SetIndex(0);
        }
        Assert.assertEquals(8,n);
    }

    @Test
    public void WithShiftParseTest2b()
    {
        String hex = "0094";
        byte[] bytes = hexStringToByteArray(hex);
        int  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValueFromBytesShift(bytes,2);
            ByteReader.SetIndex(0);
        }
        Assert.assertEquals(148,n);
    }

    @Test
    public void WithShiftParseTest4b()
    {
        String hex = "0f0ea850";
        byte[] bytes = hexStringToByteArray(hex);
        int  n = 0;
        for(int i = 0 ; i < 10000000;i++)
        {
            n = ByteReader.ReadValueFromBytesShift(bytes,4);
            ByteReader.SetIndex(0);
        }
        Assert.assertEquals(252618832,n);
    }

    @Test
    public void WithShiftParseTest8b()
    {
        String hex = "0000013feb55ff74";
        byte[] bytes = hexStringToByteArray(hex);
        long  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValueFromBytesShiftLong(bytes,8);
            ByteReader.SetIndex(0);
        }

        Assert.assertEquals(1374042849140L,n);
    }

    @Test
    public void WithForParseTest1b()
    {
        String hex = "08";
        byte[] bytes = hex.getBytes();
        int  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValue(bytes,1);
            ByteReader.SetIndex(0);
        }
        System.out.println("With For "+n);
    }

    @Test
    public void WithForParseTest2b()
    {
        String hex = "0094";
        byte[] bytes = hex.getBytes();
        int  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValue(bytes,2);
            ByteReader.SetIndex(0);
        }
        System.out.println("With For "+n);
    }

    @Test
    public void WithForParseTest4b()
    {
        String hex = "0f0ea850";
        byte[] bytes = hex.getBytes();
        int  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValue(bytes,4);
            ByteReader.SetIndex(0);
        }
        System.out.println("With For "+n);
    }

    @Test
    public void WithForParseTest8b()
    {
        String hex = "0000013feb55ff74";
        byte[] bytes = hex.getBytes();
        long  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValueLong(bytes,8);
            ByteReader.SetIndex(0);
        }
        System.out.println("With For "+n);
    }

    @Test
    public void WithStringParseTest()
    {
        String hex = "0f0ea850";
        byte[] bytes = hex.getBytes();
        int  n = 0;
        for(int i = 0 ; i< 10000000;i++)
        {
            n = ByteReader.ReadValueFromBytesString(bytes,4);
            ByteReader.SetIndex(0);
        }
        System.out.println("With String "+n);
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    @Test
    public void ParsingWithShift()
    {
        byte[] bytes2 = {0x00, (byte)0x94};
        String data = "00F7";//148
      //  byte[] bytes = data.getBytes();
        byte[] bytes = hexStringToByteArray(data);
        ArrayUtils.reverse(bytes);
        int actual = ByteConverter.toInt16(bytes,0);
        System.out.println(actual);
    }


























}
