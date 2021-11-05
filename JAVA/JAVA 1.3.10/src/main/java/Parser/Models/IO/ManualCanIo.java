package Parser.Models.IO;

public class ManualCanIo {

    private int id;
    private long value;
    private int size;


    public ManualCanIo(int id, long value, int size) {
        this.id = id;
        this.value = value;
        this.size = size;
    }


    @Override
    public String toString(){
        return "Element size : "+ size +"b\n\tid: " + id+" : "+value;
    }

}
