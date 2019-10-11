package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.CalendarAble;
import main.model.Calender;
import main.model.Day;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ExpandedDayView extends StackPane implements ViewAble{
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane dayGrid;
    @FXML private Label weekDayLbl;
    private DayEventListView dayEvents;



    public ExpandedDayView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/expandedDayView.fxml"));// todo
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setupDayView();

    }

    @Override
    public void updateView(List<? extends CalendarAble> days) {
        String weekday = days.get(0).getString(); //week.get(i)....getWeekdayfunction()
        weekDayLbl.setText(weekday);
        Day day = (Day) days.get(0);
        dayEvents.updateDay(day);
    }

    @Override
    public void updateTimeLine(int hour, int minute) {
        System.out.println("gg");
        dayEvents.updateTimeline(hour, minute);
    }

    private void setupDayView() {
        dayGrid.setGridLinesVisible(true);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        dayGrid.add(new HourColumnView(), 0, 0);
        dayEvents = new DayEventListView(Calender.getInstant().getDayFromLDT(LocalDateTime.now()));
        dayGrid.add(dayEvents, 1, 0);
    }

    private void createDay(Day dayData) {

    }
}
