package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.Facade;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
    public void updateView(List<LocalDateTime> days) {
        Facade f = new Facade();
        String weekday = "" + days.get(0).getDayOfMonth() + "/" + days.get(0).getMonthValue();//week.get(i)....getWeekdayfunction()
        weekDayLbl.setText(weekday);
        day = days.get(0);
        dayEvents.updateDay(day, dayGrid.getCellBounds(1, 0).getWidth());
        noteField.clear();
        if (f.noteOnDay(day)) {
            noteField.setText(f.getNoteTextFromLdt(day));
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
        Facade f = new Facade();
        f.updateNote(noteField.getText(), day);
    }

}

