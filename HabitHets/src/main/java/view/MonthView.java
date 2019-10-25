package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.calendar.Calender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the graphical representation of the month view in the application
 */
public class MonthView extends StackPane implements ViewAble {
    @FXML private GridPane monthGrid;
    @FXML private ScrollPane scrollPane;
    private List<Label> monthdays;
    private List<Label> weeknb;
    private Calender calender;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    public MonthView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/month.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setupMonth();
        this.calender = new Calender();
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it to make it possible to just update the existing content in future
     */
    private void setupMonth() {
        monthdays = new ArrayList<>();
        weeknb = new ArrayList<>();
        for(int i = 1; i < 7; i++){
            for(int j = 0; j < 8; j++){
                AnchorPane a = new AnchorPane();
                Label l = new Label(" ");
                a.getChildren().add(l);
                l.setStyle("-fx-font-size: 16px");
                if(j == 0){
                    l.setTextFill(Color.valueOf("#e64e4e"));
                    weeknb.add(l);
                }
                else{
                    l.setTextFill(Color.valueOf("#FFFF"));
                    monthdays.add(l);
                }

                monthGrid.add(a, j, i);
            }
        }
    }

    /**
     * Is implemented by the view interface
     * Updates the content in this view given a DateTime
     * @param currentDay
     */
    @Override
    public void updateView(LocalDateTime currentDay) {
        List<LocalDateTime> ldtList = calender.getLdtMonthFromDate(currentDay);

        LocalDateTime firstday = ldtList.get(0);
        int firstDate = firstday.getDayOfWeek().getValue();

        List<LocalDateTime> prevMonth = calender.getLdtMonthFromDate(firstday.minusMonths(1));
        int lastDayPrevMonth  = prevMonth.get(prevMonth.size()-1).getDayOfMonth();

        drawPreviousMonth(lastDayPrevMonth, firstDate);
        drawCurrentMonth(ldtList, firstDate);
        drawNextMonth(ldtList,firstDate);
        drawWeekNbrs(ldtList);

    }

    /**
     * Updates TimeLines position that is visible on screen every 60th second, is only being called if current view is either weekView or expandedDayView
     * @param hour
     * @param minute
     */
    @Override
    public void updateTimeLine(int hour, int minute) {
        //nothing to see here, just a dumb function inherited from the ViewAble interface
    }

    /**
     * Fills the month views first days with days from the previous month
     * @param lastDayPrevMonth
     * @param firstDate
     */
    private void drawPreviousMonth(int lastDayPrevMonth, int firstDate){
        for(int l = firstDate; 0 <= l; l--){
            monthdays.get(l).setText(String.valueOf(lastDayPrevMonth+2));
            monthdays.get(l).setStyle("-fx-opacity: .5");
            lastDayPrevMonth--;
        }
    }

    /**
     * Fills the month view with days from the given month
     * @param ldtList
     * @param firstDate
     */
    private void drawCurrentMonth(List<LocalDateTime> ldtList, int firstDate){
        int j = 0;
        for(int i = firstDate-1; i< monthdays.size(); i++){
            if(j < ldtList.size()){
                LocalDateTime tmpDay = ldtList.get(j);
                Integer daynb = tmpDay.getDayOfMonth();
                monthdays.get(i).setText(daynb.toString());
                monthdays.get(i).setStyle("-fx-opacity: 1");
                j++;
            }else{
                monthdays.get(i).setText(" ");
            }
        }
    }

    /**
     * Fills the month views last days with days from the next month
     * @param ldtList
     * @param firstDate
     */
    private void drawNextMonth(List<LocalDateTime> ldtList, int firstDate){
        LocalDateTime firstday = ldtList.get(0);
        List<LocalDateTime> nextMonth = calender.getLdtMonthFromDate(firstday.plusMonths(1));
        int firstDayNextMonth = 0;
        for(int l = ldtList.size()+firstDate-1; l < monthdays.size(); l++){
            LocalDateTime tmpDay = nextMonth.get(firstDayNextMonth);
            Integer daynb = tmpDay.getDayOfMonth();
            monthdays.get(l).setText(daynb.toString());
            monthdays.get(l).setStyle("-fx-opacity: .5");
            firstDayNextMonth++;
        }
    }

    /**
     *  Fills the month view with week numbers
     * @param ldtList
     */
    private void drawWeekNbrs(List<LocalDateTime> ldtList) {
        int w = calender.getWeekFromLdt(ldtList.get(0));
        for (Label l : weeknb) {
            l.setText("" + w++);
        }
    }
}

