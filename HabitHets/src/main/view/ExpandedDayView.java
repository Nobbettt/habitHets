package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ExpandedDayView extends StackPane implements ViewAble{
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane dayGrid;
    @FXML private Label weekDayLbl;
    @FXML private TextArea noteField;
    private DayEventListView dayEvents;
    private Note note;
    private Day day;
    private NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();



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
        day = (Day) days.get(0);
        dayEvents.updateDay(day, dayGrid.getCellBounds(1,0).getWidth());
        note = noteOrganizer.getNoteDate(day.getLdt().toLocalDate());
        noteField.clear();
        if(note != null){
            noteField.setText(note.getDescription());
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
        dayEvents = new DayEventListView(Calender.getInstant().getDayFromLDT(LocalDateTime.now()));
        dayGrid.add(dayEvents, 1, 0);

    }

    @FXML
    private void notehandle() {/*
        if(note == null){
            String noteString = noteField.getText();
            LocalDate d = day.getLdt().toLocalDate();
            note = noteOrganizer.addNote(noteString, d);
        } else{
            note.setDescription(noteField.getText());
            note.setDay(day.getLdt().toLocalDate());
        }*/
    }
}

