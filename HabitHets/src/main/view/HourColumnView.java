package main.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HourColumnView extends VBox {
    private AnchorPane block;
    public HourColumnView() {

        for(int i = 0; i < 24; i++) {
            AnchorPane block = new AnchorPane();
            this.getStylesheets().add(getClass().getResource("../resources/css/day-style.css").toExternalForm());
            block.getStyleClass().add("hour-wrapper");
            this.getChildren().add(block);
        }

    }
}
