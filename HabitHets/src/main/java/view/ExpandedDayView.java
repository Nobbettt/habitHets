package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.*;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/expandedDayView.fxml"));// todo
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
        dayEvents.updateDay(day.getLdt(), dayGrid.getCellBounds(1,0).getWidth());
        note = noteOrganizer.getNoteDate(day.getLdt());
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
        dayEvents = new DayEventListView(LocalDateTime.now());
        dayGrid.add(dayEvents, 1, 0);

    }

    @FXML
    private void notehandle() {
        System.out.println("gg");
        if(note == null){
            String noteString = noteField.getText();
            LocalDateTime d = day.getLdt();
            note = noteOrganizer.addNote(noteString, d);
        } else{
            note.setDescription(noteField.getText());
            note.setDay(day.getLdt());
        }
    }
}

