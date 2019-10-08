package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.model.Habit;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitView extends AnchorPane {

    @FXML private VBox vBox;

    public HabitView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/habit.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    List<CheckBox> checkboxes = new ArrayList<>();

    public void updateHabitView(List<Habit> habits) {
        for (Habit habit: habits){
            CheckBox c  =new CheckBox(habit.getTitle());
            c.setStyle("-fx-font-size: 20");
            AnchorPane a = new AnchorPane();
            a.getChildren().add(c);
            vBox.getChildren().add(a);
            checkboxes.add(c);
        }
    }


}
