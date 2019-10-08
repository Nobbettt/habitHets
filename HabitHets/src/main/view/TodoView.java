package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.model.TodoHandler;
import main.model.Todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoView extends AnchorPane {

    @FXML private AnchorPane todoList;

    @FXML private VBox vboxtodo;
    TodoHandler todoHandler = TodoHandler.getInstant();





    public TodoView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/todo.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }




}

    @FXML

    private void addTodoOnClick(){
        todoHandler.add();
        List<Todo> newTodo = new ArrayList<>();
        newTodo.add(todoHandler.getTodoList().get(todoHandler.getTodoList().size()-1));
        updateTodoView(newTodo);

    }

    int a =0;
        public void updateTodoView(List<Todo> todos ){
                for (Todo todo: todos){
                        CheckBox c = new CheckBox(todo.getTitle());
                        AnchorPane a = new AnchorPane();
                        a.getChildren().add(c);
                        a.setCursor(Cursor.CLOSED_HAND);
                        vboxtodo.getChildren().add(a);

                    }
                }






}
