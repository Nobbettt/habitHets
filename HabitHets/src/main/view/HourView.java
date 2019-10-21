package main.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

class HourView extends AnchorPane {

    HourView() {

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
