package view;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * This class is responsible for the time stamps in the left column, that can be found in the expanded day- and week view
 */
class HourColumnView extends VBox {

    /**
     * Fills a container (VBox) with timestamp objects
     * Uses properties from the real hour vies object to mock its diemsions
     */
    HourColumnView() {
        for (int i = 0; i < 24; i++) {
            AnchorPane block = new AnchorPane();
            this.getStylesheets().add(getClass().getResource("/css/day-style.css").toExternalForm());
            block.getStyleClass().add("hour-wrapper");
            this.getStyleClass().add("hour-wrapper-column");// todo add right border to this
            this.getChildren().add(block);

            Label hourLbl = new Label(i + ":00");
            hourLbl.getStyleClass().add("hour-lbl");
            block.getChildren().add(hourLbl);
        }
    }
}

