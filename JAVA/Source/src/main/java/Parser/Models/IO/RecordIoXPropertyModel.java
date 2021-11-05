package Parser.Models.IO;

public class RecordIoXPropertyModel extends RecordIoPropertyModel {
    private String data;

    /**
     * <h1>Record IO property construct</h1>
     * <p>record IO property requires ID and value </p>
     *
     * @param ID    property ID
     * @param value property value
     * @param data property data
     */
    public RecordIoXPropertyModel(int ID, long value, String data) {
        super(ID, value);
        this.data = data;
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
}
