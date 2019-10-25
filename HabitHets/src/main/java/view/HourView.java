package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * This class is responsible for the hours that are represented in the DayEventList view that is used in expanded day- and week view
 */
class HourView extends AnchorPane {

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    HourView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hour.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
