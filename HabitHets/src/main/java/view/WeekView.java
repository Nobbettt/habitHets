package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.calendar.Calender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for the graphical representation of the week view in the application
 */
public class WeekView extends StackPane implements ViewAble {
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
    private List<LocalDateTime> week;
    private List<DayEventListView> weekDayEvents;
    private Calender calender;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    public WeekView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.calender = new Calender();
        this.week = calender.getLdtWeekFromLdt(LocalDateTime.now());
        setUpWeek();
    }

    /**
     * Updated the week view and its content given a week list containing 7 day objects
     * @param currentDay
     */
    @Override
    public void updateView(LocalDateTime currentDay) {
        this.week = calender.getLdtWeekFromLdt(currentDay);
        for(int i = 0; i < 7; i++) {
            LocalDateTime tmpDay = week.get(i);
            String weekday = "" + week.get(i).getDayOfMonth() + "/" + week.get(i).getMonthValue(); //week.get(i)....getWeekdayfunction()
            weekDays.get(i).setText(weekday);
            weekDayEvents.get(i).updateDay(tmpDay, weekGrid.getCellBounds(1, 0).getWidth());
        }
    }

    /**
     * Updates TimeLines position that is visible on screen every 60th second, is only being called if current view is either weekView or expandedDayView
     * @param hour
     * @param minute
     */
    @Override
    public void updateTimeLine(int hour, int minute) {
        for (DayEventListView dView : weekDayEvents) {
            dView.updateTimeline(hour, minute);
        }
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it to make it possible to just update the existing content in future
     */
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
            DayEventListView dayEvents = new DayEventListView(week.get(i-1));
            weekGrid.add(dayEvents, i, 0);
            weekDayEvents.add(dayEvents);
        }
    }
}
