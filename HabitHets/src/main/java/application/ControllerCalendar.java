package main.java.application;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.model.Day;
import main.view.WeekView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable {
    @FXML private GridPane mainGrid;
    private AnchorPane calendarAnchor;


    public ControllerCalendar() {

        System.out.println("first?");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainGrid.setGridLinesVisible(true);


        setupCalender();

        //temporary
        Day d = new Day(LocalDateTime.now());
        List<Day> week = new ArrayList<>();
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);

        renderWeek(week);

    }

    // Day stuff
    @FXML
    private void showCalendarDayClick() {
        System.out.println("Day");
    }

    private void renderDay(Day day) {

        //renderCalendar();
    }

    // Week stuff
    @FXML
    private void showCalendarWeekClick() {
        //temporary
        Day d = new Day(LocalDateTime.now());
        List<Day> week = new ArrayList<>();
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);

        renderWeek(week);
        System.out.println("Week");
    }

    private void renderWeek(List<Day> week) {
        WeekView weekView = new WeekView(week);
        GridPane weekGrid = weekView.getWeekGrid();
        renderCalendar(weekGrid);
    }

    // Month stuff
    @FXML
    private void showCalendarMonthClick() {
        System.out.println("Month");
    }

    // Year stuff
    @FXML
    private void showCalendarYearClick() {
        System.out.println("year");
    }

    private void setupCalender() {
        calendarAnchor = new AnchorPane();
        calendarAnchor.setStyle("-fx-background-color: red");
        mainGrid.add(calendarAnchor, 1, 0);
    }

    private void renderCalendar(Node node) {
        if(calendarAnchor.getChildren() != null) {
            calendarAnchor.getChildren().removeAll();
        }
        calendarAnchor.getChildren().add(node);
        fitItem(calendarAnchor, node);
    }

    private void fitItem(AnchorPane parent, Node child) {
        parent.setTopAnchor(child, 0.0);
        parent.setRightAnchor(child, 0.0);
        parent.setBottomAnchor(child, 0.0);
        parent.setLeftAnchor(child, 0.0);
    }
}
