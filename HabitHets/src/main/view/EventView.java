package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.model.Event;

import java.io.IOException;

public class EventView extends AnchorPane {

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    Event event;

    public EventView(Event event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/event.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.event = event;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        titleLabel.setText(event.getTitle());
        timeLabel.setText("" + event.getStartTime().getHour() + ":" + event.getStartTime().getMinute() + " - " + event.getEndTime().getHour() + ":" + event.getEndTime().getMinute());
    }
}
