package Parser.Models.IO;
import Primary.ByteReader;

import java.util.ArrayList;
import java.util.List;

public class McanIoProperty extends RecordIoPropertyModel {

    private List<ManualCanIo> ioElements;

    public McanIoProperty(int ID, byte[] value) {
        super(ID, value.length);
        ioElements = Parse(value);
    }

    @Override
    public String GetData() {
        return "Count  "+ioElements.size()+FormatMcanIoList();
    }

    private List<ManualCanIo> Parse(byte[] data) {

        ByteReader reader = new ByteReader(data);
        int numberOfElements = reader.ReadValue(1);
        List<ManualCanIo> ioElements = new ArrayList<>();
        int[] sizes = new int[]{1, 2, 4, 8};
        for (int i = 0; i < sizes.length; i++) {
            int numberOfLists = reader.ReadValue(1);
            for (int listNumber = 0; listNumber < numberOfLists; listNumber++) {
                int id = reader.ReadValue(4);
                int size = sizes[i];
                int value = reader.ReadValue(size);
                ioElements.add(new ManualCanIo(id, value, size));
            }
        }
        return ioElements;
    }

    private String FormatMcanIoList()
    {
        StringBuilder sb = new StringBuilder();
        for (ManualCanIo mcan: ioElements) {
            sb.append("\n").append(mcan.toString());
        }
        return sb.toString();
    }

}
