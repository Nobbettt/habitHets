package view;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoView extends AnchorPane {
    @FXML private AnchorPane todoList;
    @FXML private Button addTodo;
    @FXML private VBox vboxtodo;
    @FXML private VBox vboxdonetodo;
    @FXML private AnchorPane newTodo;
    @FXML public Button closeNewTodo;
    @FXML public Button saveNewTodo;
    @FXML private TextField todoTitle;
    @FXML private ScrollPane todoScrollpane;
    @FXML private ScrollPane doneTodoScrollpane;
    private Facade facade;

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    public TodoView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/todo.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        todoScrollpane.setFitToWidth(true);
        todoScrollpane.setFitToHeight(true);
        doneTodoScrollpane.setFitToWidth(true);
        doneTodoScrollpane.setFitToHeight(true);
        this.facade = new Facade();
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
            facade.createNewTodo(todoTitle.getText());
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
        newTodo.toFront();
    }

    List<CheckBox> checkBoxes = new ArrayList<>();

    public void updateTodoView( ) {
        vboxtodo.getChildren().clear();
        vboxdonetodo.getChildren().clear();
        for (int id : facade.getTodoIds()) {
            TodoElementView todoElement = new TodoElementView(id);
            vboxtodo.getChildren().add(todoElement);
        }
        for (int id : facade.getDoneTodoIds()) {
            TodoDoneElementView todoDoneElement = new TodoDoneElementView(id);
            vboxdonetodo.getChildren().add(todoDoneElement);
        }

        for (int i = 0; i < vboxtodo.getChildren().size(); i++) {
            TodoElementView td = (TodoElementView) vboxtodo.getChildren().get(i);
            CheckBox c = td.getCb();
            if (c.isSelected()) {
                //todoOrganizer.doneTodoRemove(td.todo.getId());
                vboxtodo.getChildren().remove(td);
                TodoDoneElementView tdd = new TodoDoneElementView(td.getTodoID());
                vboxdonetodo.getChildren().add(tdd);
            }
        }
    }
}
