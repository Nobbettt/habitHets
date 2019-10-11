package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.Aggregate;
import main.model.CalendarAble;
import main.model.Day;
import main.model.Month;

import java.io.IOException;
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
        Aggregate aggregate = new Aggregate();
        Month m = aggregate.getMonth(month.get(1).getLdt());
        int j = 0;
        int w = m.getFirstWeek()-4;
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

