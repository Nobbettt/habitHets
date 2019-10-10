package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.model.Todo;
import main.model.TodoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoElementView extends AnchorPane {

    Todo todo;
    TodoHandler todoHandler = TodoHandler.getInstant();
    TodoView todoView;



    public TodoElementView(Todo todo, TodoView todoView) {
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
        this.todoView = todoView;
        setUpTodo();

    }

    List<CheckBox> cb = new ArrayList<>();

    public CheckBox getCb() {
        return cb.get(0);
    }

    private void setUpTodo(){
        CheckBox c = (CheckBox)getChildren().get(0);
        c.setText(todo.getTitle());
        c.setStyle("-fx-font-size: 20");
        cb.add(c);
    }

    List<CheckBox> checkBoxes= new ArrayList<>();



    @FXML
    private void remove(){
        for (CheckBox c : cb){
            if (c.isSelected()){
                todoHandler.getDoneTodoList().add(todo);
                todoHandler.getTodoList().remove(todo);
                System.out.println("j");
            }
        }
        todoView.updateTodoView(TodoHandler.getInstant().getTodoList());
    }

}
