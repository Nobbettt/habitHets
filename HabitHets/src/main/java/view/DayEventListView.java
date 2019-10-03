package main.java.view;

import javafx.scene.layout.VBox;
import main.java.model.Day;

public class DayEventListView extends VBox {

    public DayEventListView() {

        for(int i = 0; i < 24; i++) {
            HourView hour = new HourView();
            this.getChildren().add(hour);
        }
    }

    public void updateDay(Day day) {

    }
}
