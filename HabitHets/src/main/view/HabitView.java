package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
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
    @FXML private Button addHabit;
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
        habitsList = new ArrayList<>();
        isExpanded = true;
    }



    public boolean getIsExpanded() {
        return isExpanded;
    }
    public void setIsExpanded(boolean state) { isExpanded = state;}


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
        java.awt.Color tmpC = java.awt.Color.decode(habitObjectView.getHabit().getColor());
        Color c = Color.rgb(tmpC.getRed(),tmpC.getGreen(),tmpC.getBlue());
        colorPicker.setValue(c);
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
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            handler.addHabit(title.getText(), colorToString(colorPicker.getValue()));
            List<Habit> newHabit = new ArrayList<>();
            newHabit.add(handler.getHabitList().get(handler.getHabitList().size()-1));
            updateHabitView(newHabit);
        }
        title.clear();
        colorPicker.setValue(Color.WHITE);
    }

    @FXML
    private void saveFrEdit(){
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            editing.getHabit().setTitle(title.getText());
            editing.getHabit().setColor(colorToString(colorPicker.getValue()));
            editing.updateElementView(editing.getHabit());
        }
        title.clear();
        colorPicker.setValue(Color.WHITE);
    }

    private String colorToString(Color color){
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
        addHabit.setVisible(false);
        isExpanded = false;
    }

    public void visiable(){
        for(HabitObjectView h : habitsList){
            h.showHabits();
        }
        addHabit.setVisible(true);
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
