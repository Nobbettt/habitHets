package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.Facade;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonthViewMini extends StackPane implements ViewAble {
    @FXML private GridPane monthGrid;
    @FXML private ScrollPane scrollPane;
    private List<Label> monthdays;
    private List<Label> weeknb;

    public MonthViewMini(){
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
    public void updateView(List<LocalDateTime> month) {
        updateMonthView(month);
    }

    @Override
    public void updateTimeLine(int hour, int minute) {

    }

    private void updateMonthView(List<LocalDateTime> month) {
        Facade f = new Facade();
        int j = 0;
        int w = f.getWeekFromLdt(month.get(0));
        for (Label l : monthdays){
            l.setText("" + month.get(0).plusDays(j).getDayOfMonth() + "/" + month.get(0).plusDays(j).getMonthValue());
            if (j < month.size()-1)
                j++;
        }
        for(Label l : weeknb){
            l.setText("" + w++);
        }
    }
}
