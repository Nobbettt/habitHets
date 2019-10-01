package main.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.model.Day;

import java.io.IOException;

public class DayView extends AnchorPane  {


    public DayView(Day day) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/day.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
