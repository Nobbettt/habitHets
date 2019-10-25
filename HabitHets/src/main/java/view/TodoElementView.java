package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoElementView extends AnchorPane  {
    private Facade facade;
    private int id;
    List<CheckBox> checkBoxes= new ArrayList<>();

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    TodoElementView(int id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/todoElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        this.id = id;
        this.facade = new Facade();
        setUpTodo();

    }

    int getTodoID(){
        return id;
    }

    List<CheckBox> cb = new ArrayList<>();

    CheckBox getCb() {
        return cb.get(0);
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it
     */
    private void setUpTodo(){
        CheckBox c = (CheckBox)getChildren().get(0);
        c.setText(facade.getTodoTitle(id));
        c.setStyle("-fx-font-size: 15");
        cb.add(c);
    }

    @FXML
    private void remove(){
        for (CheckBox c : cb){
            if (c.isSelected()){
                facade.removeDoneTodo(id);
            }
        }
    }

    @FXML
    private void deleteTodo(){
        facade.removeTodo(id);
    }
}