package view;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HourColumnView extends VBox {
    public HourColumnView() {
        for(int i = 0; i < 24; i++) {
            AnchorPane block = new AnchorPane();
            this.getStylesheets().add(getClass().getResource("../resources/css/day-style.css").toExternalForm());
            block.getStyleClass().add("hour-wrapper");
            this.getStyleClass().add("hour-wrapper-column");// todo add right border to this
            this.getChildren().add(block);

            Label hourLbl = new Label(i+":00");
            hourLbl.getStyleClass().add("hour-lbl");
            block.getChildren().add(hourLbl);
        }
    }
}

