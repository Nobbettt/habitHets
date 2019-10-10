package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import main.model.Habit;
import main.model.HabitHandler;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitObjectView extends AnchorPane {

    private HabitHandler handler = HabitHandler.getInstant();
    private Habit habit;

    @FXML private Label streakLabel;
    @FXML private CheckBox checkbox;
    @FXML private Label bestStreakLabel;
    @FXML private Circle color;

    public HabitObjectView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/habitElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    private void checkHabit(){
        habit.onClickHabit();
        if(habit.isCheckedToday()) {
            checkbox.setSelected(true);
        } else {
            checkbox.setSelected(false);
        }
    }



    public void updateElementView(Habit habit) {
        checkbox.setText(habit.getTitle());
        Integer streak = habit.getStreak();
        Integer bestStreak = habit.getBestStreak();
        streakLabel.setText(streak.toString());
        bestStreakLabel.setText(bestStreak.toString());
        checkbox.setSelected(habit.isCheckedToday());
        this.habit = habit;
        color.setStyle("-fx-stroke: " + habit.getColor()+"; -fx-fill: "+ habit.getColor());

    }


}
