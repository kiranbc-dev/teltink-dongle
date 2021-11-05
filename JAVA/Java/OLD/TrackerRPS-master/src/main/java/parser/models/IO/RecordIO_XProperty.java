package Parser.Models.IO;

public class RecordIO_XProperty extends RecordIO_Property {
    /**
     * <h1>Record IO property construct</h1>
     * <p>record IO property requires ID and value </p>
     *
     * @param ID    property ID
     * @param value property value
     */
    public RecordIO_XProperty(int ID, long value,String data) {
        super(ID, value);
        this.data = data;
    }

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
