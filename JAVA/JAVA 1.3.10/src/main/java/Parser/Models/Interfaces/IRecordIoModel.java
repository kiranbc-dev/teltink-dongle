package Parser.Models.Interfaces;

import Parser.Models.IO.RecordIoModelList;

public interface IRecordIoModel {

    RecordIoModelList getRecordIO_records();
    int getElementCount();
    int getEventID();


}
