package Parser.Models.IO;

import java.io.Serializable;

/**
 * <h1>Record IO Property</h1>
 * <p>Record IO Element holds ID and value.
 * The object implements Serializable.</p>
 */
public class RecordIoPropertyModel implements Serializable {

    private int ID;
    private long value;
    private String data;

    /**
     * <h1>Record IO property construct</h1>
     * <p>record IO property requires ID and value </p>
     *
     * @param ID    property ID
     * @param value property value
     */
    public RecordIoPropertyModel(int ID, long value) {
        this.ID = ID;
        this.value = value;
    }

    /**
     * <h1>GetData</h1>
     * <p>Gets X property data.</p>
     * @return data
     */
    public String GetData() {
        return data;
    }

    /**
     * <h1>Set Data</h1>
     * <p>Sets data for X io property.</p>
     * @param data is inserted hex value
     */
    public void setData(String data) {
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

    public String toString()
    {
        return ("\nID      : " + getID()+"\n"+
        "\tValue   : " + getValue());
    }
}
