package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.model.Habit;
import main.model.HabitHandler;


import java.io.IOException;


public class HabitObjectView extends AnchorPane {

    private Habit habit;

    @FXML private Label streakLabel;
    @FXML private Label bestStreakLabel;
    @FXML private Button color;
    @FXML private Label title;
    @FXML private GridPane habitGrid;
    @FXML private Button edit;

    private HabitView habitView;


    public HabitObjectView(HabitView habitView) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/habitElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.habitView = habitView;
    }


    public Habit getHabit() {
        return habit;
    }


    @FXML
    private void checkHabit(){
        habit.onClickHabit();
        setColor();
    }

    @FXML
    private void editHabit(){
        habitView.edit(this);
    }


    public void updateElementView(Habit habit) {
        this.habit = habit;
        title.setText(habit.getTitle());
        Integer streak = habit.getStreak();
        Integer bestStreak = habit.getBestStreak();
        streakLabel.setText(streak.toString());
        bestStreakLabel.setText(bestStreak.toString());

        setColor();
    }


    private void setColor() {
        if (habit.isCheckedToday()){
            color.setStyle("-fx-background-color: " + habit.getColor() + "; -fx-border-color: "+habit.getColor());
        }else{
            color.setStyle("-fx-background-color: #fff; -fx-border-color: "+habit.getColor());
        }
    }


    public void hideHabits(){
        habitGrid.setVisible(false);
        title.setVisible(false);
        this.setPrefHeight(40);
        habitGrid.setPrefHeight(0);
    }

    public void showHabits(){
        habitGrid.setVisible(true);
        title.setVisible(true);
        this.setPrefHeight(100);
        habitGrid.setPrefHeight(USE_COMPUTED_SIZE);
    }


}
