package Primary;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

/**
 * <h1>Byte Reader</h1>
 * <p>This class has functions for reading the bytes efficiently instead of plain string.  </p>
 * Class contains of static methods.
 */
public class ByteReader {

    /**
     * <h1>Index variable</h1>
     * <p>Index variable is important for reading and parsing data.
     * Every time when we call any of these methods,
     * the index is increased to keep the track from where to continue parsing data.
     * Once we have finished parsing data we must call function SetIndex to 0, so we can parse other data.</p>
     */
    private static int index  = 0;

    /**
     * <h1>Read Value</h1>
     *
     * <p>Method reads the given byte array,
     * it converts to int and returns the value of selected bytes from byte array.</p>
     *
     * @param bytes Bytes that we want to read from
     * @return returns int value of converted bytes
     */
    public static int ReadValue(byte[] bytes) {
        int length = bytes.length;
        byte[] resultBytes = new byte[length];
        System.arraycopy(bytes, 0, resultBytes, 0, length);
        ArrayUtils.reverse(resultBytes);
        if(length == 1)
            return resultBytes[0];

        if(length == 2)
            return ByteConverter.toInt16(resultBytes,0);

        if(length == 4)
            return ByteConverter.toInt32(resultBytes,0);

        return 0;
    }

    /**
     * <h1>Read Value</h1>
     *
     * <p>Method reads the given byte array and how many bytes we would like to read,
     * it converts to int and returns the value of selected bytes from byte array.</p>
     *
     * @param bytes Bytes that we want to read from
     * @param take how many bytes we want to read
     * @return returns int value of converted bytes
     */
    public static int ReadValue(byte[] bytes, int take) {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        ArrayUtils.reverse(resultBytes);
        if(take == 1)
            return resultBytes[0];

        if(take == 2)
            return ByteConverter.toInt16(resultBytes,0);

        if(take == 4)
            return ByteConverter.toInt32(resultBytes,0);

        if(take == 8)
            return (int) ByteConverter.toInt64(resultBytes,0);

        return 0;
    }

    /**
     * <h1>Read Value Unsigned</h1>
     *
     * <p>Method reads the given byte array and how many bytes we would like to read,
     * it converts to int and returns the unsigned value of selected bytes from byte array.</p>
     *
     * @param bytes Bytes that we want to read from
     * @param take how many bytes we want to read
     * @return returns Unsigned int value of converted bytes
     */
    public static int ReadValueUnsigned(byte[] bytes, int take)
    {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        ArrayUtils.reverse(resultBytes);
        if(take == 1)
            return resultBytes[0] & 0xFF ;

        if(take == 2)
            return ByteConverter.toUInt16(resultBytes,0);

        return 0;
    }

    /**
     * <h1>Read Value</h1>
     *
     * <p>Method reads the given byte array and how many bytes we would like to read,
     * it converts to long and returns the value of selected bytes from byte array.</p>
     *
     * @param bytes Bytes that we want to read from
     * @param take how many bytes we want to read
     * @return returns long value of converted bytes
     */
    public static long ReadValueLong(byte[] bytes, int take) {
        byte[] resultBytes = new byte[take];
        try{
            System.arraycopy(bytes, index, resultBytes, 0, take);
            index += take;
            ArrayUtils.reverse(resultBytes);
            return ByteConverter.toInt64(resultBytes,0);
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Byte array too short in Read Value Long\nBytes length: "+bytes.length+" | tried to read: "+take);
        }
       return -1;
    }


    public static int ReadValueFromBytesShift(byte[] bytes, int take) {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        ArrayUtils.reverse(resultBytes);
        if(take == 1)
            return resultBytes[0];

        if(take == 2)
            return ByteConverter.toInt16(resultBytes,0);

        if(take == 4)
            return ByteConverter.toInt32(resultBytes,0);

        return 0;
    }

    public static long ReadValueFromBytesShiftLong(byte[] bytes, int take) {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        ArrayUtils.reverse(resultBytes);
        return ByteConverter.toInt64(resultBytes,0);
    }

    public static String ReadImei(byte[] bytes, int take) {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        return new String(resultBytes);
    }

    public static int ReadValueFromBytesString(byte[] bytes, int take) {
        take <<= 1;
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        String b = new String(resultBytes);
        return Converter.ConvertHexToInt(b);
    }

    /**
     * <h1>Get An Int</h1>
     *
     * <p>For converting bytes to int</p>
     *
     * @param receivedBytes bytes we want to convert to int
     *
     * @return returns an int value
     */
    public static int getAnInt(byte[] receivedBytes) {
        return receivedBytes[1] & 0xFF | (receivedBytes[0] & 0xFF) << 8;
    }

    public static void SetIndex(int n) {
        index = n;
    }

    public static void SetIndexSkipBytes(int n) {
        index += n;
    }

}
