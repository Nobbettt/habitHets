package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.model.CalendarAble;
import main.model.Day;

import java.awt.*;
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
                 Label l = new Label("test");
                 a.getChildren().add(l);

                 if(j == 0 && i == 1){
                     l.setText("Week");
                 }
                 if(j == 1 && i == 1){
                     l.setText("Mon");
                 }
                 if(j == 2 && i == 1){
                     l.setText("Tue");
                 }
                 if(j == 3 && i == 1){
                     l.setText("Wed");
                 }
                 if(j == 4 && i == 1){
                     l.setText("Thu");
                 }
                 if(j == 5 && i == 1){
                     l.setText("Fri");
                 }
                 if(j == 6 && i == 1){
                     l.setText("Sat");
                 }
                 if(j == 7 && i == 1){
                     l.setText("Sun");
                 }

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
        int j = 0;
        int w = month.get(0).getWeekNr()-4;
        for (Label l : monthdays){
            l.setText("" + month.get(0).getLdt().plusDays(j).getDayOfMonth() + "/" + month.get(0).getLdt().plusDays(j).getMonthValue());
            if (j < month.size()-1)
            j++;
        }
        for(Label l : weeknb){
            l.setText("" + w++);
        }
    }
    }

