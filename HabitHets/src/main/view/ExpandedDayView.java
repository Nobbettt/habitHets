package main.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import main.model.Day;

import java.io.IOException;

public class ExpandedDayView extends StackPane {


    public ExpandedDayView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/week.fxml"));// todo
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }


    public void updateExpandedDayView(Day dayData) {

    }

    private void setupDayPane() {

    }

    private void createDay(Day dayData) {


    }
}
