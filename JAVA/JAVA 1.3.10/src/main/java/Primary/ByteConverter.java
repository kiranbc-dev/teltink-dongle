package Primary;

public class ByteConverter {

    public static float toFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(toInt32(data,offset));
    }

    public static double toDouble(byte[] data, int offset) {
        return Double.longBitsToDouble(toInt64(data, offset));
    }

    static int toUInt16(byte[] data, int offset)
    {
        return (data[offset] & 0xFF | (data[offset + 1] & 0xFF) << 8);
    }

    public static short toInt16(byte[] data, int offset) {
        return (short) (data[offset] & 0xFF | (data[offset + 1] & 0xFF) << 8);
    }

    public static int toInt32(byte[] data, int offset) {
        return (data[offset] & 0xFF) |
                ((data[offset + 1] & 0xFF) << 8)
                | ((data[offset + 2] & 0xFF) << 16)
                | ((data[offset + 3] & 0xFF) << 24);
    }

    static long toInt64(byte[] data, int offset) {
        return (((long) (data[offset + 7] & 0xff) << 56)
                | ((long) (data[offset + 6] & 0xff) << 48)
                | ((long) (data[offset + 5] & 0xff) << 40)
                | ((long) (data[offset + 4] & 0xff) << 32)
                | ((long) (data[offset + 3] & 0xff) << 24)
                | ((long) (data[offset + 2] & 0xff) << 16)
                | ((long) (data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff));
    }
}
