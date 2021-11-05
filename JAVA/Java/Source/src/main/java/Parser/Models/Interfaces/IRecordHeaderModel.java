package Parser.Models.Interfaces;

import java.util.Date;

public interface IRecordHeaderModel {
    Date GetTimestamp();
    long getRecordPriority();
    String toString();
}
