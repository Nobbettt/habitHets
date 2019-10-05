package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.model.CalendarAble;

import java.io.IOException;
import java.util.List;

public class YearView extends StackPane implements ViewAble {
    @FXML private ScrollPane yearScroll;
    @FXML private GridPane yearGrid;

    public YearView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpYear();
    }

    private void setUpYear() {

    }

    @Override
    public void updateView(List<? extends CalendarAble> months) {

    }

    @Override
    public void updateTimeLine() {
        // nothing to see here, just an dumb method required by the interface to be implemented
    }
}
