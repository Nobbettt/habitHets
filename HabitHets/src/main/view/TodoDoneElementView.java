package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import main.model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoDoneElementView extends AnchorPane  {

    private Facade facade;
    private int id;




    TodoDoneElementView(int id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/todoDoneElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        this.facade = new Facade();
        this.id = id;
        setUpTodo();

    }

    List<CheckBox> cb = new ArrayList<>();

    public CheckBox getCb() {
        return cb.get(0);
    }

    private void setUpTodo(){
        CheckBox c = (CheckBox)getChildren().get(0);
        c.setText(facade.getDoneTodoTitle(id));
        c.setStyle("-fx-font-size: 15");
        c.setSelected(true);
        cb.add(c);
    }

    List<CheckBox> checkBoxes= new ArrayList<>();



    @FXML
    private void remove(){
        for (CheckBox c : cb){
            if (!c.isSelected()){
                facade.createNewTodo(facade.getDoneTodoTitle(id));
                facade.moveBackTodo(id);
            }
        }

    }
}
