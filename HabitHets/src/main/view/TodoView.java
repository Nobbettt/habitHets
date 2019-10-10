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
    @FXML public VBox vboxdonetodo;
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
        updateTodoView(todoHandler.getTodoList());
    }

    List<CheckBox> checkBoxes = new ArrayList<>();


        public void updateTodoView(List<Todo> todos ){
            vboxtodo.getChildren().clear();
            vboxdonetodo.getChildren().clear();
                for (Todo todo: todos){
                    TodoElementView todoElement = new TodoElementView(todo, this);
                    vboxtodo.getChildren().add(todoElement);
                    }
                for (Todo todo : TodoHandler.getInstant().getDoneTodoList()){
                    TodoElementView todoElement = new TodoElementView(todo, this);
                    vboxdonetodo.getChildren().add(todoElement);
                }

                for (int i = 0; i < vboxtodo.getChildren().size(); i++){
                    TodoElementView td = (TodoElementView)vboxtodo.getChildren().get(0);
                    CheckBox c = td.getCb();
                    if (c.isSelected()){
                        todoHandler.getDoneTodoList().add(td.todo);
                        todoHandler.getTodoList().remove(td.todo);
                        vboxtodo.getChildren().remove(td);
                        vboxdonetodo.getChildren().add(td);
                    }
                }
                }












}
