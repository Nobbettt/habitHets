package main.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import main.model.Todo;

import java.io.IOException;

public class TodoElementView extends AnchorPane {

    Todo todo;

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

    private void setUpTodo(){
        CheckBox c = (CheckBox)getChildren().get(0);
        c.setText(todo.getTitle());
    }


}
