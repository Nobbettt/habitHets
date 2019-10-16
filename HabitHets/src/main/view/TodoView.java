package main.view;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.model.Todo;
import main.model.TodoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoView extends AnchorPane {

    @FXML private AnchorPane todoList;
    @FXML private Button addTodo;
    @FXML private VBox vboxtodo;
    @FXML public VBox vboxdonetodo;
    @FXML public AnchorPane newTodo;
    @FXML public Button closeNewTodo;
    @FXML public Button saveNewTodo;
    @FXML public TextField todoTitle;
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
        //addTodo.setShape(new Circle());


    }

    @FXML
    private void closeNewTodoWindow(){
        newTodo.setVisible(false);
        newTodo.toBack();
    }

    @FXML
    private void saveNewTodo(){
        newTodo.setVisible(false);
        newTodo.toBack();
        if (todoTitle.getText() != null && !todoTitle.getText().isEmpty()){
            todoHandler.addTodo(todoTitle.getText());
            todoTitle.clear();

        }
        else{
            todoTitle.clear();
        }
        updateTodoView();

    }

    @FXML
    private void addTodoOnClick(){
        newTodo.setVisible(true);
        newTodo.toFront();/*



        todoHandler.add();
        List<Todo> newTodo = new ArrayList<>();
        newTodo.add(todoHandler.getTodoList().get(todoHandler.getTodoList().size()-1));
        updateTodoView(todoHandler.getTodoList());*/
    }

    List<CheckBox> checkBoxes = new ArrayList<>();


    public void updateTodoView( ) {
        vboxtodo.getChildren().clear();
        vboxdonetodo.getChildren().clear();
        for (Todo todo : todoHandler.getTodoList()) {
            TodoElementView todoElement = new TodoElementView(todo);
            vboxtodo.getChildren().add(todoElement);
        }
        for (Todo todo : TodoHandler.getInstant().getDoneTodoList()) {
            TodoDoneElementView todoDoneElement = new TodoDoneElementView(todo);
            //todoDoneElement.getChildren().get(1).setVisible(false);
            vboxdonetodo.getChildren().add(todoDoneElement);
            //todoDoneElement.getCb().setSelected(true);
        }

        for (int i = 0; i < vboxtodo.getChildren().size(); i++) {
            TodoElementView td = (TodoElementView) vboxtodo.getChildren().get(i);
            CheckBox c = td.getCb();
            if (c.isSelected()) {
                //todoHandler.doneTodoRemove(td.todo.getId());
                vboxtodo.getChildren().remove(td);
                TodoDoneElementView tdd=new TodoDoneElementView(td.todo);
                vboxdonetodo.getChildren().add(tdd);

            }
        }

        }



}
