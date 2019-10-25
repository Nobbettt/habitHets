package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the graphical representation of the habit view in the application
 */
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
    @FXML private Label habitModifyTypeLabel;
    @FXML private Label idLbl;
    private List<HabitObjectView> habitsList;
    private HabitObjectView editing;
    private Facade facade;
    private boolean isExpanded;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     * Fits content to window
     */
    public HabitView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/habit.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpHabit();
        this.facade = new Facade();
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it
     */
    private void setUpHabit() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        habitsList = new ArrayList<>();
    }

    /**
     * returns if the habit view is expanded or not
     * @return
     */
    public boolean getIsExpanded() {
        return isExpanded;
    }

    /**
     * Sets the local is expanded boolean variable to either false or true
     * @param state
     */
    public void setIsExpanded(boolean state) { isExpanded = state;}

    /**
     * Sets visibility of create new habit popup
     * @param visibility
     */
    private void setVisibilityAdd(Boolean visibility){
        create.setVisible(visibility);
    }

    /**
     * sets visibility of edit habit pane
     * @param visibility
     */
    private void setVisibilityEdit(Boolean visibility){
        save.setVisible(visibility);
    }

    /**
     *  Edits a habit object given a id
     * @param id
     */
    private void edit(String id){
        if(facade.habitExist(id)) {
            for (HabitObjectView habitObjectView : habitsList) {
                if (habitObjectView.getHabitID() == Integer.valueOf(id)) {
                    editing = habitObjectView;
                }
            }
            newHabit.setVisible(true);
            newHabit.toFront();
            setVisibilityAdd(false);
            create.toBack();
            setVisibilityEdit(true);
            save.toFront();
            title.setText(facade.getHabitTitle(Integer.valueOf(id)));
            idLbl.setText(id);
            Color c = Color.valueOf(facade.getHabitColor(Integer.valueOf(id)));
            colorPicker.setValue(c);
            habitModifyTypeLabel.setText("Edit Habit");
        }
    }

    /**
     * Pops up add new habit popup on click on "+" add button
     */
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

    /**
     * Creates a new Habit from inputs on button click on "create" button
     */
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

    /**
     * Saves edited changes to a habit object on click on "edit" btn
     */
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

    /**
     * Closes habit pop up on click on "close" button
     */
    @FXML
    private void closeNewHabitWindow(){
        newHabit.setVisible(false);
        newHabit.toBack();
    }

    /**
     * Hides all habit elements on click on toggle button (collapse habit view)
     */
    public void hide(){
        for(HabitObjectView h : habitsList){
            h.hideHabits();
        }
        addHabit.setVisible(false);
        generalTitle.setText("H");
        generalTitle.setStyle("-fx-padding: 0 0 0 0");
        isExpanded = false;
    }

    /**
     * Shows all habit elements on click on toggle button (expand habit view)
     */
    public void visible(){
        for(HabitObjectView h : habitsList){
            h.showHabits();
        }
        addHabit.setVisible(true);
        generalTitle.setText("Habit");
        generalTitle.setStyle("-fx-padding: 0 30 0 0");
        isExpanded = true;
    }

    /**
     * Updates habit views containing habits on call
     * @param ids
     */
     public void updateHabitView(List<Integer> ids) {
        vBox.getChildren().clear();
        for (Integer id: ids){
            HabitObjectView habitObjectView = new HabitObjectView(id);
            habitObjectView.addListener(this);
            vBox.getChildren().add(habitObjectView);
            habitObjectView.updateElementView(id);
            habitsList.add(habitObjectView);
        }
    }

    /**
     * Helper method to toHexString to correctly format values
     * @param val
     * @return
     */
    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    /**
     * Converts java Color object to hex color in String format
     * @param value
     * @return
     */
    private String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }

    /**
     * Updates view with help of listener listening to changes
     * @param msg
     */
    @Override
    public void actOnUpdate(String msg) {
        edit(msg);
    }
}
