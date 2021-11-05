package Parser.Models.IO;

import java.io.Serializable;

/**
 * <h1>Record IO Property</h1>
 * <p>Record IO Element holds ID and value.
 * The object implements Serializable.</p>
 * */
public class RecordIO_Property implements Serializable {

    private int ID;
    private long value;
    private String data;

    /**
     * <h1>Record IO property construct</h1>
     * <p>record IO property requires ID and value </p>
     * @param ID property ID
     * @param value property value
     * */
    public RecordIO_Property(int ID, long value) {
        this.ID = ID;
        this.value = value;
    }

    public RecordIO_Property(int ID, long length, String data) {
        this.ID = ID;
        this.value = length;
        this.data = data;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
