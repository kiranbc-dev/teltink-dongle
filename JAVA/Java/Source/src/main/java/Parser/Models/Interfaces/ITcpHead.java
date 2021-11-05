package Parser.Models.Interfaces;

public interface ITcpHead{

    int GetPreamble();
    int GetAvlDataLength();
    int GetCodec();
    int GetRecordCount();
    String toString();


}
