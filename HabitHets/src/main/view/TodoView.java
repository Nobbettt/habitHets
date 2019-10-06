package main.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TodoView extends StackPane {

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
}
