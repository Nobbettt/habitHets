package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.java.application.ControllerCalendar;
import main.model.IPlanable;

import java.io.IOException;

public class EventView extends AnchorPane {
    @FXML
    private Label titleLabel;
    @FXML
    private Label timeLabel;
    IPlanable ip;
    ControllerCalendar controllerCalendar;

    public EventView(IPlanable ip) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/event.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.ip = ip;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        titleLabel.setText(ip.getInfo().get(0));
        timeLabel.setText(eventTimeString());
        controllerCalendar = ControllerCalendar.instance;
    }

    private String eventTimeString() {
        String hour;
        String minute;
        if (Integer.valueOf(ip.getInfo().get(6)) < 10) {
            hour = "0" + Integer.valueOf(ip.getInfo().get(6));
        } else {
            hour = ip.getInfo().get(6);
        }
        if (Integer.valueOf(ip.getInfo().get(7)) < 10) {
            minute = "0" + Integer.valueOf(ip.getInfo().get(7));
        } else {
            minute = ip.getInfo().get(7);
        }
        return hour + ":" + minute;
    }

    @FXML
    public void editEventClicked(){
        System.out.println(controllerCalendar.toString());
        if (ip != null) {
            controllerCalendar.editEventPressed(ip);
        } else {
            System.out.println("FUUUUCK");
        }
    }
}
