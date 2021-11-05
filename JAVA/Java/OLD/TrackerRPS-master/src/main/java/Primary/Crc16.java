package Primary;

public class Crc16 {

    private int offset;
    private int polynom;
    private int preset;

    /**
     * <h1>Construct for Crc16 object</h1>
     * <p>requires int offset, int polynom, int preset,
     * if left blank the values will be set to 0 and polynom to 0xA001</p>
     * @param offset the offset of the bytes to be extracted
     * @param polynom a bit mask of the generator polynomial
     * */
    public Crc16(int offset, int polynom) {
        this.offset = offset;
        this.polynom = polynom;
    }

    public Crc16() {
        offset = 0;
        polynom = 0xA001;
        preset = 0;
    }


    /**
     * <h1>CRC16 calculates bytes array</h1>
     * <p>Calculates crc16 of given bytearray.</p>
     * @param bytes this is the first parameter for calculating bytes
     * @return returns int value
     * */
    public int getCRC(byte[] bytes)
    {
        return CalcCrc16(bytes);
    }

    /**
     * Used to calculate crc16
     * @param buffer bytes array to calculate
     * @return returns int value
     * */
    private int CalcCrc16(byte[] buffer)
    {
        //preset &= 0xFFFF;
        polynom &= 0xFFFF;

        int crc = preset;
        for (int i = 0; i < buffer.length; i++)
        {
            int data = buffer[(i + offset) % buffer.length] & 0xFF;
            crc ^= data;
            for (int j = 0; j < 8; j++)
            {
                if ((crc & 0x0001) != 0)
                {
                    crc = (crc >> 1) ^ polynom;
                }
                else
                {
                    crc = crc >> 1;
                }
            }
        }
        return crc & 0xFFFF;
    }

}