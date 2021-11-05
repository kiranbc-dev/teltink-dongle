package Source;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PrimaryUIViewModel implements ViewModel {
    private final StringProperty MemoryData = new SimpleStringProperty();

    public String getMemoryData() {
        return MemoryData.get();
    }

    public StringProperty memoryDataProperty() {
        return MemoryData;
    }

    public void setMemoryData(String memoryData) {
        this.MemoryData.set(memoryData);
    }
}