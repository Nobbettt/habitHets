package main.java.application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import main.model.Aggregate;
import main.model.Day;
import main.view.ExpandedDayView;
import main.view.ViewAble;
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
    private ExpandedDayView expandedDayView;
    private Timeline timeLineCaller;
    private ViewAble currentView;
    public ControllerCalendar() {
        weekView = new WeekView();
        expandedDayView = new ExpandedDayView();
        currentView = weekView;

    }

    // function is called every min to update timeline in GUI
    private void updateTimeline() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // timeLine setup stuff
        timeLineCaller = new Timeline(new KeyFrame(Duration.seconds(5), event -> updateTimeline()));
        timeLineCaller.setCycleCount(Timeline.INDEFINITE);
        timeLineCaller.play();

        setupCalender();

        //temporary
        Aggregate aggregate = new Aggregate();
        List<Day> week = aggregate.getWeekFromDate(LocalDateTime.now());
        /*
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        */

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
        List<Day> days = new ArrayList<>();
        days.add(day);
        currentView = expandedDayView;
        expandedDayView.updateView(days);
        renderCalendar(expandedDayView);
    }

    // Week stuff
    @FXML
    private void showCalendarWeekClick() {
        //temporary
        Aggregate aggregate = new Aggregate();
        List<Day> week = aggregate.getWeekFromDate(LocalDateTime.now());
        /*week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        week.add(d);
        */

        renderWeek(week);
        System.out.println("Week");
    }

    private void renderWeek(List<Day> week) {
        currentView = weekView;
        weekView.updateView(week);
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
