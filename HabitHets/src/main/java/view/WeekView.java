package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.java.model.Day;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekView extends StackPane {
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane weekGrid;
    @FXML private GridPane dayGrid;
    @FXML private Label weekDay1;
    @FXML private Label weekDay2;
    @FXML private Label weekDay3;
    @FXML private Label weekDay4;
    @FXML private Label weekDay5;
    @FXML private Label weekDay6;
    @FXML private Label weekDay7;
    private List<Label> weekDays;
    private List<DayEventListView> weekDayEvents;

    public WeekView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpWeek();
    }

    public void updateWeekView(List<Day> week) {
        for(int i = 0; i < 7; i++) {

            Day tmpDay = new Day(LocalDateTime.now());

            String weekday = week.get(i).getDateString(); //week.get(i)....getWeekdayfunction()

            weekDays.get(i).setText(weekday);
            weekDayEvents.get(i).updateDay(tmpDay);
        }
    }

    private void setUpWeek() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        weekDays = new ArrayList<>();
        weekDays.addAll(Arrays.asList(weekDay1, weekDay2, weekDay3, weekDay4, weekDay5, weekDay6, weekDay7));

        weekDayEvents = new ArrayList<>();
        weekGrid.add(new HourColumnView(), 0, 0);
        for(int i = 1; i < 8; i++) {
            DayEventListView dayEvents = new DayEventListView();
            weekGrid.add(dayEvents, i, 0);
            weekDayEvents.add(dayEvents);
        }
    }

}
