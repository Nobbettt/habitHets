package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.CalendarAble;
import model.Day;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonthInYear extends AnchorPane  {

    @FXML private GridPane gridPane;
    @FXML private Label monthLabel;
    List<Label> weekdays = new ArrayList<>();
    List<Label> days = new ArrayList<>();

    public MonthInYear() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/monthInYear.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        setup();
    }



    public void updateView(List<? extends CalendarAble> months, String monthName) {
        Day firstDay = (Day)months.get(0);
        int k = firstDay.getLdt().getDayOfWeek().getValue()-1;
        int dc = 0;
        monthLabel.setText(monthName);
        for (int e=0; e<k; e++){
            days.get(e).setText(" ");
        }
        for (int i = k; i < days.size(); i++){
            if (dc< months.size()){
                Day tmpDay = (Day)months.get(dc);
                Integer dayNb = tmpDay.getLdt().getDayOfMonth();
                days.get(i).setText(dayNb.toString());
                dc++;
            }


        }


    }



    private void setup(){
        for (int i = 1; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                AnchorPane a = new AnchorPane();
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setTextFill(Color.valueOf("#FFFF"));
                days.add(label);
                a.getChildren().add(label);
                gridPane.add(a,j,i);
                fitItem(a, label);
            }
        }

    }

    private void fitItem(AnchorPane parent, Node child) {

            setTopAnchor(child, 0.0);

            setRightAnchor(child, 0.0);

            setBottomAnchor(child, 0.0);

            setLeftAnchor(child, 0.0);
        }

}
