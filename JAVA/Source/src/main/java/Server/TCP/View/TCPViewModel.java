package Server.TCP.View;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TCPViewModel implements ViewModel {
    private final StringProperty ClientMessage = new SimpleStringProperty();

    StringProperty clientMessageProperty() {
        return ClientMessage;
    }

    public String getClientMessage() {
        return ClientMessage.get();
    }

    public void setClientMessage(String clientMessage) {
        this.ClientMessage.set(clientMessage);
    }

}
