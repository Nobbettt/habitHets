package main.java.application;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.model.Day;
import main.view.ExpandedDayView;
import main.view.WeekView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable {
    @FXML private GridPane mainGrid;
    private AnchorPane calendarPane;
    private WeekView weekView;

    public ControllerCalendar() {
        weekView = new WeekView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        //temporary
        Day d = new Day(LocalDateTime.now());
        renderDay(d);

        System.out.println("Day");
    }

    private void renderDay(Day day) {
        ExpandedDayView expandedDayView = ExpandedDayView.getExpandedDayView();
        ExpandedDayView.updateExpandedDayView(day);
        renderCalendar(expandedDayView);
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
        weekView.updateWeekView(week);
        renderCalendar(weekView);
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
        calendarPane = new AnchorPane();
        mainGrid.add(calendarPane, 1, 0);
    }

    private void renderCalendar(Node node) {
        if(calendarPane.getChildren() != null) {
            calendarPane.getChildren().clear();
        }
        calendarPane.getChildren().add(node);
        fitItem(calendarPane, node);
    }

    private void fitItem(AnchorPane parent, Node child) {
        parent.setTopAnchor(child, 0.0);
        parent.setRightAnchor(child, 0.0);
        parent.setBottomAnchor(child, 0.0);
        parent.setLeftAnchor(child, 0.0);
    }
}
