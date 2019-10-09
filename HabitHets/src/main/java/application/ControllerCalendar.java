package main.java.application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import main.model.Aggregate;
import main.model.CalendarAble;
import main.model.Day;
import main.model.Month;
import main.view.ExpandedDayView;
import main.view.ViewAble;
import main.view.WeekView;
import main.view.YearView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable {
    @FXML private GridPane mainGrid;
    @FXML private Label currentValueLbl;
    @FXML private Button prevBtn;
    @FXML private Button nextBtn;
    private AnchorPane calendarPane;
    private YearView yearView;
    private WeekView weekView;
    private ExpandedDayView expandedDayView;
    private Timeline timeLineCaller;
    public ViewAble currentView;
    private LocalDateTime timeNow;

    public ControllerCalendar() {
        yearView = new YearView();
        weekView = new WeekView();
        expandedDayView = new ExpandedDayView();
        currentView = weekView;


        updateTimeline();
    }

    // function is called every min to update timeline in GUI
    public void updateTimeline() {
        timeNow = LocalDateTime.now();
        currentView.updateTimeLine(timeNow.getHour(), timeNow.getMinute());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // timeLine setup stuff
        timeLineCaller = new Timeline(new KeyFrame(Duration.seconds(60), event -> updateTimeline()));
        timeLineCaller.setCycleCount(Timeline.INDEFINITE);
        timeLineCaller.play();

        setupCalender();
        //temporary
        Aggregate aggregate = new Aggregate();
        List<Day> week = aggregate.getWeekFromDate(LocalDateTime.now());

        renderWeek(week);

        AnchorPane ap = new AnchorPane();
        ap.setStyle("-fx-background-color: red");
        calendarPane.getChildren().add(ap);
    }

    @FXML
    private void prevClick() {
        System.out.println("prev");
        List<? extends CalendarAble> dataList = getPrevData();
        currentView.updateView(dataList);
    }

    @FXML
    private void nextClick() {
        System.out.println("next");
        List<? extends CalendarAble> dataList = getNextData();
        currentView.updateView(dataList);
    }

    private List<? extends CalendarAble> getPrevData() {
        List l = new ArrayList();
        return l;
    }

    private List<? extends CalendarAble> getNextData() {
        List l = new ArrayList();
        return l;
    }

    // Day stuff
    @FXML
    private void showCalendarDayClick() {
        //temporary
        Day d = new Day(LocalDateTime.now());
        renderDay(d);
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
        renderWeek(week);
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
        // tmp things
        List<Month> year = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            year.add(new Month(2019, i));
        }


        renderYear(year);
        System.out.println("Week");
    }

    private void renderYear(List<Month> year) {
        currentView = yearView;
        yearView.updateView(year);
        renderCalendar(yearView);
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
