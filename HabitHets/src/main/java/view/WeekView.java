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

    public WeekView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.calender = Calender.getInstance();
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
     * updates the horizontal line that indicates the time given the two parameters
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
     * Initializes and prepares the columns for content
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
