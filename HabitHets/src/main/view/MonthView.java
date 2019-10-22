package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import main.model.Facade;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonthView extends StackPane implements ViewAble {
    @FXML private GridPane monthGrid;
    @FXML private ScrollPane scrollPane;
    private List<Label> monthdays;
    private List<Label> weeknb;

    public MonthView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/month.fxml"));
              fxmlLoader.setRoot(this);
              fxmlLoader.setController(this);
              try {
                fxmlLoader.load();
              } catch (IOException exception) {
                  throw new RuntimeException(exception);
               }
              setupMonth();
    }

     private void setupMonth() {
         monthdays = new ArrayList<>();
         weeknb = new ArrayList<>();
         for(int i = 1; i < 7; i++){
             for(int j = 0; j < 8; j++){
                 AnchorPane a = new AnchorPane();
                 Label l = new Label(" ");
                 a.getChildren().add(l);

                 if(j == 0){
                     l.setTextFill(Color.valueOf("#FF4500"));
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

    @Override
    public void updateView(List<LocalDateTime> month) {
        updateMonthView(month);

    }

    @Override
    public void updateTimeLine(int hour, int minute) {

    }

    private void updateMonthView(List<LocalDateTime> ldtList) {

        LocalDateTime firstday = ldtList.get(0);
        int k = firstday.getDayOfWeek().getValue();


        Facade facade = new Facade();

        List<LocalDateTime> prevMonth = facade.getLdtMonthFromDate(firstday.minusMonths(1));
        int lastDayPrevMonth  = prevMonth.get(prevMonth.size()-1).getDayOfMonth();

        for(int l = k; 0 <= l; l--){
            monthdays.get(l).setText(String.valueOf(lastDayPrevMonth+2));
            monthdays.get(l).setStyle("-fx-opacity: .5");
            lastDayPrevMonth--;
        }

        int j = 0;
        for(int i = k-1; i< monthdays.size(); i++){
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

        List<LocalDateTime> nextMonth = facade.getLdtMonthFromDate(firstday.plusMonths(1));
        int firstDayNextMonth = 0;
        for(int l = ldtList.size()+k-1; l < monthdays.size(); l++){
            LocalDateTime tmpDay = nextMonth.get(firstDayNextMonth);
            Integer daynb = tmpDay.getDayOfMonth();
            monthdays.get(l).setText(daynb.toString());
            monthdays.get(l).setStyle("-fx-opacity: .5");
            firstDayNextMonth++;
        }

        int w = facade.getWeekFromLdt(ldtList.get(0));
        for(Label l : weeknb){
            l.setText("" + w++);
        }

    }
}

