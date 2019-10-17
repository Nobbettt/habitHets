package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import main.model.Aggregate;
import main.model.CalendarAble;
import main.model.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonthView extends StackPane implements ViewAble {
    @FXML private GridPane monthGrid;
    @FXML private ScrollPane scrollPane;
    private List<Label> monthdays;
    private List<Label> weeknb;
    private List<Label> weekday;
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

     public void setupMonth() {
         monthdays = new ArrayList<>();
         weeknb = new ArrayList<>();
         weekday = new ArrayList<>();
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
    public void updateView(List<? extends CalendarAble> month) {
        updateMonthView((List<Day>) month);
    }

    @Override
    public void updateTimeLine(int hour, int minute) {

    }

    private void updateMonthView(List<Day> month) {
        Day firstday = (Day) month.get(0);
        int k = firstday.getLdt().getDayOfWeek().getValue();


        Aggregate a = new Aggregate();

        List<Day> prevMonth = a.getMonthFromDate(firstday.getLdt().minusMonths(1));
        int lastDayPrevMonth = prevMonth.size()-1;
        for(int l = k; 0 <= l; l--){
            Day tmpDay = (Day) prevMonth.get(lastDayPrevMonth);
            Integer daynb = tmpDay.getLdt().getDayOfMonth();
            monthdays.get(l).setText(daynb.toString());
            lastDayPrevMonth--;
        }

        int j = 0;
        for(int i = k; i< monthdays.size(); i++){
            if(j < month.size()){
                Day tmpDay = (Day) month.get(j);
                Integer daynb = tmpDay.getLdt().getDayOfMonth();
                monthdays.get(i).setText(daynb.toString());
                j++;
            }else{
                monthdays.get(i).setText(" ");
            }

        }

        List<Day> nextMonth = a.getMonthFromDate(firstday.getLdt().plusMonths(1));
        int firstDayNextMonth = 0;
        for(int l = month.size()+k; l < monthdays.size(); l++){
            Day tmpDay = (Day) nextMonth.get(firstDayNextMonth);
            Integer daynb = tmpDay.getLdt().getDayOfMonth();
            monthdays.get(l).setText(daynb.toString());
            firstDayNextMonth++;
        }

        int w = month.get(0).getWeekNr()-4;
        for(Label l : weeknb){
            l.setText("" + w++);
        }

    }
    }

