package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    @FXML private ColorPicker colorPicker ;
    @FXML private Button saveFromAdd;
    @FXML private Button saveFromEdit;
    private List<HabitObjectView> habitsList;
    private HabitObjectView editing;

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


    private void setVisibilityAdd(Boolean visibility){
        saveFromAdd.setVisible(visibility);
    }

    private void setVisibilityEdit(Boolean visibility){
        saveFromEdit.setVisible(visibility);
    }


    public void edit(HabitObjectView habitObjectView){
        newHabit.setVisible(true);
        newHabit.toFront();
        setVisibilityAdd(false);
        saveFromAdd.toBack();
        setVisibilityEdit(true);
        saveFromEdit.toFront();
        title.setText(habitObjectView.getHabit().getTitle());
        //TODO set color
        editing = habitObjectView;
    }

    @FXML
    private void addNewHabit(){
        newHabit.setVisible(true);
        newHabit.toFront();
        setVisibilityEdit(false);
        saveFromEdit.toBack();
        setVisibilityAdd(true);
        saveFromAdd.toFront();
    }

    @FXML
    private void saveHabitOnClick(){
       // saveFromAdd.setVisible(true);
        //saveFromAdd.toFront();
        //saveFromEdit.toBack();
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            handler.addHabit(title.getText(), ColorToString(colorPicker.getValue()));
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
    private void saveFrEdit(){
       // saveFromAdd.setVisible(false);
       // saveFromEdit.setVisible(true);
        //saveFromEdit.toFront();
        //saveFromAdd.toBack();
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            editing.getHabit().setTitle(title.getText());
            // todo color
            editing.updateElementView(editing.getHabit());
            title.clear();
        }
        else {
            title.clear();
        }
    }

    private String ColorToString(Color color){
        return String.format("#%02X%02X%02X",
                ((int)color.getRed())*255,
                ((int)color.getGreen())*255,
                ((int)color.getBlue())*255);
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
            HabitObjectView habitObjectView = new HabitObjectView(this);
            vBox.getChildren().add(habitObjectView);
            habitObjectView.updateElementView(habit);
            habitsList.add(habitObjectView);
         //   hide();
        }
    }


}
