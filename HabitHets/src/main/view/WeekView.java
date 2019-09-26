package main.view;

import javafx.scene.layout.*;
import main.model.Day;

import java.util.ArrayList;
import java.util.List;

public class WeekView extends StackPane {
    private static WeekView weekView;
    private static GridPane weekGrid;
    private static List<DayView> days;

    private WeekView() {}

    public static WeekView getWeekView() {
        if (weekView == null) {
            weekView = new WeekView();
            setupWeekGrid();
        }
        return weekView;
    }

    public static void updateWeekView(List<Day> week) {
        if(days == null) {
            createWeek(week);
        } else {
            updateWeek(week);
        }
    }

    private static void setupWeekGrid() {
        weekGrid = new GridPane();
        weekGrid.setStyle("-fx-background-color: #47bcad");
        RowConstraints height = new RowConstraints();
        height.setPercentHeight(100);
        ColumnConstraints width = new ColumnConstraints();
        double calcWidth = Math.ceil((double)100 / 7);
        width.setPercentWidth(calcWidth);
        weekGrid.getRowConstraints().setAll(height);
        weekGrid.getColumnConstraints().setAll(width);

        weekView.getChildren().add(weekGrid);
    }

    private static void createWeek(List<Day> week) {
        days = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            StackPane sp = new StackPane();
            weekGrid.add(sp, i, 0);
            DayView dayView = new DayView(week.get(i));
            sp.getChildren().add(dayView);
            days.add(dayView);
        }
    }

    private static void updateWeek(List<Day> week) {
        for(int i = 0; i < 7; i++) {
            //todo
            // update local week list and update fxml value
        }
    }

}
