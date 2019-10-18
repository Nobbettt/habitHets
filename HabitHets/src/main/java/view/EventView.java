package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import application.ControllerCalendar;
import model.Event;

import java.io.IOException;

public class EventView extends AnchorPane {
    @FXML
    private Label titleLabel;
    @FXML
    private Label timeLabel;
    Event event;
    ControllerCalendar controllerCalendar;

    public EventView(Event event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/event.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.event = event;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        titleLabel.setText(event.getTitle());
        timeLabel.setText(eventTimeString());
        controllerCalendar = ControllerCalendar.instance;
    }

    private String eventTimeString() {
        String hour;
        String minute;
        if (event.getStartTime().getHour() < 10) {
            hour = "0" + event.getStartTime().getHour();
        } else {
            hour = Integer.toString(event.getStartTime().getHour());
        }
        if (event.getStartTime().getMinute() < 10) {
            minute = "0" + event.getStartTime().getMinute();
        } else {
            minute = Integer.toString(event.getStartTime().getMinute());
        }
        return hour + ":" + minute;
    }

    @FXML
    public void editEventClicked(){
        System.out.println(controllerCalendar.toString());
        if (event != null) {
            controllerCalendar.editEventPressed(event);
        } else {
            System.out.println("FUUUUCK");
        }
    }
}
