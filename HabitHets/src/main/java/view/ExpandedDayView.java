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

    @FXML private ScrollPane scrollPane;
    @FXML private GridPane dayGrid;
    @FXML private Label weekDayLbl;
    @FXML private TextArea noteField;
    @FXML private Button saveNoteButton;
    private DayEventListView dayEvents;
    private LocalDateTime day;
    private Facade facade;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
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

    /**
     * Updates TimeLines position that is visible on screen every 60th second, is only being called if current view is either weekView or expandedDayView
     * @param hour
     * @param minute
     */
    @Override
    public void updateTimeLine(int hour, int minute) {
        dayEvents.updateTimeline(hour, minute);
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it to make it possible to just update the existing content in future
     */
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

