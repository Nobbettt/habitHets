package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Facade;

import java.io.IOException;
import java.time.LocalDateTime;

public class ExpandedDayView extends StackPane implements ViewAble {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane dayGrid;
    @FXML
    private Label weekDayLbl;
    @FXML
    private TextArea noteField;
    @FXML
    private Button saveNoteButton;
    private DayEventListView dayEvents;
    private LocalDateTime day;
    private Facade facade;


    public ExpandedDayView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/expandedDayView.fxml"));// todo
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.facade = new Facade();
        setupDayView();


    }

    @Override
    public void updateView(LocalDateTime currentDay) {
        String weekday = "" + currentDay.getDayOfMonth() + "/" + currentDay.getMonthValue();//week.get(i)....getWeekdayfunction()
        weekDayLbl.setText(weekday);
        day = currentDay;
        dayEvents.updateDay(day, dayGrid.getCellBounds(1, 0).getWidth());
        if (facade.noteOnDay(day)) {
            noteField.setText(facade.getNoteTextFromLdt(day));
        } else {
            noteField.clear();
        }
    }

    @Override
    public void updateTimeLine(int hour, int minute) {
        dayEvents.updateTimeline(hour, minute);
    }

    private void setupDayView() {
        dayGrid.setGridLinesVisible(true);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        dayGrid.add(new HourColumnView(), 0, 0);
        dayEvents = new DayEventListView(LocalDateTime.now());
        dayGrid.add(dayEvents, 1, 0);

    }

    @FXML
    private void notehandle() {
        facade.updateNote(noteField.getText(), day);
    }

}

