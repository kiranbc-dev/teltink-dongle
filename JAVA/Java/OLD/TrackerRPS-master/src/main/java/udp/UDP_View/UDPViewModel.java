package UDP.UDP_View;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UDPViewModel implements ViewModel {
    private final StringProperty ClientMessage = new SimpleStringProperty();


    public String getClientMessage() {
        return ClientMessage.get();
    }

    public StringProperty clientMessageProperty() {
        return ClientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.ClientMessage.set(clientMessage);
    }
}
