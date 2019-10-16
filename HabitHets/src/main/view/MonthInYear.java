package main.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MonthInYear extends AnchorPane {

    public MonthInYear() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/monthInYear.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }



}
