package main.view;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import main.model.Day;

import java.util.List;

public class WeekView extends AnchorPane {
    private GridPane weekGrid;
    private List<Day> week;

    public WeekView(List<Day> week) {
        this.week = week;
        setupWeekGrid();
        initWeek();
    }

    public GridPane getWeekGrid() {
        return weekGrid;
    }

    private void setupWeekGrid() {
        weekGrid = new GridPane();
        weekGrid.setStyle("-fx-background-color: #47bcad");
    }

    private void initWeek() {
        RowConstraints height = new RowConstraints();
        height.setPercentHeight(100);
        ColumnConstraints width = new ColumnConstraints();
        double calcWidth = Math.ceil((double)100 / 7);
        width.setPercentWidth(calcWidth);
        weekGrid.getRowConstraints().setAll(height);
        weekGrid.getColumnConstraints().setAll(width);

        for(int i = 0; i < 7; i++) {
            AnchorPane aPane = new AnchorPane();
            weekGrid.add(aPane, i, 0);

            DayView dayView = new DayView(week.get(i));
            aPane.getChildren().add(dayView);
            fitItem(aPane, dayView);
        }
    }

    private void fitItem(AnchorPane parent, Node child) {
        parent.setTopAnchor(child, 0.0);
        parent.setRightAnchor(child, 0.0);
        parent.setBottomAnchor(child, 0.0);
        parent.setLeftAnchor(child, 0.0);
    }



}
