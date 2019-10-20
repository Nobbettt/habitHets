package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.model.Facade;


import java.io.IOException;


public class HabitObjectView extends AnchorPane {

    private int id;
    Facade facade;

    @FXML private Label streakLabel;
    @FXML private Label bestStreakLabel;
    @FXML private Button color;
    @FXML private Label title;
    @FXML private GridPane habitGrid;
    @FXML private Button edit;
    @FXML private Button deleteHabit;
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
        this.facade = new Facade();
    }


    public int getHabitID() {
        return id;
    }


    @FXML
    private void checkHabit(){
        facade.habitClicked(id);
        updateStreaks();
        setColor();
    }

    @FXML
    private void deleteHabit(){
        facade.removeHabit(id);
        habitView.updateHabitView(facade.getAllHabitIds());
    }

    @FXML
    private void editHabit(){
        habitView.edit(this);
    }

    public void updateElementView(int id) {
        this.id = id;
        title.setText(facade.getHabitTitle(id));
        updateStreaks();
        setColor();
    }

    private void updateStreaks() {
        Integer streak = facade.getStreak(id);
        Integer bestStreak = facade.getBestStreak(id);
        streakLabel.setText(streak.toString());
        bestStreakLabel.setText(bestStreak.toString());
    }

    private void setColor() {
        if (facade.habitIsCheckedToday(id)){
            color.setStyle("-fx-background-color: " + facade.getHabitColor(id) + "; -fx-border-color: "+ facade.getHabitColor(id));
        }else{
            color.setStyle("-fx-background-color: #fff; -fx-border-color: "+ facade.getHabitColor(id));
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
