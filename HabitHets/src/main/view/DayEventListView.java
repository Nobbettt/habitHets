package view;

import javafx.scene.layout.VBox;
import model.Day;

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
