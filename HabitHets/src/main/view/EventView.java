package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.java.application.ControllerCalendar;
import main.model.Facade;

import java.io.IOException;

public class EventView extends AnchorPane {
    @FXML
    private Label titleLabel;
    @FXML
    private Label timeLabel;
    private int id;
    ControllerCalendar controllerCalendar;
    Facade facade;

    EventView(int id) {
        this.id = id;
        facade = new Facade();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/event.fxml"));
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
}
