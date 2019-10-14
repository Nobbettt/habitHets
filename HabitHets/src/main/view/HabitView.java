package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    @FXML private AnchorPane newHabit;
    @FXML private TextField title;
    private String color = "blue";
    private List<HabitObjectView> habitsList;

    private boolean isExpanded;


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
        isExpanded = false;
        hide();
    }

    public boolean getIsExpanded() {
        return isExpanded;
    }


    @FXML
    private void addNewHabit(){
        newHabit.setVisible(true);
        newHabit.toFront();
    }

    @FXML
    private void saveHabitOnClick(){
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            handler.addHabit(title.getText(), color);
            title.clear();
            List<Habit> newHabit = new ArrayList<>();
            newHabit.add(handler.getHabitList().get(handler.getHabitList().size()-1));
            updateHabitView(newHabit);
        }
        else {
            title.clear();
        }


    }

    @FXML
    private void closeNewHabitWindow(){
        newHabit.setVisible(false);
        newHabit.toBack();
    }


    public void hide(){
        for(HabitObjectView h : habitsList){
            h.hideHabits();
        }
        isExpanded = false;
    }

    public void visiable(){
        for(HabitObjectView h : habitsList){
            h.showHabits();
        }
        isExpanded = true;
    }


    //List<CheckBox> checkboxes = new ArrayList<>();

    public void updateHabitView(List<Habit> habits) {
        for (Habit habit: habits){
            HabitObjectView habitObjectView = new HabitObjectView();
            vBox.getChildren().add(habitObjectView);
            habitObjectView.updateElementView(habit);
            habitsList.add(habitObjectView);
         //   hide();
        }
    }


}
