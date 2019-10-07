package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.awt.*;
import java.io.IOException;

public class TodoElement {

    @FXML private Label todoTitle;

    public TodoElement() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/todoElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void changeLabel(String title){
        todoTitle.setText(title);
    }
}
