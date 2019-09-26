package main.view;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import main.model.Day;

public class ExpandedDayView extends StackPane {
    private static ExpandedDayView expandedDayView;
    private static SplitPane splitPane;
    private static Day day;
    private static StackPane dayPane;
    private static StackPane notePane;

    private ExpandedDayView() { }

    public static ExpandedDayView getExpandedDayView() {
        if (expandedDayView == null) {
            expandedDayView = new ExpandedDayView();
            setupDayPane();
        }
        return expandedDayView;
    }

    public static void updateExpandedDayView(Day dayData) {
        if(day == null) {
            createDay(dayData);
        } else {
            updateDay(dayData);
        }
    }

    private static void setupDayPane() {
        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);

        dayPane = new StackPane();
        notePane = new StackPane();

        expandedDayView.getChildren().add(splitPane);
    }

    private static void createDay(Day dayData) {
        DayView dayView = new DayView(dayData);
        day = dayData;
        dayPane.getChildren().add(dayView);

        // add note element whatever
        //notePane.getChildren().add();
        //todo

        splitPane.getItems().addAll(dayPane, notePane);
        splitPane.setDividerPositions(0.3);

    }

    private static void updateDay(Day day) {
       //todo
        // update local day object and update fxml value
    }

}
