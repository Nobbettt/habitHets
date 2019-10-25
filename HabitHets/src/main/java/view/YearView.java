package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.calendar.Calender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the graphical representation of the year view in the application
 */
public class YearView extends AnchorPane implements ViewAble {
    @FXML private ScrollPane yearScroll;
    @FXML private GridPane yearGrid;
    private List<MonthInYear> monthInYears = new ArrayList<>();

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    public YearView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/year.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpYear();
    }

    private void setUpYear() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                MonthInYear miy = new MonthInYear();
                monthInYears.add(miy);
                yearGrid.add(miy, j, i);
            }
        }
    }

    @Override
    public void updateView(LocalDateTime currentDay) {
        for(int i=0; i< monthInYears.size(); i++){
            LocalDateTime l = Calender.getInstance().getLdtYearFromDate(currentDay).get(i);
            monthInYears.get(i).updateView(Calender.getInstance().getLdtMonthFromDate(l), Calender.getInstance().getMonthString(l));
        }

    }

    /**
     * Updates TimeLines position that is visible on screen every 60th second, is only being called if current view is either weekView or expandedDayView
     * @param hour
     * @param minute
     */
    @Override
    public void updateTimeLine(int hour, int minute) {
        // nothing to see here, just an dumb method required by the interface to be implemented
    }
}
