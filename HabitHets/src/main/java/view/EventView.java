package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import Controller.ControllerCalendar;
import model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventView extends AnchorPane {
    @FXML
    private Label titleLabel;
    @FXML
    private Label timeLabel;
    private int id;
    private ControllerCalendar controllerCalendar;
    private Facade facade;
    private List<ViewListener> listeners = new ArrayList<>();

    EventView(int id) {
        this.id = id;
        facade = new Facade();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/event.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        titleLabel.setText(facade.getEventTitle(id));
        timeLabel.setText(facade.getEventStarttimeString(id));
        controllerCalendar = ControllerCalendar.instance;
    }

    @FXML
    public void editEventClicked(){
        controllerCalendar.editEventPressed(id);

    }

    public void addListener(ViewListener l){
        listeners.add(l);

    }

    private void notifyListener(String msg){
        for (ViewListener l : listeners)
            l.actOnUpdate(msg);
    }
}
