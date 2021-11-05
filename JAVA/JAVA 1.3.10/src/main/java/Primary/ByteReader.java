package Primary;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * <h1>Byte Reader</h1>
 * <p>This class has functions for reading the bytes efficiently instead of plain string.  </p>
 * Class contains of static methods.
 */
public class ByteReader {
    private static final Logger LOGGER = Logger.getLogger(ByteReader.class);
    /**
     * <h1>Index variable</h1>
     * <p>Index variable is important for reading and parsing data.
     * Every time when we call any of these methods,
     * the index is increased to keep the track from where to continue parsing data.
     * Once we have finished parsing data we must call function SetIndex to 0, so we can parse other data.</p>
     */
    private int index;
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public ByteReader(byte[] bytes)  {
        this(bytes, 0);
    }

    public ByteReader(byte[] bytes, int offset){
        index = offset;
        this.bytes = bytes;
    }

    /**
     * <h1>Read Value</h1>
     *
     * <p>Method reads the given byte array,
     * it converts to int and returns the value of selected bytes from byte array.</p>
     *
     * @return returns int value of converted bytes
     */
    public int ReadValue() {
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
     * @param take how many bytes we want to read
     * @return returns int value of converted bytes
     */
    public int ReadValue(int take) {

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
     * @param take how many bytes we want to read
     * @return returns Unsigned int value of converted bytes
     */
    public int ReadValueUnsigned(int take)
    {
        try {
            byte[] resultBytes = new byte[take];
            System.arraycopy(bytes, index, resultBytes, 0, take);
            index += take;
            ArrayUtils.reverse(resultBytes);
            if (take == 1)
                return resultBytes[0] & 0xFF;

            if (take == 2)
                return ByteConverter.toUInt16(resultBytes, 0);

            return 0;
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            LOGGER.error("Read Value Unsigned Exception ! : ",exception);
            return 0;
        }
    }

    /**
     * <h1>Read Value</h1>
     *
     * <p>Method reads the given byte array and how many bytes we would like to read,
     * it converts to long and returns the value of selected bytes from byte array.</p>
     *
     * @param take how many bytes we want to read
     * @return returns long value of converted bytes
     */
    public long ReadValueLong(int take) {
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


    public int ReadValueFromBytesShift(int take) {
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

    public long ReadValueFromBytesShiftLong(int take) {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        ArrayUtils.reverse(resultBytes);
        return ByteConverter.toInt64(resultBytes,0);
    }

    public String ReadImei(int take) {
        byte[] resultBytes = new byte[take];
        System.arraycopy(bytes, index, resultBytes, 0, take);
        index += take;
        return new String(resultBytes);
    }

    public int ReadValueFromBytesString(int take) {
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
    public int getAnInt(byte[] receivedBytes) {
        return receivedBytes[1] & 0xFF | (receivedBytes[0] & 0xFF) << 8;
    }

    public byte[] ReadBytes(int length) {
        return Arrays.copyOfRange(bytes, index, index+length);
    }

    public void SkipIndex(int n)
    {
        index += n;
    }

}
