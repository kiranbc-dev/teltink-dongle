package Parser.Models.IO;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>Record IO List</h1>
 * <p>Record IO Elements lists object that holds lists of 1,2,4 and 8 bytes elements lists.
 * The object implements Serializable.</p>
 * */
public class RecordIO_ElementsLists implements Serializable {

    private List<RecordIO_Property> byteList_1List;
    private List<RecordIO_Property> byteList_2List;
    private List<RecordIO_Property> byteList_4List;
    private List<RecordIO_Property> byteList_8List;
    private List<RecordIO_Property> byteList_XList;

    public RecordIO_ElementsLists(){}

    /**
     * <h1>Get 1 byte list</h1>
     * @return returns list of 1 byte elements
     * */
    public List<RecordIO_Property> getByteList_1List() {
        return byteList_1List;
    }

    public void setByteList_1List(List<RecordIO_Property> byteList_1List) {
        this.byteList_1List = byteList_1List;
    }

    /**
     * <h1>Get 2byte list</h1>
     * @return returns list of 2 byte elements
     * */
    public List<RecordIO_Property> getByteList_2List() {
        return byteList_2List;
    }

    public void setByteList_2List(List<RecordIO_Property> byteList_2List) {
        this.byteList_2List = byteList_2List;
    }

    /**
     * <h1>Get 4 byte list</h1>
     * @return returns list of 4 byte elements
     * */
    public List<RecordIO_Property> getByteList_4List() {
        return byteList_4List;
    }

    public void setByteList_4List(List<RecordIO_Property> byteList_4List) {
        this.byteList_4List = byteList_4List;
    }

    /**
     * <h1>Get 4 byte list</h1>
     * @return returns list of 4 byte elements
     * */
    public List<RecordIO_Property> getByteList_8List() {
        return byteList_8List;
    }

    public void setByteList_8List(List<RecordIO_Property> byteList_8List) {
        this.byteList_8List = byteList_8List;
    }

    public List<RecordIO_Property> getByteList_XList() {
        return byteList_XList;
    }

    public void setByteList_XList(List<RecordIO_Property> byteList_XList) {
        this.byteList_XList = byteList_XList;
    }
}
