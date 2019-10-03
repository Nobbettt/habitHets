package main.java.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HourView extends AnchorPane {

    public HourView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/hour.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
