package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Day;

import java.io.IOException;

public class ExpandedDayView extends StackPane {
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
        setupView();
    }

    public void updateExpandedDayView(model.Day dayData) {
        String weekday = dayData.getDateString(); //week.get(i)....getWeekdayfunction()
        weekDayLbl.setText(weekday);
        dayEvents.updateDay(dayData);
    }

    private void setupView() {
        dayGrid.setGridLinesVisible(true);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        dayGrid.add(new HourColumnView(), 0, 0);
        dayEvents = new DayEventListView();
        dayGrid.add(dayEvents, 1, 0);
    }

    private void createDay(model.Day dayData) {

    }
}
