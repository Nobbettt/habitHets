package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the graphical representation of the single habit that can be found in the habit view
 */
class HabitObjectView extends AnchorPane {
    private int id;
    private Facade facade;
    private List<ViewListener> listeners;
    @FXML private Label streakLabel;
    @FXML private Label bestStreakLabel;
    @FXML private Button color;
    @FXML private Label title;
    @FXML private GridPane habitGrid;
    @FXML private Button edit;
    @FXML private Button deleteHabit;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     * Sets local id to the received id
     */
    public HabitObjectView(int id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/habitElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.id = id;
        listeners = new ArrayList<>();
        this.facade = new Facade();
    }

    /**
     * Returns habits id
     * @return
     */
    int getHabitID() {
        return id;
    }

    /**
     * Click function on checking/ toggling a habit element
     */
    @FXML
    private void checkHabit(){
        facade.habitClicked(id);
        updateStreaks();
        setColor();
    }

    /**
     * Click function for deleting a habit
     */
    @FXML
    private void deleteHabit(){
        facade.removeHabit(id);
        notifyListener(Integer.toString(id));
    }

    /**
     * Click function for opening editing mode for a habit
     */
    @FXML
    private void editHabit(){
        notifyListener(Integer.toString(id));
    }

    /**
     * Updates a habits attributes and sets objects id to habits id
     * @param id
     */
    void updateElementView(int id) {
        this.id = id;
        title.setText(facade.getHabitTitle(id));
        updateStreaks();
        setColor();
    }

    /**
     * Function updates habits streaks on toggle
     */
    private void updateStreaks() {
        Integer streak = facade.getStreak(id);
        Integer bestStreak = facade.getBestStreak(id);
        streakLabel.setText(streak.toString());
        bestStreakLabel.setText(bestStreak.toString());
    }

    /**
     * Sets habits color and fill property depending on toggle status
     */
    private void setColor() {
        if (facade.habitIsCheckedToday(id)){
            color.setStyle("-fx-background-color: " + facade.getHabitColor(id) + "; -fx-border-color: "+ facade.getHabitColor(id));
        }else{
            color.setStyle("-fx-background-color: #999; -fx-border-color: "+ facade.getHabitColor(id));
        }
    }

    /**
     * Makes every part of tha habit object invisible except toggle button
     */
    void hideHabits(){
        habitGrid.setVisible(false);
        title.setVisible(false);
        this.setPrefHeight(40);
        habitGrid.setPrefHeight(0);
        deleteHabit.setVisible(false);
    }

    /**
     * Makes the whole habit element visible
     */
    void showHabits(){
        habitGrid.setVisible(true);
        title.setVisible(true);
        this.setPrefHeight(100);
        habitGrid.setPrefHeight(USE_COMPUTED_SIZE);
        deleteHabit.setVisible(true);
    }

    /**
     * Adds listener to listeners list given a ViewListener
     * @param l
     */
    void addListener(ViewListener l){
        listeners.add(l);
    }

    /**
     * Notifies all listeners that are in the listeners lst
     * @param msg
     */
    private void notifyListener(String msg){
        for (ViewListener l : listeners)
            l.actOnUpdate(msg);
    }
}
