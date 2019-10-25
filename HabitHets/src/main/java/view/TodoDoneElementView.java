package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import model.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TodoDoneElementView extends AnchorPane  {
    private Facade facade;
    private int id;
    List<CheckBox> checkBoxes= new ArrayList<>();

    /**
     * Imports fxml file and sets this class as the fx controller and root of the fxml file
     * loads the file and checks for exception
     */
    TodoDoneElementView(int id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/todoDoneElement.fxml"));
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
