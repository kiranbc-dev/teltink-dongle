package Source.View;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PrimaryUIViewModel implements ViewModel {
    private final StringProperty MemoryData = new SimpleStringProperty();

    public String getMemoryData() {
        return MemoryData.get();
    }

    private void setMemoryData(String memoryData) {
        this.MemoryData.set(memoryData);
    }

    private StringProperty memoryDataProperty() {
        return MemoryData;
    }
}