package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.CalendarAble;
import main.model.Day;
import main.model.Month;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

     public void setupMonth() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
         scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

         monthdays = new ArrayList<>();
         weeknb = new ArrayList<>();
         for(int i = 0; i < 5; i++){
             for(int j = 0; j < 8; j++){
                 AnchorPane a = new AnchorPane();
                 Label l = new Label("test");
                 a.getChildren().add(l);

                 if(j == 0){
                     weeknb.add(l);
                 }
                 else{
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
        int mth = 4;
        int yr = 2019;
        Month m = new Month(yr, mth);
        int i = 0;
        int d = m.getFirstWeek() - 4;

   /*     while (i < 5) {
            weeknb.get(i).setText("" + d);
            d++;
            i++;
        } */

        int j = 0;
        for (Label l : monthdays){
            l.setText("" + month.get(j).getLdt().plusDays(j).getDayOfMonth() + "/" + month.get(j).getLdt().plusDays(j).getMonthValue());
            if (j < month.size()-1)
            j++;
        }
        /*while (j < 30) {
                monthdays.get(j).setText(month.get(j).getString());

                count++;
                j++;
        }
        */
    }



/*
        for(int i = month.get(0).getLdt().getDayOfWeek().getValue(); i < 30; i++){
            monthdays.get(i - 1).setText(month.get(i).getString());
        }
        weeknb.get(0).setText(month.get(0).getWeekNr()+"");
*/

    }

