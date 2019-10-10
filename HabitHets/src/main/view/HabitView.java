package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.model.Habit;
import main.model.HabitHandler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitView extends AnchorPane {

    private HabitHandler handler = HabitHandler.getInstant();

    @FXML private VBox vBox;
    @FXML private ScrollPane scrollPane;
    private List<HabitObjectView> habitsList;


    public HabitView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/habit.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        habitsList = new ArrayList<>();
    }


    @FXML
    private void addHabitOnClick(){
        handler.add();
        List<Habit> newHabit = new ArrayList<>();
        newHabit.add(handler.getHabitList().get(handler.getHabitList().size()-1));
        updateHabitView(newHabit);

    }


    List<CheckBox> checkboxes = new ArrayList<>();

    public void updateHabitView(List<Habit> habits) {
        for (Habit habit: habits){
            HabitObjectView habitObjectView = new HabitObjectView();
            vBox.getChildren().add(habitObjectView);
            habitObjectView.updateElementView(habit);
            habitsList.add(habitObjectView);
        }
    }


}
