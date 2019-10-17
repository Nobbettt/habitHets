package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import main.model.Todo;
import main.model.TodoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoElementView extends AnchorPane  {

    Todo todo;
    TodoHandler todoHandler = TodoHandler.getInstant();

    public TodoElementView(Todo todo) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/todoElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
         this.todo = todo;
        setUpTodo();

    }

    List<CheckBox> cb = new ArrayList<>();

    public CheckBox getCb() {
        return cb.get(0);
    }

    private void setUpTodo(){
        CheckBox c = (CheckBox)getChildren().get(0);
        c.setText(todo.getTitle());
        c.setStyle("-fx-font-size: 15");
        cb.add(c);
    }

    List<CheckBox> checkBoxes= new ArrayList<>();

    @FXML
    private void remove(){
        for (CheckBox c : cb){
            if (c.isSelected()){
                todoHandler.doneTodoRemove(todo.getId());
            }
        }
    }

    @FXML
    private void deleteTodo(){
        todoHandler.remove(todo.getId());
    }




}