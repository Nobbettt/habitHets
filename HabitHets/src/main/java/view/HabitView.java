package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Habit;
import model.HabitOrganizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabitView extends AnchorPane implements ViewListener {

    private HabitOrganizer handler = HabitOrganizer.getInstant();
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
    private Habit habit;
    private boolean isExpanded;
    private HabitObjectView hov;


    public HabitView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/habit.fxml"));
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
        create.setVisible(visibility);
    }

    private void setVisibilityEdit(Boolean visibility){
        save.setVisible(visibility);
    }


    public void edit(String msg){
        habit = handler.getHabitById(msg);
        newHabit.setVisible(true);
        newHabit.toFront();
        setVisibilityAdd(false);
        create.toBack();
        setVisibilityEdit(true);
        save.toFront();
        title.setText(habit.getTitle());
        java.awt.Color tmpC = java.awt.Color.decode(habit.getColor());
        Color c = Color.rgb(tmpC.getRed(),tmpC.getGreen(),tmpC.getBlue());
        colorPicker.setValue(c);
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
            handler.addHabit(title.getText(), colorToString(colorPicker.getValue()));
            List<Habit> newHabit = new ArrayList<>();
            newHabit.add(handler.getHabitList().get(handler.getHabitList().size()-1));
            updateHabitView();
        }
        title.clear();
        colorPicker.setValue(Color.WHITE);
    }

    @FXML
    private void save(){
        closeNewHabitWindow();
        if(title.getText() != null && !title.getText().isEmpty()){
            habit.setTitle(title.getText());
            habit.setColor(colorToString(colorPicker.getValue()));
            for (HabitObjectView h : habitsList) {
                if(h.getHabit().getId() == habit.getId()) {
                    h.updateElementView();
                }
            }
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


    //List<CheckBox> checkboxes = new ArrayList<>();

    public void updateHabitView() {
        vBox.getChildren().clear();
        for (Habit habit: handler.getHabitList()){
            HabitObjectView habitObjectView = new HabitObjectView(habit);
            habitObjectView.addListener(this);
            vBox.getChildren().add(habitObjectView);
            habitObjectView.updateElementView();
            habitsList.add(habitObjectView);
         //   hide();
        }
    }


    @Override
    public void actOnUpdate(String msg) {
        edit(msg);
        System.out.println("test");
    }
}
