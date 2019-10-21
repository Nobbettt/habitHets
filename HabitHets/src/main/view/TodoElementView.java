package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import main.model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoElementView extends AnchorPane  {

    Facade facade;
    public int id;

    TodoElementView(int id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/todoElement.fxml"));
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

    List<CheckBox> cb = new ArrayList<>();

    CheckBox getCb() {
        return cb.get(0);
    }

    private void setUpTodo(){
        CheckBox c = (CheckBox)getChildren().get(0);
        c.setText(facade.getTodoTitle(id));
        c.setStyle("-fx-font-size: 15");
        cb.add(c);
    }

    List<CheckBox> checkBoxes= new ArrayList<>();

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