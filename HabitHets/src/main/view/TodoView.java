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
    @FXML private VBox vboxdonetodo;
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

    List<CheckBox> checkBoxes = new ArrayList<>();


        public void updateTodoView(List<Todo> todos ){
                for (Todo todo: todos){
                    TodoElementView todoElement = new TodoElementView(todo);
                    vboxtodo.getChildren().add(todoElement);


                    /*
                        CheckBox c = new CheckBox(todo.getTitle());
                        c.setId(todo.getId()+"");
                        c.setStyle("-fx-font-size: 20");
                        AnchorPane a = new AnchorPane();
                        a.getChildren().add(c);
                        vboxtodo.getChildren().add(a);
                        checkBoxes.add(c);
                        doneTodoList(checkBoxes);*/

                    }
                }

         public void doneTodoList(List<CheckBox> checkBoxes){
            for (CheckBox c : checkBoxes){
                if (c.isSelected()){
                    CheckBox cd = c;
                    c.setStyle("-fx-font-size: 20");
                    AnchorPane a = new AnchorPane();
                    a.getChildren().add(cd);
                    vboxdonetodo.getChildren().add(a);


                }
            }
         }







}
