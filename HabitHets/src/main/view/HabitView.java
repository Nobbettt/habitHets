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

public class HabitView extends AnchorPane implements ViewListener {

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
    @FXML private Label idLbl;
    private List<HabitObjectView> habitsList;
    private HabitObjectView editing;
    private Facade facade;

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


    public void edit(String msg){
        if(facade.habitExist(msg)) {
            for (HabitObjectView habitObjectView : habitsList) {
                if (habitObjectView.getHabitID() == Integer.valueOf(msg)) {
                    editing = habitObjectView;
                }
            }
            newHabit.setVisible(true);
            newHabit.toFront();
            setVisibilityAdd(false);
            create.toBack();
            setVisibilityEdit(true);
            save.toFront();
            title.setText(facade.getHabitTitle(Integer.valueOf(msg)));
            idLbl.setText(msg);
            //java.awt.Color tmpC = java.awt.Color.decode(facade.getHabitColor(editing.getHabitID()));
            Color c = Color.valueOf(facade.getHabitColor(Integer.valueOf(msg)));
            colorPicker.setValue(c);
            habitModifyTypeLabel.setText("Edit Habit");
        }

    }

    @FXML
    private void addNewHabit(){
        title.clear();
        colorPicker.setValue(Color.FIREBRICK);
        habitModifyTypeLabel.setText("Add Habit");
        setVisibilityEdit(false);
        setVisibilityAdd(true);
        newHabit.setVisible(true);
        newHabit.toFront();
        save.toBack();
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
            facade.updateHabitTitle(Integer.valueOf(idLbl.getText()),title.getText());
            facade.updateHabitColor(Integer.valueOf(idLbl.getText()), toHexString(colorPicker.getValue()));
            editing.updateElementView(Integer.valueOf(idLbl.getText()));
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


    //List<CheckBox> checkboxes = new ArrayList<>();

    public void updateHabitView(List<Integer> ids) {
        vBox.getChildren().clear();
        for (Integer id: ids){
            HabitObjectView habitObjectView = new HabitObjectView(id);
            habitObjectView.addListener(this);
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


    @Override
    public void actOnUpdate(String msg) {
        edit(msg);
        System.out.println("test");
    }
}
