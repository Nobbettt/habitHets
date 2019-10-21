package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitView extends AnchorPane {

    @FXML private VBox vBox;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane newHabit;
    @FXML private TextField title;
    @FXML private ColorPicker colorPicker ;
    @FXML private Button create;
    @FXML private Button save;
    @FXML private Button addHabit;
    @FXML private Label generalTitle;
    @FXML private AnchorPane titleBackground;
    @FXML private Label collapsedTitle;
    @FXML private Label habitModifyTypeLabel;
    private List<HabitObjectView> habitsList;
    private HabitObjectView editing;
    Facade facade;

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
        this.facade = new Facade();
    }



    public boolean getIsExpanded() {
        return isExpanded;
    }
    public void setIsExpanded(boolean state) { isExpanded = state;}


    private void setVisibilityAdd(Boolean visibility){
        create.setVisible(visibility);
    }

    private void setVisibilityEdit(Boolean visibility){
        save.setVisible(visibility);
    }


    void edit(HabitObjectView habitObjectView){
        newHabit.setVisible(true);
        newHabit.toFront();
        setVisibilityAdd(false);
        create.toBack();
        setVisibilityEdit(true);
        save.toFront();
        title.setText(facade.getHabitTitle(habitObjectView.getHabitID()));
        java.awt.Color tmpC = java.awt.Color.decode(facade.getHabitColor(habitObjectView.getHabitID()));
        Color c = Color.valueOf(facade.getHabitColor(habitObjectView.getHabitID()));
        colorPicker.setValue(c);
        editing = habitObjectView;
        habitModifyTypeLabel.setText("Edit Habit");

    }

    @FXML
    private void addNewHabit(){
        newHabit.setVisible(true);
        newHabit.toFront();
        setVisibilityEdit(false);
        save.toBack();
        setVisibilityAdd(true);
        create.toFront();
    }

    @FXML
    private void create(){
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            facade.createHabit(title.getText(), toHexString(colorPicker.getValue()));
            updateHabitView(facade.getAllHabitIds());
        }
        title.clear();
        colorPicker.setValue(Color.WHITE);
    }

    @FXML
    private void save(){
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            facade.updateHabitTitle(editing.getHabitID(),title.getText());
            facade.updateHabitColor(editing.getHabitID(), toHexString(colorPicker.getValue()));
            editing.updateElementView(editing.getHabitID());
        }
        title.clear();
        colorPicker.setValue(Color.WHITE);
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
        generalTitle.setVisible(false);
        titleBackground.setVisible(true);
        collapsedTitle.setVisible(true);
        isExpanded = false;
    }

    public void visiable(){
        for(HabitObjectView h : habitsList){
            h.showHabits();
        }
        addHabit.setVisible(true);
        generalTitle.setVisible(true);
        titleBackground.setVisible(false);
        collapsedTitle.setVisible(false);
        isExpanded = true;
    }

    public void updateHabitView(List<Integer> ids) {
        vBox.getChildren().clear();
        for (Integer id: ids){
            HabitObjectView habitObjectView = new HabitObjectView(this);
            vBox.getChildren().add(habitObjectView);
            habitObjectView.updateElementView(id);
            habitsList.add(habitObjectView);
         //   hide();
        }
    }

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    private String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }


}
